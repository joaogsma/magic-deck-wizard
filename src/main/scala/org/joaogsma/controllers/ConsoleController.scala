package org.joaogsma.controllers

import org.joaogsma.metrics.countCards
import org.joaogsma.metrics.countTags
import org.joaogsma.models.DeckEntry
import org.joaogsma.ports.file.DeckListPort

import scala.util.Failure
import scala.util.Success

object ConsoleController extends App
{
  args.toList match
  {
    case filename :: Nil =>
      DeckListPort.read(filename) match
      {
        case Success(cards) => print(metricsString(cards))
        case Failure(exception) => println(s"[ERROR] ${exception.getMessage}")
      }
    case _ => println("[ERROR] Missing filename")
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
