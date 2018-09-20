package org.joaogsma.models

sealed trait Color

object Color
{
  case object White extends Color
  case object Blue extends Color
  case object Black extends Color
  case object Red extends Color
  case object Green extends Color

  implicit object ColorOrdering extends Ordering[Color]
  {
    val ORDERING_MAP: Map[Color, Int] = Map(White -> 1, Blue -> 2, Black -> 3, Red -> 4, Green -> 5)

    override def compare(a: Color, b: Color): Int = ORDERING_MAP(a) - ORDERING_MAP(b)
  }
}
