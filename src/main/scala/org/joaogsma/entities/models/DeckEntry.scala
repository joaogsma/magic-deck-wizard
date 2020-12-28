package org.joaogsma.entities.models

case class DeckEntry(
    count: Int,
    name: String,
    manaCost: Option[Set[Mana]],
    colors: Option[Set[Color]],
    types: Option[Set[Type]],
    cmc: Option[Double],
    tags: Set[String])

object DeckEntry {
  def apply(count: Int, name: String, tags: Set[String]): DeckEntry =
      DeckEntry(count, name, Option.empty, Option.empty, Option.empty, Option.empty, tags)
}