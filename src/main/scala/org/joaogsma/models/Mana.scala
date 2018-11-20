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
  case class HybridMonoWhite(override val count: Int) extends Mana(count)
  case class HybridMonoBlue(override val count: Int) extends Mana(count)
  case class HybridMonoBlack(override val count: Int) extends Mana(count)
  case class HybridMonoRed(override val count: Int) extends Mana(count)
  case class HybridMonoGreen(override val count: Int) extends Mana(count)
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
      case _: HybridMonoWhite => 4
      case _: HybridMonoBlue => 5
      case _: HybridMonoBlack => 6
      case _: HybridMonoRed => 7
      case _: HybridMonoGreen => 8
      case _: PhyrexianWhite => 9
      case _: PhyrexianBlue => 10
      case _: PhyrexianBlack => 11
      case _: PhyrexianRed => 12
      case _: PhyrexianGreen => 13
      case _: White => 14
      case _: Blue => 15
      case _: Black => 16
      case _: Red => 17
      case _: Green => 18
    }
  }
}
