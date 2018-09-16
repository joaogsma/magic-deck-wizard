package org.joaogsma

import org.joaogsma.metrics.countCards
import org.joaogsma.metrics.countTags
import org.joaogsma.models.DeckEntry
import org.joaogsma.ports.file.DeckListPort

object MtgDeckWizard extends App
{
  args.toList match
  {
    case List() => println("Pass the filename as a parameter")
    case List(filename) =>
      val cards: Seq[DeckEntry] = DeckListPort.readCards(filename)
      print(metricsString(cards))
  }

  private def metricsString(cards: Seq[DeckEntry]): String =
  {
    val result: StringBuilder = StringBuilder.newBuilder

    val totalCardCount: Int = countCards(cards)
    result.append(s"Total number of cards: $totalCardCount\n")

    val tagCount: Map[String, Int] = countTags(cards)

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

    result.toString
  }
}
