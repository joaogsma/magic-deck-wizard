package org.joaogsma.controllers

import java.util.Comparator

import javafx.stage
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
import scalafx.stage.Screen
import scalafx.stage.Stage

import scala.io.StdIn
import scala.math.max
import scala.util.Failure
import scala.util.Success

object ConsoleController extends JFXApp
{
  val entriesOpt: Option[Seq[DeckEntry]] = Option(parameters.raw.toList)
      .flatMap
      {
        case filename :: Nil => Some(filename)
        case _ =>
          println("[ERROR] Missing filename")
          None
      }
      .flatMap(filename =>
      {
        readDeckList(filename)
            .map(entries =>
            {
              val filledEntries = entries.map(fillMissingField)
              if (entries.exists(_.card.isEmpty) && filledEntries.forall(_.card.isDefined))
              {
                val answer = queryUser("Save filled deck list? [y/n]", Set("y", "n"))
                if (answer.contains("y"))
                  writeFilledDeckList(filledEntries, filename)
              }
              filledEntries
            })
      })

  entriesOpt match
  {
    case Some(entries) =>
      println(metricsString(entries))
      initializeStages(entries)
    case None => Platform.exit()
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
            val ratioStr = f"${count.toDouble / totalCardCount}%.2f"
            val padding = List.fill(maxTagLength - tag.length + 1)('=').mkString
            s"  - $tag $padding> count = $countStr; ratio = $ratioStr\n"
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
          padding = Insets(20)
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
          .map { case (key, value) => XYChart.Data(value: Number, s"$key ($value)") }
          .toSeq
    )

    val countAxisUpperBound = if (tagCounts.isEmpty) 20 else max(20, tagCounts.values.max + 1)

    val countAxis = NumberAxis("Count")
    countAxis.setAutoRanging(false)
    countAxis.setLowerBound(0)
    countAxis.setUpperBound(countAxisUpperBound)
    countAxis.setTickUnit(1)
    countAxis.setMinorTickVisible(false)

    val categoriAxis = CategoryAxis("Tags")
    categoriAxis.setTickLabelFont(Font.font(12))

    val tagsBarChart = BarChart[Number, String](
      countAxis,
      categoriAxis,
      ObservableBuffer(XYChart.Series[Number, String](data))
    )
    tagsBarChart.setLegendVisible(false)
    tagsBarChart.setHorizontalGridLinesVisible(false)
    tagsBarChart.setMinWidth(countAxisUpperBound * 30)
    tagsBarChart.setMinHeight(tagCounts.keys.size * 30)
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
          .map { case (key, value) => XYChart.Data[Number, String](value, key.formatted("%.2f")) }
          .toSeq
    )

    val totalData = toDataObservableBuffer(totalManaCurve)
    val whiteData = toDataObservableBuffer(whiteManaCurve)
    val blueData = toDataObservableBuffer(blueManaCurve)
    val blackData = toDataObservableBuffer(blackManaCurve)
    val redData = toDataObservableBuffer(redManaCurve)
    val greenData = toDataObservableBuffer(greenManaCurve)

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

    XYChart.Series[Number, String]("Total", totalData)
    XYChart.Series[Number, String]("White", whiteData)
    XYChart.Series[Number, String]("Blue", blueData)
    XYChart.Series[Number, String]("Black", blackData)
    XYChart.Series[Number, String]("Red", redData)
    XYChart.Series[Number, String]("Green", greenData)

    val manaCurveBarChart = BarChart[Number, String](
      countAxis,
      manaCostAxis,
      ObservableBuffer(
        XYChart.Series[Number, String]("Total", totalData),
        XYChart.Series[Number, String]("White", whiteData),
        XYChart.Series[Number, String]("Blue", blueData),
        XYChart.Series[Number, String]("Black", blackData),
        XYChart.Series[Number, String]("Red", redData),
        XYChart.Series[Number, String]("Green", greenData)
      )
    )

    manaCurveBarChart.setStyle("-fx-bar-fill: blue")

    manaCurveBarChart.setHorizontalGridLinesVisible(false)
    manaCurveBarChart.setBarGap(0)
    manaCurveBarChart.setMinWidth(countAxisUpperBound * 30)
    manaCurveBarChart.setMinHeight(totalManaCurve.keys.size * 30)
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
