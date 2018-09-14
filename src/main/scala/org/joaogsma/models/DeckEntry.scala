package org.joaogsma.models

import scala.util.matching.Regex

case class DeckEntry(
    count: Int,
    card: Option[Card],
    tags: Set[String]
)

object DeckEntry
{
  def apply(count: Int, tags: Set[String]): DeckEntry =
  {
    DeckEntry(count, Option.empty, tags)
  }

  def parse(line: String): Option[DeckEntry] =
  {
    Option(getCount(line))
        .filter(_ > 0)
        .map(DeckEntry(_, getTags(line)))
  }

  private val CARD_COUNT_REGEX: Regex = "^\\[\\d+\\]".r
  private val TAG_GROUP_REGEX: Regex = "(@[^ @]+( @[^ @]+)*)*$".r
  private val CARD_TAG_REGEX: Regex = "@[^ @]+".r

  private def getTags(line: String): Set[String] =
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

  private def getCount(line: String): Int =
  {
    CARD_COUNT_REGEX
        .findFirstIn(line)
        .getOrElse("0")
        .replaceAll("\\]|\\[", "")
        .toInt
  }
}

