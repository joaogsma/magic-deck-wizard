package org.joaogsma

import scala.io.BufferedSource
import scala.io.Source

object MtgDeckWizard extends App
{
  args.toList match
  {
    case List() => println("Pass the filename as a parameter")
    case List(filename) =>
      val bufferedSource = Source.fromFile(filename)
      val cards: Seq[Card] = usingFile(bufferedSource, readCards)
      print(metricsString(cards))
  }

  private def usingFile[A](bufferedSource: BufferedSource, f: BufferedSource => A): A =
  {
    try
      f(bufferedSource)
    finally
      bufferedSource.close()
  }

  private def readCards(bufferedSource: BufferedSource): Seq[Card] =
  {
    val deckLines: Iterator[String] = bufferedSource.getLines.filter(_.nonEmpty)
    deckLines
        .map(_.trim)
        .map(Card.apply)
        .filter(_.isDefined)
        .map(_.get)
        .toList
  }

  private def metricsString(cards: Seq[Card]): String =
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

  private def countTags(cards: Seq[Card]): Map[String, Int] =
  {
    cards
        .flatMap(card => card.tags.map(_ -> card.count))
        .groupBy(_._1)
        .mapValues(_.map(_._2).sum)
  }
}
