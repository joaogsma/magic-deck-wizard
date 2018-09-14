package org.joaogsma.models

case class DeckEntry(
    count: Int,
    card: Option[Card],
    tags: Set[String]
)

object DeckEntry
{
  def apply(count: Int, tags: Set[String]): DeckEntry = DeckEntry(count, Option.empty, tags)
}

