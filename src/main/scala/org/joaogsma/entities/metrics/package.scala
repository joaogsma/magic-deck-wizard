package org.joaogsma.entities

import org.joaogsma.entities.models.{Color, DeckEntry, CompleteDeckEntry, Mana, Type}

import scala.math.max

package object metrics {
  def countCards(entries: Seq[CompleteDeckEntry]): Int = entries.map(_.count).sum

  def countTags(entries: Seq[CompleteDeckEntry]): Map[String, Int] = {
    entries
        .flatMap(entry => entry.tags.map(_ -> entry.count))
        .groupBy(_._1)
        .view
        .mapValues(_.map(_._2).sum)
        .toMap
  }

  def countTypes(entries: Seq[CompleteDeckEntry]): Map[Type, Int] = {
    entries
        .flatMap(entry => entry.card.types.map(_ -> entry.count))
        .groupBy(_._1)
        .view
        .mapValues(_.map(_._2).sum)
        .toMap
  }

  def countColors(entries: Seq[CompleteDeckEntry]): Map[Option[Color], Int] = {
    val counts: Map[Option[Color], Int] = entries
        .flatMap(entry => {
          entry.card.colors match {
            case Nil => List(Option.empty -> entry.count)
            case colors => colors.map(Some(_) -> entry.count)
          }
        })
        .groupBy(_._1)
        .view
        .mapValues(_.map(_._2).sum)
        .toMap

    val keys = List(
      None,
      Some(Color.White),
      Some(Color.Blue),
      Some(Color.Black),
      Some(Color.Red),
      Some(Color.Green))

    keys.map(key => key -> counts.getOrElse(key, 0)).toMap
  }

  def countManaSymbols(entries: Seq[CompleteDeckEntry]): Map[Option[Color], Int] = {
    val counts: Map[Option[Color], Int] = entries
        .flatMap(entry => {
          def toCountOptions(count: Int, colors: Color*): Seq[Option[(Option[Color], Int)]] =
              colors.to(Seq).map(color => Some(Some(color) -> count * entry.count))

          entry.card.manaCost
              .flatMap {
                case Mana.HybridMonoWhite(count) => toCountOptions(count, Color.White)
                case Mana.HybridMonoBlue(count) => toCountOptions(count, Color.Blue)
                case Mana.HybridMonoBlack(count) => toCountOptions(count, Color.Black)
                case Mana.HybridMonoRed(count) => toCountOptions(count, Color.Red)
                case Mana.HybridMonoGreen(count) => toCountOptions(count, Color.Green)

                case Mana.HybridWhiteBlue(count) => toCountOptions(count, Color.White, Color.Blue)
                case Mana.HybridWhiteBlack(count) => toCountOptions(count, Color.White, Color.Black)
                case Mana.HybridWhiteRed(count) => toCountOptions(count, Color.White, Color.Red)
                case Mana.HybridWhiteGreen(count) => toCountOptions(count, Color.White, Color.Green)
                case Mana.HybridBlueBlack(count) => toCountOptions(count, Color.Blue, Color.Black)
                case Mana.HybridBlueRed(count) => toCountOptions(count, Color.Blue, Color.Red)
                case Mana.HybridBlueGreen(count) => toCountOptions(count, Color.Blue, Color.Green)
                case Mana.HybridBlackRed(count) => toCountOptions(count, Color.Black, Color.Red)
                case Mana.HybridBlackGreen(count) => toCountOptions(count, Color.Black, Color.Green)
                case Mana.HybridRedGreen(count) => toCountOptions(count, Color.Red, Color.Green)

                case Mana.PhyrexianWhite(count) => toCountOptions(count, Color.White)
                case Mana.PhyrexianBlue(count) => toCountOptions(count, Color.Blue)
                case Mana.PhyrexianBlack(count) => toCountOptions(count, Color.Black)
                case Mana.PhyrexianRed(count) => toCountOptions(count, Color.Red)
                case Mana.PhyrexianGreen(count) => toCountOptions(count, Color.Green)

                case Mana.White(count) => toCountOptions(count, Color.White)
                case Mana.Blue(count) => toCountOptions(count, Color.Blue)
                case Mana.Black(count) => toCountOptions(count, Color.Black)
                case Mana.Red(count) => toCountOptions(count, Color.Red)
                case Mana.Green(count) => toCountOptions(count, Color.Green)

                case Mana.Colorless(count) => Seq(Some(None -> count * entry.count))
                case _ => Seq(Option.empty)
              }
              .filter(_.isDefined)
              .map(_.get)
        })
        .groupBy(_._1)
        .view
        .mapValues(_.map(_._2).sum)
        .toMap

    val keys = List(
      Some(Color.White),
      Some(Color.Blue),
      Some(Color.Black),
      Some(Color.Red),
      Some(Color.Green),
      None)
    keys.map(key => key -> counts.getOrElse(key, 0)).toMap
  }

  def countManaCurve(entries: Seq[CompleteDeckEntry]): Map[Double, Int] = {
    val counts: Map[Double, Int] = entries
        .groupBy(_.card.cmc)
        .view
        .mapValues(_.map(_.count).sum)
        .toMap

    val maxKey = if (counts.isEmpty) 10.0 else max(counts.keys.max, 10.0)
    Range.BigDecimal.inclusive(0.0, maxKey, 1.0)
        .map(_.toDouble)
        .map(key => key -> counts.getOrElse(key, 0))
        .toMap
  }
}
