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

  case class HybridWhiteBlue(override val count: Int) extends Mana(count)
  case class HybridWhiteBlack(override val count: Int) extends Mana(count)
  case class HybridWhiteRed(override val count: Int) extends Mana(count)
  case class HybridWhiteGreen(override val count: Int) extends Mana(count)
  case class HybridBlueBlack(override val count: Int) extends Mana(count)
  case class HybridBlueRed(override val count: Int) extends Mana(count)
  case class HybridBlueGreen(override val count: Int) extends Mana(count)
  case class HybridBlackRed(override val count: Int) extends Mana(count)
  case class HybridBlackGreen(override val count: Int) extends Mana(count)
  case class HybridRedGreen(override val count: Int) extends Mana(count)

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

      case _: HybridWhiteBlue => 9
      case _: HybridWhiteBlack => 10
      case _: HybridWhiteRed => 11
      case _: HybridWhiteGreen => 12
      case _: HybridBlueBlack => 13
      case _: HybridBlueRed => 14
      case _: HybridBlueGreen => 15
      case _: HybridBlackRed => 16
      case _: HybridBlackGreen => 17
      case _: HybridRedGreen => 18

      case _: PhyrexianWhite => 19
      case _: PhyrexianBlue => 20
      case _: PhyrexianBlack => 21
      case _: PhyrexianRed => 22
      case _: PhyrexianGreen => 23

      case _: White => 24
      case _: Blue => 25
      case _: Black => 26
      case _: Red => 27
      case _: Green => 28
    }
  }
}
