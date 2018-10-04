package org.joaogsma.adapters.text

import org.joaogsma.models.Color
import org.joaogsma.models.Color.ColorOrdering

import scala.util.Failure
import scala.util.Try
import scala.util.matching.Regex

object ColorAdapter
{
  private val WHITE: String = "[Ww]"
  private val BLUE: String = "[Uu]"
  private val BLACK: String = "[Bb]"
  private val RED: String = "[Rr]"
  private val GREEN: String = "[Gg]"

  val COLORS_REGEX: Regex = s""""($WHITE|$BLUE|$BLACK|$RED|$GREEN)*"""".r

  private val COLORS = List(WHITE, BLUE, BLACK, RED, GREEN)

  def parseToSequence(str: String): Try[Seq[Color]] =
  {
    if (!str.matches(COLORS_REGEX.toString))
      Failure(new IllegalArgumentException(s"Malformed colors: $str"))
    else
    {
      Try
      {
        str
            .substring(1, str.length - 1)
            .groupBy(c => COLORS.find(c.toString.matches))
            .map
            {
              case (Some(colorStr), occurrences) if occurrences.length == 1 => toColor(colorStr)
              case _ => throw new IllegalArgumentException(s"Malformed colors: $str")
            }
            .toList
            .sorted
      }
    }
  }

  def toString(seq: Seq[Color]): String =
      '\"' + seq.ensuring(_ != null).sorted.map(toString).mkString + '\"'

  protected def toColor(str: String): Color = str match
  {
    case WHITE => Color.White
    case BLUE => Color.Blue
    case BLACK => Color.Black
    case RED => Color.Red
    case GREEN => Color.Green
  }

  protected def toString(color: Color): String = color match
  {
    case Color.White => "W"
    case Color.Blue => "U"
    case Color.Black => "B"
    case Color.Red => "R"
    case Color.Green => "G"
  }
}
