package org.joaogsma.models

sealed trait Type

object Type
{
  case object Creature extends Type
  case object Instant extends Type
  case object Sorcery extends Type
  case object Artifact extends Type
  case object Enchantment extends Type
  case object Planeswalker extends Type
  case object Land extends Type
}
