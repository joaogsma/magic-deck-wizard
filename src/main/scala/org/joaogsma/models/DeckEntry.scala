package org.joaogsma.models

case class DeckEntry(
    count: Int,
    name: String,
    card: Option[Card],
    tags: Set[String]
)

object DeckEntry
{
  def apply(count: Int, name: String, tags: Set[String]): DeckEntry =
      DeckEntry(count, name, Option.empty, tags)
}

