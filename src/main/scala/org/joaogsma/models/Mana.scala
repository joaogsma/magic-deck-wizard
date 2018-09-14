package org.joaogsma.models

sealed abstract class Mana(val count: Int)

// TODO: add support for phyrexian, hybrid and generic/hybrid mana
object Mana
{
  case class Generic(override val count: Int) extends Mana(count)
  case class Colorless(override val count: Int) extends Mana(count)
  case class White(override val count: Int) extends Mana(count)
  case class Blue(override val count: Int) extends Mana(count)
  case class Black(override val count: Int) extends Mana(count)
  case class Red(override val count: Int) extends Mana(count)
  case class Green(override val count: Int) extends Mana(count)
}
