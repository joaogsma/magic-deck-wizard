package org.joaogsma.adapters.text

import org.joaogsma.models.Mana
import org.joaogsma.models.Mana.ManaOrdering

import scala.util.Failure
import scala.util.Try

object ManaAdapter {
  private val X: String = "X"
  private val GENERIC: String = "\\d+"
  private val COLORLESS: String = "C"
  private val HYBRID_MONO_WHITE: String = "2/W"
  private val HYBRID_MONO_BLUE: String = "2/U"
  private val HYBRID_MONO_BLACK: String = "2/B"
  private val HYBRID_MONO_RED: String = "2/R"
  private val HYBRID_MONO_GREEN: String = "2/G"
  private val PHYREXIAN_WHITE: String = "W/P"
  private val PHYREXIAN_BLUE: String = "U/P"
  private val PHYREXIAN_BLACK: String = "B/P"
  private val PHYREXIAN_RED: String = "R/P"
  private val PHYREXIAN_GREEN: String = "G/P"
  private val WHITE: String = "W"
  private val BLUE: String = "U"
  private val BLACK: String = "B"
  private val RED: String = "R"
  private val GREEN: String = "G"

  val MANA_COST_REGEX: String = (s""""(\\{$X\\}|\\{$GENERIC\\}|\\{$COLORLESS\\}|"""
      + (s"""\\{$HYBRID_MONO_WHITE\\}|\\{$HYBRID_MONO_BLUE\\}|\\{$HYBRID_MONO_BLACK\\}|"""
          + s"""\\{$HYBRID_MONO_RED\\}|\\{$HYBRID_MONO_GREEN\\}|""")
      + (s"""\\{$PHYREXIAN_WHITE\\}|\\{$PHYREXIAN_BLUE\\}|\\{$PHYREXIAN_BLACK\\}|"""
          + s"""\\{$PHYREXIAN_RED\\}|\\{$PHYREXIAN_GREEN\\}|""")
      + s"""\\{$WHITE\\}|\\{$BLUE\\}|\\{$BLACK\\}|\\{$RED\\}|\\{$GREEN\\})*"""")

  private val MANA_SYMBOLS = Seq(
    X,
    GENERIC,
    COLORLESS,
    PHYREXIAN_WHITE,
    PHYREXIAN_BLUE,
    PHYREXIAN_BLACK,
    PHYREXIAN_RED,
    PHYREXIAN_GREEN,
    WHITE,
    BLUE,
    BLACK,
    RED,
    GREEN)

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
    case HYBRID_MONO_WHITE => Mana.HybridMonoWhite(count)
    case HYBRID_MONO_BLUE => Mana.HybridMonoBlue(count)
    case HYBRID_MONO_BLACK => Mana.HybridMonoBlack(count)
    case HYBRID_MONO_RED => Mana.HybridMonoRed(count)
    case HYBRID_MONO_GREEN => Mana.HybridMonoGreen(count)
    case PHYREXIAN_WHITE => Mana.PhyrexianWhite(count)
    case PHYREXIAN_BLUE => Mana.PhyrexianBlue(count)
    case PHYREXIAN_BLACK => Mana.PhyrexianBlack(count)
    case PHYREXIAN_RED => Mana.PhyrexianRed(count)
    case PHYREXIAN_GREEN => Mana.PhyrexianGreen(count)
    case WHITE => Mana.White(count)
    case BLUE => Mana.Blue(count)
    case BLACK => Mana.Black(count)
    case RED => Mana.Red(count)
    case GREEN => Mana.Green(count)
  }

  private def toCharSeq(mana: Mana): Seq[String] = mana match {
    case Mana.X(count) => List.fill(count)(s"{$X}")
    case Mana.Generic(count) => List(s"{$count}")
    case Mana.Colorless(count) => List.fill(count)(s"{$COLORLESS}")
    case Mana.HybridMonoWhite(count) => List.fill(count)(s"{$HYBRID_MONO_WHITE}")
    case Mana.HybridMonoBlue(count) => List.fill(count)(s"{$HYBRID_MONO_BLUE}")
    case Mana.HybridMonoBlack(count) => List.fill(count)(s"{$HYBRID_MONO_BLACK}")
    case Mana.HybridMonoRed(count) => List.fill(count)(s"{$HYBRID_MONO_RED}")
    case Mana.HybridMonoGreen(count) => List.fill(count)(s"{$HYBRID_MONO_GREEN}")
    case Mana.PhyrexianWhite(count) => List.fill(count)(s"{$PHYREXIAN_WHITE}")
    case Mana.PhyrexianBlue(count) => List.fill(count)(s"{$PHYREXIAN_BLUE}")
    case Mana.PhyrexianBlack(count) => List.fill(count)(s"{$PHYREXIAN_BLACK}")
    case Mana.PhyrexianRed(count) => List.fill(count)(s"{$PHYREXIAN_RED}")
    case Mana.PhyrexianGreen(count) => List.fill(count)(s"{$PHYREXIAN_GREEN}")
    case Mana.White(count) => List.fill(count)(s"{$WHITE}")
    case Mana.Blue(count) => List.fill(count)(s"{$BLUE}")
    case Mana.Black(count) => List.fill(count)(s"{$BLACK}")
    case Mana.Red(count) => List.fill(count)(s"{$RED}")
    case Mana.Green(count) => List.fill(count)(s"{$GREEN}")
  }
}
