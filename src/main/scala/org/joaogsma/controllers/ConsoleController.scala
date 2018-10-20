package org.joaogsma.controllers

import cats.implicits._
import com.monovore.decline._
import org.joaogsma.metrics.countCards
import org.joaogsma.metrics.countManaCurve
import org.joaogsma.metrics.countTags
import org.joaogsma.metrics.countTypes
import org.joaogsma.models.Color
import org.joaogsma.models.DeckEntry
import org.joaogsma.ports.file.DeckListPort
import org.joaogsma.ports.scryfall.ScryfallPort
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.application.Platform
import scalafx.collections.ObservableBuffer
import scalafx.geometry.Insets
import scalafx.scene.Scene
import scalafx.scene.chart.BarChart
import scalafx.scene.chart.CategoryAxis
import scalafx.scene.chart.NumberAxis
import scalafx.scene.chart.PieChart
import scalafx.scene.chart.XYChart
import scalafx.scene.layout.HBox
import scalafx.scene.text.Font
import scalafx.stage.Stage

import scala.io.StdIn
import scala.math.max
import scala.math.pow
import scala.util.Failure
import scala.util.Success

object ConsoleController extends JFXApp
{
  private val CONSOLE_MODE = 1
  private val WINDOWED_MODE = 2

  parseArguments(parameters.raw) match
  {
    case Left(help) =>
      println(help)
      Platform.exit()
    case Right((mode, file)) =>
      readDeckList(file)
          .map(entries =>
          {
            val filledEntries = entries.map(fillMissingField)
            if (entries.exists(_.card.isEmpty) && filledEntries.forall(_.card.isDefined))
            {
              val answer = queryUser("Save filled deck list? [y/n]", Set("y", "n"))
              if (answer.contains("y"))
                writeFilledDeckList(filledEntries, file)
            }
            filledEntries
          })
          .foreach(entries =>
          {
            mode match
            {
              case CONSOLE_MODE =>
                println(metricsString(entries))
                Platform.exit()
              case WINDOWED_MODE => initializeStages(entries)
            }
          })
  }

  def parseArguments(args: Seq[String]): Either[Help, (Int, String)] = {
    val consoleOpts = Opts
        .flag(long = "console", short = "c", help = "Print the results on the console")
        .map(_ => CONSOLE_MODE)
    val windowedOpts = Opts
        .flag(long = "windowed", short = "w", help = "Print the results on separate windows")
        .map(_ => WINDOWED_MODE)
    val fileOpts = Opts.argument[String]("file")

    val command = Command("dummy-name", "dummy-header")(
      (consoleOpts orElse windowedOpts, fileOpts).mapN(Tuple2.apply)
    )

    command.parse(args)
  }

  def readDeckList(filename: String): Option[Seq[DeckEntry]] =
  {
    DeckListPort.read(filename) match
    {
      case Failure(exception) =>
        println(s"[ERROR] ${exception.getMessage}")
        None
      case Success(entries) => Option(entries)
    }
  }

  def fillMissingField(entry: DeckEntry): DeckEntry = entry.card match
  {
    case Some(_) => entry
    case None =>
      print(s""" Searching Scryfall for missing information on card "${entry.name}"...""")
      ScryfallPort.searchCardName(entry.name) match
      {
        case Success(card) =>
          println("done")
          entry.copy(card = Some(card))
        case Failure(_) =>
          println(
            s"\n[ERROR] Could not get missing information of the card ${entry.name} from Scryfall."
          )
          entry
      }
  }

  def writeFilledDeckList(filledCards: Seq[DeckEntry], filename: String): Unit =
  {
    val filledDeckListFilename =
      filename.lastIndexOf('.') match
      {
        case -1 => filename + "_filled"
        case formatStart =>
          (filename.substring(0, formatStart) + "_filled"
              + filename.substring(formatStart))
      }

    DeckListPort.write(filledCards, filledDeckListFilename)
    println(s"[INFO] Filled deck list saved at $filledDeckListFilename")
  }

  private def metricsString(entries: Seq[DeckEntry]): String =
  {
    val result: StringBuilder = StringBuilder.newBuilder

    val totalCardCount: Int = countCards(entries)
    result.append(s"Total number of cards: $totalCardCount\n")

    val tagCount: Map[String, Int] = countTags(entries)

    if (tagCount.nonEmpty)
    {
      val maxTagLength: Int = tagCount.keys.map(_.length).max
      result.append("Tags:\n")
      tagCount
          .map { case (tag, count) =>
            val countStr = if (count < 10) "0" + count.toString else count.toString
            val padding = List.fill(maxTagLength - tag.length + 1)('=').mkString
            s"  - $tag $padding> count = $countStr\n"
          }
          .toSeq
          .sorted
          .foreach(result.append)
    }

    if (entries.forall(_.card.isDefined))
    {
      result.append("Mana curve:\n")
      countManaCurve(entries)
          .filter(_._2 > 0)
          .toList
          .sorted
          .foreach { case (cost, count) => result.append(s"  - $cost: $count\n") }
    }
    result.toString
  }

  private def initializeStages(entries: Seq[DeckEntry]): Unit =
  {
    stage = new PrimaryStage {
      title = "Tags"
      scene = new Scene {
        content = new HBox {
          padding = Insets(5)
          children = Seq(initializeTagsBarChart(entries))
          resizable = false
        }
      }
    }

    val secondaryStages = List(
      new Stage {
        title = "Mana Curve"
        scene = new Scene {
          content = new HBox {
            padding = Insets(20)
            children = Seq(initializeManaCurveBarChart(entries))
            resizable = false
          }
          stylesheets = Seq("mana-curve-stage.css")
        }
      },
      new Stage {
        title = "Card Types"
        scene = new Scene {
          content = new HBox {
            padding = Insets(20)
            children = Seq(initializeTypesPieChart(entries))
            resizable = false
          }
        }
      }
    )

    secondaryStages
        .zipWithIndex
        .foreach
        {
          case (secondaryStage, index) =>
            secondaryStage.setY((index + 1) * 100)
            secondaryStage.setX((index + 1) * 100)
            secondaryStage.show()
        }
  }

  private def initializeTagsBarChart(entries: Seq[DeckEntry]): BarChart[Number, String] =
  {
    val tagCounts = countTags(entries)

    val data = ObservableBuffer(
      tagCounts
          .toSeq
          .sorted(Ordering[(String, Int)].reverse)
          .map { case (key, value) => XYChart.Data(value: Number, s"$key ($value)") }
    )

    val countAxisUpperBound = if (tagCounts.isEmpty) 20 else max(20, tagCounts.values.max + 1)

    val countAxis = NumberAxis("Count")
    countAxis.setAutoRanging(false)
    countAxis.setLowerBound(0)
    countAxis.setUpperBound(countAxisUpperBound)
    countAxis.setTickUnit(1)
    countAxis.setMinorTickVisible(false)

    val categoriAxis = CategoryAxis("Tags")
    val fontSize = if (tagCounts.keys.size <= 30) 12 else if (tagCounts.keys.size <= 39) 11 else 10
    categoriAxis.setTickLabelFont(Font.font(fontSize))

    val tagsBarChart = BarChart[Number, String](
      countAxis,
      categoriAxis,
      ObservableBuffer(XYChart.Series[Number, String](data))
    )

    val minHeight = fontSize match {
      case 10 => 21 * tagCounts.keys.size
      case 11 => 21.5 * tagCounts.keys.size
      case 12 => 22 * tagCounts.keys.size
    }

    tagsBarChart.setCategoryGap(5)
    tagsBarChart.setLegendVisible(false)
    tagsBarChart.setHorizontalGridLinesVisible(false)
    tagsBarChart.setMinWidth(countAxisUpperBound * 35)
    tagsBarChart.setMinHeight(minHeight)
    tagsBarChart
  }

  private def initializeManaCurveBarChart(entries: Seq[DeckEntry]): BarChart[Number, String] =
  {
    assert(entries.forall(_.card.isDefined))

    val totalManaCurve = countManaCurve(entries)
    val whiteManaCurve = countManaCurve(entries.filter(_.card.get.colors.contains(Color.White)))
    val blueManaCurve = countManaCurve(entries.filter(_.card.get.colors.contains(Color.Blue)))
    val blackManaCurve = countManaCurve(entries.filter(_.card.get.colors.contains(Color.Black)))
    val redManaCurve = countManaCurve(entries.filter(_.card.get.colors.contains(Color.Red)))
    val greenManaCurve = countManaCurve(entries.filter(_.card.get.colors.contains(Color.Green)))

    def toDataObservableBuffer(manaCurve: Map[Double, Int]) = ObservableBuffer(
      manaCurve
          .toSeq
          .sortBy(_._1)
          .map { case (key, value) => XYChart.Data[Number, String](value, key.formatted("%.2f")) }
    )

    val totalSeries = Option(totalManaCurve)
        .filter(_.exists { case (_, count) => count > 0 })
        .orElse(Some(Map.empty[Double, Int]))
        .map(toDataObservableBuffer)
        .map(XYChart.Series[Number, String]("Total", _))
        .get

    val whiteSeries = Option(whiteManaCurve)
        .filter(_.exists { case (_, count) => count > 0 })
        .orElse(Some(Map.empty[Double, Int]))
        .map(toDataObservableBuffer)
        .map(XYChart.Series[Number, String]("White", _))
        .get

    val blueSeries = Option(blueManaCurve)
        .filter(_.exists { case (_, count) => count > 0 })
        .orElse(Some(Map.empty[Double, Int]))
        .map(toDataObservableBuffer)
        .map(XYChart.Series[Number, String]("Blue", _))
        .get

    val blackSeries = Option(blackManaCurve)
        .filter(_.exists { case (_, count) => count > 0 })
        .orElse(Some(Map.empty[Double, Int]))
        .map(toDataObservableBuffer)
        .map(XYChart.Series[Number, String]("Black", _))
        .get

    val redSeries = Option(redManaCurve)
        .filter(_.exists { case (_, count) => count > 0 })
        .orElse(Some(Map.empty[Double, Int]))
        .map(toDataObservableBuffer)
        .map(XYChart.Series[Number, String]("Red", _))
        .get

    val greenSeries = Option(greenManaCurve)
        .filter(_.exists { case (_, count) => count > 0 })
        .orElse(Some(Map.empty[Double, Int]))
        .map(toDataObservableBuffer)
        .map(XYChart.Series[Number, String]("Green", _))
        .get

    val countAxisUpperBound =
        if (totalManaCurve.isEmpty) 20 else max(20, totalManaCurve.values.max + 1)

    val countAxis = NumberAxis("Count")
    countAxis.setAutoRanging(false)
    countAxis.setLowerBound(0)
    countAxis.setUpperBound(countAxisUpperBound)
    countAxis.setTickUnit(1)
    countAxis.setMinorTickVisible(false)

    val manaCostAxis = CategoryAxis("Mana Cost")
    manaCostAxis.setTickLabelFont(Font.font(12))

    val manaCurveBarChart = BarChart[Number, String](
      countAxis,
      manaCostAxis,
      ObservableBuffer(totalSeries, whiteSeries, blueSeries, blackSeries, redSeries, greenSeries)
    )

    val colors = Seq(whiteManaCurve, blueManaCurve, blackManaCurve, redManaCurve, greenManaCurve)
        .count(_.values.sum > 0)

    val categoryGap = pow(colors, 1.9).round
    val minHeight = (50 + 5 * colors) * totalManaCurve.keys.size

    manaCurveBarChart.setHorizontalGridLinesVisible(false)
    manaCurveBarChart.setBarGap(0)
    manaCurveBarChart.setCategoryGap(categoryGap)
    manaCurveBarChart.setMinWidth(countAxisUpperBound * 35)
    manaCurveBarChart.setMinHeight(minHeight)
    manaCurveBarChart
  }

  private def initializeTypesPieChart(entries: Seq[DeckEntry]): PieChart =
  {
    val pieChartData = ObservableBuffer(
      countTypes(entries)
          .map { case (key, value) => PieChart.Data(s"$key ($value)", value) }
          .toSeq
    )

    PieChart(pieChartData)
  }

  private def queryUser(
      message: String,
      validAnswers: Set[String],
      maxAttempts: Int = 5): Option[String] =
  {
    println(message)

    var attempts = 0
    var answer: String = StdIn.readLine
    while(!validAnswers.contains(answer) && attempts < maxAttempts)
    {
      println(s"Invalid answer: $answer")
      answer = StdIn.readLine
      attempts += 1
    }

    attempts match
    {
      case `maxAttempts` => Option.empty
      case _ => Option(answer)
    }
  }
}
