package org.joaogsma.adapters.text

import org.joaogsma.models.Mana
import org.joaogsma.models.Mana.ManaOrdering

import scala.util.Failure
import scala.util.Try

object ManaAdapter {
  private val X: String = "X"
  private val GENERIC: String = "\\d+"
  private val COLORLESS: String = "C"
  private val WHITE: String = "W"
  private val BLUE: String = "U"
  private val BLACK: String = "B"
  private val RED: String = "R"
  private val GREEN: String = "G"

  val MANA_COST_REGEX: String = (s""""(\\{$X\\}|\\{$GENERIC\\}|\\{$COLORLESS\\}|\\{$WHITE\\}|"""
      + s"""\\{$BLUE\\}|\\{$BLACK\\}|\\{$RED\\}|\\{$GREEN\\})*"""")

  private val MANA_SYMBOLS = Seq(X, GENERIC, COLORLESS, WHITE, BLUE, BLACK, RED, GREEN)

  def parseToSequence(str: String): Try[Seq[Mana]] = {
    if (!str.matches(MANA_COST_REGEX)) {
      Failure(new IllegalArgumentException(s"Malformed mana cost: $str"))
    } else {
      Try {
        str
            .replaceAll("(^\"\\{?)|(\\}?\"$)", "")
            .split("\\}\\{")
            .groupBy(string => MANA_SYMBOLS.find(string.matches))
            .filterKeys(_.isDefined)
            .map {
              case (Some(GENERIC), values) => toMana(GENERIC, values.map(_.toInt).sum)
              case (key, values) => toMana(key.get, values.length)
            }
            .toList
            .sorted
      }
    }
  }

  def toString(seq: Seq[Mana]): String =
      '\"' + seq.ensuring(_ != null).sorted.flatMap(toCharSeq).mkString + '\"'

  private def toMana(str: String, count: Int): Mana = str match {
    case X => Mana.X(count)
    case GENERIC => Mana.Generic(count)
    case COLORLESS => Mana.Colorless(count)
    case WHITE => Mana.White(count)
    case BLUE => Mana.Blue(count)
    case BLACK => Mana.Black(count)
    case RED => Mana.Red(count)
    case GREEN => Mana.Green(count)
  }

  private def toCharSeq(mana: Mana): Seq[String] = mana match {
    case Mana.X(count) => List.fill(count)("{X}")
    case Mana.Generic(count) => List(s"{$count}")
    case Mana.Colorless(count) => List.fill(count)("{C}")
    case Mana.White(count) => List.fill(count)("{W}")
    case Mana.Blue(count) => List.fill(count)("{U}")
    case Mana.Black(count) => List.fill(count)("{B}")
    case Mana.Red(count) => List.fill(count)("{R}")
    case Mana.Green(count) => List.fill(count)("{G}")
  }
}
