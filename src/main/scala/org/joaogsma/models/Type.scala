package org.joaogsma.models

sealed trait Type

object Type {
  case object Creature extends Type
  case object Instant extends Type
  case object Sorcery extends Type
  case object Artifact extends Type
  case object Enchantment extends Type
  case object Planeswalker extends Type
  case object Land extends Type

  implicit object TypeOrdering extends Ordering[Type] {
    private val ORDERING_MAP: Map[Type, Int] = Map(
      Enchantment -> 1,
      Artifact -> 2,
      Creature -> 3,
      Instant -> 4,
      Sorcery -> 5,
      Planeswalker -> 6,
      Land -> 7)

    override def compare(a: Type, b: Type): Int = ORDERING_MAP(a) - ORDERING_MAP(b)
  }
}
