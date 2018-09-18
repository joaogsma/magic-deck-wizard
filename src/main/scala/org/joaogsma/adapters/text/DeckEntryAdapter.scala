package org.joaogsma.adapters.text

import org.joaogsma.models.DeckEntry

import scala.util.Try
import scala.util.matching.Regex

object DeckEntryAdapter
{
  val DECK_ENTRY_REGEX: Regex = (s"^${CountAdapter.COUNT_REGEX} +.+" +
      s"(${CardAdapter.CARD_REGEX})?( +${TagAdapter.TAG_GROUP_REGEX})?"+ "$").r

  def parse(line: String): Try[DeckEntry] =
  {
    exactMatch(DECK_ENTRY_REGEX, line)
        .map(string =>
        {
          val count: Regex.Match = CountAdapter.COUNT_REGEX.findFirstMatchIn(string).get
          val cardMatch: Option[Regex.Match] = CardAdapter.CARD_REGEX.findFirstMatchIn(string)
          val tagsMatch: Option[Regex.Match] = TagAdapter.TAG_GROUP_REGEX.findFirstMatchIn(string)

          val nameEnd: Int = List(cardMatch.map(_.end), tagsMatch.map(_.end))
              .find(_.isDefined)
              .flatten
              .getOrElse(string.length)
          val name: String = string.substring(count.end, nameEnd)

          DeckEntry(
            CountAdapter.parseCount(count.matched).get,
            name,
            cardMatch.map(regexMatch => CardAdapter.parse(regexMatch.matched).get),
            tagsMatch
                .map(regexMatch => TagAdapter.parseToSequence(regexMatch.matched).get)
                .getOrElse(Set.empty)
          )
        })
  }
}
