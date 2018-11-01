package org.joaogsma.ports.scalafx

import org.joaogsma.metrics.countManaCurve
import org.joaogsma.metrics.countManaSymbols
import org.joaogsma.metrics.countTags
import org.joaogsma.metrics.countTypes
import org.joaogsma.models.Color
import org.joaogsma.models.DeckEntry
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
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

import scala.math.max
import scala.math.pow

object ScalaFxPort extends JFXApp {
  def initialize(entries: Seq[DeckEntry]): Unit = {
    delayedInit(initializeStages(entries))
    main(Array.empty)
  }

  private def initializeStages(entries: Seq[DeckEntry]): Unit = {
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
        title = "Mana Symbols"
        scene = new Scene {
          content = new HBox {
            padding = Insets(20)
            children = Seq(initializeManaSymbolsBarChart(entries))
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
      })

    secondaryStages
        .zipWithIndex
        .foreach {
          case (secondaryStage, index) =>
            secondaryStage.setY((index + 1) * 100)
            secondaryStage.setX((index + 1) * 100)
            secondaryStage.show()
        }
  }

  private def initializeTagsBarChart(entries: Seq[DeckEntry]): BarChart[Number, String] = {
    val tagCounts = countTags(entries)

    val data = ObservableBuffer(
      tagCounts
          .toSeq
          .sorted(Ordering[(String, Int)].reverse)
          .map { case (key, value) => XYChart.Data(value: Number, s"$key ($value)") })

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
      ObservableBuffer(XYChart.Series[Number, String](data)))

    val minHeight = fontSize match {
      case 10 => 21 * tagCounts.keys.size
      case 11 => 22 * tagCounts.keys.size
      case 12 => 30 * tagCounts.keys.size
    }

    tagsBarChart.setCategoryGap(5)
    tagsBarChart.setLegendVisible(false)
    tagsBarChart.setHorizontalGridLinesVisible(false)
    tagsBarChart.setPrefWidth(countAxisUpperBound * 35)
    tagsBarChart.setPrefHeight(minHeight)
    tagsBarChart
  }

  private def initializeManaCurveBarChart(entries: Seq[DeckEntry]): BarChart[Number, String] = {
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
          .map { case (key, value) => XYChart.Data[Number, String](value, key.formatted("%.2f")) })

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
      ObservableBuffer(totalSeries, whiteSeries, blueSeries, blackSeries, redSeries, greenSeries))

    val colors = Seq(whiteManaCurve, blueManaCurve, blackManaCurve, redManaCurve, greenManaCurve)
        .count(_.values.sum > 0)

    val categoryGap = pow(colors, 1.9).round
    val minHeight = (50 + 5 * colors) * totalManaCurve.keys.size

    manaCurveBarChart.setHorizontalGridLinesVisible(false)
    manaCurveBarChart.setBarGap(0)
    manaCurveBarChart.setCategoryGap(categoryGap)
    manaCurveBarChart.setPrefWidth(countAxisUpperBound * 35)
    manaCurveBarChart.setPrefHeight(minHeight)
    manaCurveBarChart
  }

  private def initializeManaSymbolsBarChart(entries: Seq[DeckEntry]): BarChart[Number, String] = {
    assert(entries.forall(_.card.isDefined))

    val manaSymbols = countManaSymbols(entries)
    val manaSymbolsData = ObservableBuffer(
      manaSymbols
          .toSeq
          .sorted
          .map {
            case (colorOpt, count) =>
              val colorStr = colorOpt match {
                case None => "Colorless"
                case Some(color) => color.toString
              }
              XYChart.Data[Number, String](count, colorStr)
          })

    val countAxisUpperBound = max(20, manaSymbols.values.max + 1)

    val countAxis = NumberAxis("Count")
    countAxis.setAutoRanging(false)
    countAxis.setLowerBound(0)
    countAxis.setUpperBound(countAxisUpperBound)
    countAxis.setTickUnit(5)
    countAxis.setMinorTickVisible(true)

    val manaCostAxis = CategoryAxis("Color")
    manaCostAxis.setTickLabelFont(Font.font(12))

    val manaSymbolBarChart = BarChart[Number, String](
      countAxis,
      manaCostAxis,
      ObservableBuffer(XYChart.Series[Number, String]("Mana Symbols", manaSymbolsData)))

    manaSymbolBarChart.setLegendVisible(false)
    manaSymbolBarChart.setHorizontalGridLinesVisible(false)
    manaSymbolBarChart.setBarGap(10)
    manaSymbolBarChart.setPrefWidth(countAxisUpperBound * 12.5)
    manaSymbolBarChart.setPrefHeight(350)
    manaSymbolBarChart
  }


  private def initializeTypesPieChart(entries: Seq[DeckEntry]): PieChart = {
    val pieChartData = ObservableBuffer(
      countTypes(entries)
          .map { case (key, value) => PieChart.Data(s"$key ($value)", value) }
          .toSeq)

    PieChart(pieChartData)
  }
}
