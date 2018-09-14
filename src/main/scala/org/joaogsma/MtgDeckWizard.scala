package org.joaogsma

import org.joaogsma.adapters.text.DeckEntryAdapter
import org.joaogsma.models.DeckEntry
import org.joaogsma.ports.files.usingFile

import scala.io.BufferedSource
import scala.io.Source

object MtgDeckWizard extends App
{
  args.toList match
  {
    case List() => println("Pass the filename as a parameter")
    case List(filename) =>
      val bufferedSource = Source.fromFile(filename)
      val cards: Seq[DeckEntry] = usingFile(bufferedSource, readCards)
      print(metricsString(cards))
  }

  private def readCards(bufferedSource: BufferedSource): Seq[DeckEntry] =
  {
    val deckLines: Iterator[String] = bufferedSource.getLines.filter(_.nonEmpty)
    deckLines
        .map(_.trim)
        .map(DeckEntryAdapter.parse)
        .filter(_.isDefined)
        .map(_.get)
        .toList
  }

  private def metricsString(cards: Seq[DeckEntry]): String =
  {
    val result: StringBuilder = StringBuilder.newBuilder

    val totalCardCount: Int = cards.map(_.count).sum
    result.append(s"Total number of cards: $totalCardCount\n")

    val tagCount: Map[String, Int] = countTags(cards)
    if (tagCount.nonEmpty) {
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

    result.toString
  }

  private def countTags(cards: Seq[DeckEntry]): Map[String, Int] =
  {
    cards
        .flatMap(card => card.tags.map(_ -> card.count))
        .groupBy(_._1)
        .mapValues(_.map(_._2).sum)
  }
}
