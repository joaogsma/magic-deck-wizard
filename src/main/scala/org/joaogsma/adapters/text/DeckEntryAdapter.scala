package org.joaogsma.adapters.text

import org.joaogsma.models.DeckEntry

import scala.util.matching.Regex

object DeckEntryAdapter
{
  def parse(line: String): Option[DeckEntry] =
  {
    Option(parseCount(line))
        .filter(_ > 0)
        .map(DeckEntry(_, parseTags(line)))
  }

  private val CARD_COUNT_REGEX: Regex = "^\\[\\d+\\]".r
  private val TAG_GROUP_REGEX: Regex = "(@[^ @]+( @[^ @]+)*)*$".r
  private val CARD_TAG_REGEX: Regex = "@[^ @]+".r

  private def parseTags(line: String): Set[String] =
  {
    TAG_GROUP_REGEX
        .findFirstIn(line)
        .filterNot(_.isEmpty)
        .map(
          CARD_TAG_REGEX
              .findAllIn(_)
              .toSet[String]
              .map(_.replaceFirst("@", ""))
        )
        .getOrElse(Set.empty[String])
  }

  private def parseCount(line: String): Int =
  {
    CARD_COUNT_REGEX
        .findFirstIn(line)
        .getOrElse("0")
        .replaceAll("\\]|\\[", "")
        .toInt
  }
}
