package org.joaogsma.adapters.text

import org.joaogsma.entities.models.DeckEntry

import scala.util.Failure
import scala.util.Try
import scala.util.matching.Regex

object DeckEntryAdapter {
//  private val NAME_REGEX = "\\S+( *\\S+)*".r

//  val DECK_ENTRY_REGEX: Regex =
//      (s"^ *${CountAdapter.COUNT_REGEX} +" +
//          s"$NAME_REGEX *" +
//          s"(${CardAdapter.CARD_REGEX})?" +
//          s"( +${TagAdapter.TAG_GROUP_REGEX})?" + " *$").r

  def parse(line: String): Try[DeckEntry] = {
    Failure(new RuntimeException("Not implemented"))
//    if (!line.matches(DECK_ENTRY_REGEX.toString)) {
//      Failure(new IllegalArgumentException(s"Malformed line: $line"))
//    } else {
//      Try {
//        val count: Regex.Match = CountAdapter.COUNT_REGEX.findFirstMatchIn(line).get
//        val cardMatch: Option[Regex.Match] = CardAdapter.CARD_REGEX.findFirstMatchIn(line)
//        val tagsMatch: Option[Regex.Match] = TagAdapter.TAG_GROUP_REGEX.findFirstMatchIn(line)
//
//        val nameEnd: Int = List(cardMatch.map(_.start), tagsMatch.map(_.start))
//            .find(_.isDefined)
//            .flatten
//            .getOrElse(line.length)
//        val name: String = line.substring(count.end, nameEnd).trim
//
//        if (name.isEmpty)
//          throw new IllegalArgumentException(s"Malformed line: $line")
//
//        DeckEntry(
//          CountAdapter.parse(count.matched).get,
//          name,
//          cardMatch.map(regexMatch => CardAdapter.parse(regexMatch.matched).get),
//          tagsMatch
//              .map(regexMatch => TagAdapter.parseToSequence(regexMatch.matched).get)
//              .getOrElse(Set.empty))
//      }
//    }
  }

  def toString(entry: DeckEntry): String = {
    throw new RuntimeException("Not implemented")
//    assert(entry != null)
//    val count: String = CountAdapter.toString(entry.count)
//    val card: Option[String] = entry.card.map(CardAdapter.toString)
//    val tags: String = TagAdapter.toString(entry.tags)
//    s"$count ${entry.name} ${card.getOrElse("")} $tags"
  }
}
