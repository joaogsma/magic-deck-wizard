package org.joaogsma.models

sealed abstract class Mana(val count: Int) extends Product with Serializable

// TODO: add support for half, phyrexian, hybrid and generic/hybrid mana
object Mana
{
  case class X(override val count: Int) extends Mana(count)
  case class Generic(override val count: Int) extends Mana(count)
  case class Colorless(override val count: Int) extends Mana(count)
  case class White(override val count: Int) extends Mana(count)
  case class Blue(override val count: Int) extends Mana(count)
  case class Black(override val count: Int) extends Mana(count)
  case class Red(override val count: Int) extends Mana(count)
  case class Green(override val count: Int) extends Mana(count)

  implicit object ManaOrdering extends Ordering[Mana]
  {
    override def compare(a: Mana, b: Mana): Int = toInt(a) - toInt(b)

    private def toInt(mana: Mana): Int = mana match
    {
      case _: X => 1
      case _: Generic => 2
      case _: Colorless => 3
      case _: White => 4
      case _: Blue => 5
      case _: Black => 6
      case _: Red => 7
      case _: Green => 8
    }
  }
}
