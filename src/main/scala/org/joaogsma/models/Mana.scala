package org.joaogsma.models

sealed abstract class Mana(val count: Int) extends Product with Serializable

// TODO: add support for half, phyrexian, hybrid and generic/hybrid mana
object Mana {
  case class X(override val count: Int) extends Mana(count)
  case class Generic(override val count: Int) extends Mana(count)
  case class Colorless(override val count: Int) extends Mana(count)
  case class White(override val count: Int) extends Mana(count)
  case class Blue(override val count: Int) extends Mana(count)
  case class Black(override val count: Int) extends Mana(count)
  case class Red(override val count: Int) extends Mana(count)
  case class Green(override val count: Int) extends Mana(count)
  case class PhyrexianWhite(override val count: Int) extends Mana(count)
  case class PhyrexianBlue(override val count: Int) extends Mana(count)
  case class PhyrexianBlack(override val count: Int) extends Mana(count)
  case class PhyrexianRed(override val count: Int) extends Mana(count)
  case class PhyrexianGreen(override val count: Int) extends Mana(count)

  implicit object ManaOrdering extends Ordering[Mana] {
    override def compare(a: Mana, b: Mana): Int = toInt(a) - toInt(b)

    private def toInt(mana: Mana): Int = mana match {
      case _: X => 1
      case _: Generic => 2
      case _: Colorless => 3
      case _: PhyrexianWhite => 4
      case _: PhyrexianBlue => 5
      case _: PhyrexianBlack => 6
      case _: PhyrexianRed => 7
      case _: PhyrexianGreen => 8
      case _: White => 9
      case _: Blue => 10
      case _: Black => 11
      case _: Red => 12
      case _: Green => 13
    }
  }
}
