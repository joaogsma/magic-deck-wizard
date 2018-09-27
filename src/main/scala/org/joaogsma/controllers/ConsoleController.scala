package org.joaogsma.controllers

import org.joaogsma.metrics.countCards
import org.joaogsma.metrics.countManaCurve
import org.joaogsma.metrics.countTags
import org.joaogsma.models.DeckEntry

import org.joaogsma.ports.file.DeckListPort
import org.joaogsma.ports.scryfall.ScryfallPort

import scala.util.Failure
import scala.util.Success

object ConsoleController extends App
{
  args.toList match
  {
    case filename :: Nil =>
      DeckListPort.read(filename) match
      {
        case Success(entries) =>
          val completeCards = entries.map(fillMissingField)
          print(metricsString(completeCards))
        case Failure(exception) => println(s"[ERROR] ${exception.getMessage}")
      }
    case _ => println("[ERROR] Missing filename")
  }

  def fillMissingField(entry: DeckEntry): DeckEntry = entry.card match
  {
    case Some(_) => entry
    case None =>
      ScryfallPort.searchCardName(entry.name) match
      {
        case Success(card) => entry.copy(card = Some(card))
        case Failure(_) =>
          println(
            s"[ERROR] Could not get missing information of the card ${entry.name} from Scryfall."
          )
          entry
      }
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
}
