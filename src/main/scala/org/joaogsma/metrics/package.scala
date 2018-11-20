package org.joaogsma

import org.joaogsma.models.Color
import org.joaogsma.models.DeckEntry
import org.joaogsma.models.Mana
import org.joaogsma.models.Type

import scala.math.max

package object metrics {
  def countCards(entries: Seq[DeckEntry]): Int = entries.map(_.count).sum

  def countTags(entries: Seq[DeckEntry]): Map[String, Int] = {
    entries
        .flatMap(entry => entry.tags.map(_ -> entry.count))
        .groupBy(_._1)
        .mapValues(_.map(_._2).sum)
  }

  def countTypes(entries: Seq[DeckEntry]): Map[Type, Int] = {
    entries
        .ensuring(_.forall(_.card.isDefined))
        .flatMap(entry => entry.card.get.types.map(_ -> entry.count))
        .groupBy(_._1)
        .mapValues(_.map(_._2).sum)
  }

  def countColors(entries: Seq[DeckEntry]): Map[Option[Color], Int] = {
    val counts: Map[Option[Color], Int] = entries
        .ensuring(_.forall(_.card.isDefined))
        .flatMap(entry => {
          entry.card.get.colors match {
            case Nil => List(Option.empty -> entry.count)
            case colors => colors.map(Some(_) -> entry.count)
          }
        })
        .groupBy(_._1)
        .mapValues(_.map(_._2).sum)

    val keys = List(
      None,
      Some(Color.White),
      Some(Color.Blue),
      Some(Color.Black),
      Some(Color.Red),
      Some(Color.Green))

    keys.map(key => key -> counts.getOrElse(key, 0)).toMap
  }

  def countManaSymbols(entries: Seq[DeckEntry]): Map[Option[Color], Int] = {
    val counts: Map[Option[Color], Int] = entries
        .ensuring(_.forall(_.card.isDefined))
        .flatMap(entry => {
          def colorCount(color: Color, count: Int): Option[(Option[Color], Int)] =
              Some(Some(color) -> count * entry.count)
          entry.card.get.manaCost
              .map {
                case Mana.HybridMonoWhite(count) => colorCount(Color.White, count)
                case Mana.HybridMonoBlue(count) => colorCount(Color.Blue, count)
                case Mana.HybridMonoBlack(count) => colorCount(Color.Black, count)
                case Mana.HybridMonoRed(count) => colorCount(Color.Red, count)
                case Mana.HybridMonoGreen(count) => colorCount(Color.Green, count)
                case Mana.PhyrexianWhite(count) => colorCount(Color.White, count)
                case Mana.PhyrexianBlue(count) => colorCount(Color.Blue, count)
                case Mana.PhyrexianBlack(count) => colorCount(Color.Black, count)
                case Mana.PhyrexianRed(count) => colorCount(Color.Red, count)
                case Mana.PhyrexianGreen(count) => colorCount(Color.Green, count)
                case Mana.White(count) => colorCount(Color.White, count)
                case Mana.Blue(count) => colorCount(Color.Blue, count)
                case Mana.Black(count) => colorCount(Color.Black, count)
                case Mana.Red(count) => colorCount(Color.Red, count)
                case Mana.Green(count) => colorCount(Color.Green, count)
                case Mana.Colorless(count) => Some(None -> count * entry.count)
                case _ => Option.empty
              }
              .filter(_.isDefined)
              .map(_.get)
        })
        .groupBy(_._1)
        .mapValues(_.map(_._2).sum)

    val keys = List(
      Some(Color.White),
      Some(Color.Blue),
      Some(Color.Black),
      Some(Color.Red),
      Some(Color.Green),
      None)
    keys.map(key => key -> counts.getOrElse(key, 0)).toMap
  }

  def countManaCurve(entries: Seq[DeckEntry]): Map[Double, Int] = {
    val counts: Map[Double, Int] = entries
        .ensuring(_.forall(_.card.isDefined))
        .groupBy(_.card.get.cmc)
        .mapValues(_.map(_.count).sum)

    val maxKey = if (counts.isEmpty) 10.0 else max(counts.keys.max, 10.0)
    Range.BigDecimal.inclusive(0.0, maxKey, 1.0)
        .map(_.toDouble)
        .map(key => key -> counts.getOrElse(key, 0))
        .toMap
  }
}
