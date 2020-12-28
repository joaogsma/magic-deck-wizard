package org.joaogsma.adapters.text

import org.joaogsma.entities.models.Mana
import org.joaogsma.entities.models.Mana.ManaOrdering

import scala.util.Failure
import scala.util.Try

object ManaAdapter {
//  private val X: String = "X"
//  private val GENERIC: String = "\\d+"
//  private val COLORLESS: String = "C"

//  private val HYBRID_MONO_WHITE: String = "2/W"
//  private val HYBRID_MONO_BLUE: String = "2/U"
//  private val HYBRID_MONO_BLACK: String = "2/B"
//  private val HYBRID_MONO_RED: String = "2/R"
//  private val HYBRID_MONO_GREEN: String = "2/G"

//  private val HYBRID_WHITE_BLUE: String = "W/U"
//  private val HYBRID_WHITE_BLACK: String = "W/B"
//  private val HYBRID_WHITE_RED: String = "W/R"
//  private val HYBRID_WHITE_GREEN: String = "W/G"
//  private val HYBRID_BLUE_BLACK: String = "U/B"
//  private val HYBRID_BLUE_RED: String = "U/R"
//  private val HYBRID_BLUE_GREEN: String = "U/G"
//  private val HYBRID_BLACK_RED: String = "B/R"
//  private val HYBRID_BLACK_GREEN: String = "B/G"
//  private val HYBRID_RED_GREEN: String = "R/G"

//  private val PHYREXIAN_WHITE: String = "W/P"
//  private val PHYREXIAN_BLUE: String = "U/P"
//  private val PHYREXIAN_BLACK: String = "B/P"
//  private val PHYREXIAN_RED: String = "R/P"
//  private val PHYREXIAN_GREEN: String = "G/P"

//  private val WHITE: String = "W"
//  private val BLUE: String = "U"
//  private val BLACK: String = "B"
//  private val RED: String = "R"
//  private val GREEN: String = "G"

//  private val MANA_SYMBOLS: Seq[String] = Seq(
//    X,
//    GENERIC,
//    COLORLESS,
//    HYBRID_MONO_WHITE,
//    HYBRID_MONO_BLUE,
//    HYBRID_MONO_BLACK,
//    HYBRID_MONO_RED,
//    HYBRID_MONO_GREEN,
//    HYBRID_WHITE_BLUE,
//    HYBRID_WHITE_BLACK,
//    HYBRID_WHITE_RED,
//    HYBRID_WHITE_GREEN,
//    HYBRID_BLUE_BLACK,
//    HYBRID_BLUE_RED,
//    HYBRID_BLUE_GREEN,
//    HYBRID_BLACK_RED,
//    HYBRID_BLACK_GREEN,
//    HYBRID_RED_GREEN,
//    PHYREXIAN_WHITE,
//    PHYREXIAN_BLUE,
//    PHYREXIAN_BLACK,
//    PHYREXIAN_RED,
//    PHYREXIAN_GREEN,
//    WHITE,
//    BLUE,
//    BLACK,
//    RED,
//    GREEN)

//  val MANA_COST_REGEX: String = new StringBuilder()
//      .append("\"(")
//      .append(MANA_SYMBOLS.map(str => s"""\\{$str\\}""").mkString("|"))
//      .append(")*\"")
//      .mkString

  def parseToSequence(str: String): Try[Seq[Mana]] = {
    Failure(new RuntimeException("Not implemented"))
//    if (!str.matches(MANA_COST_REGEX)) {
//      Failure(new IllegalArgumentException(s"Malformed mana cost: $str"))
//    } else {
//      Try {
//        str
//            .replaceAll("(^\"\\{?)|(\\}?\"$)", "")
//            .split("\\}\\{")
//            .groupBy(string => MANA_SYMBOLS.find(string.matches))
//            .view
//            .filterKeys(_.isDefined)
//            .map {
//              case (Some(GENERIC), values) => toMana(GENERIC, values.map(_.toInt).sum)
//              case (key, values) => toMana(key.get, values.length)
//            }
//            .toList
//            .sorted
//      }
//    }
  }

//  def toString(seq: Seq[Mana]): String =
//      "\"" + seq.ensuring(_ != null).sorted.flatMap(toCharSeq).mkString + "\""

//  private def toMana(str: String, count: Int): Mana = str match {
//    case X => Mana.X(count)
//    case GENERIC => Mana.Generic(count)
//    case COLORLESS => Mana.Colorless(count)
//
//    case HYBRID_MONO_WHITE => Mana.HybridMonoWhite(count)
//    case HYBRID_MONO_BLUE => Mana.HybridMonoBlue(count)
//    case HYBRID_MONO_BLACK => Mana.HybridMonoBlack(count)
//    case HYBRID_MONO_RED => Mana.HybridMonoRed(count)
//    case HYBRID_MONO_GREEN => Mana.HybridMonoGreen(count)
//
//    case HYBRID_WHITE_BLUE => Mana.HybridWhiteBlue(count)
//    case HYBRID_WHITE_BLACK => Mana.HybridWhiteBlack(count)
//    case HYBRID_WHITE_RED => Mana.HybridWhiteRed(count)
//    case HYBRID_WHITE_GREEN => Mana.HybridWhiteGreen(count)
//    case HYBRID_BLUE_BLACK => Mana.HybridBlueBlack(count)
//    case HYBRID_BLUE_RED => Mana.HybridBlueRed(count)
//    case HYBRID_BLUE_GREEN => Mana.HybridBlueGreen(count)
//    case HYBRID_BLACK_RED => Mana.HybridBlackRed(count)
//    case HYBRID_BLACK_GREEN => Mana.HybridBlackGreen(count)
//    case HYBRID_RED_GREEN => Mana.HybridRedGreen(count)
//
//    case PHYREXIAN_WHITE => Mana.PhyrexianWhite(count)
//    case PHYREXIAN_BLUE => Mana.PhyrexianBlue(count)
//    case PHYREXIAN_BLACK => Mana.PhyrexianBlack(count)
//    case PHYREXIAN_RED => Mana.PhyrexianRed(count)
//    case PHYREXIAN_GREEN => Mana.PhyrexianGreen(count)
//
//    case WHITE => Mana.White(count)
//    case BLUE => Mana.Blue(count)
//    case BLACK => Mana.Black(count)
//    case RED => Mana.Red(count)
//    case GREEN => Mana.Green(count)
//  }

//  private def toCharSeq(mana: Mana): Seq[String] = mana match {
//    case Mana.X(count) => List.fill(count)(s"{$X}")
//    case Mana.Generic(count) => List(s"{$count}")
//    case Mana.Colorless(count) => List.fill(count)(s"{$COLORLESS}")
//
//    case Mana.HybridMonoWhite(count) => List.fill(count)(s"{$HYBRID_MONO_WHITE}")
//    case Mana.HybridMonoBlue(count) => List.fill(count)(s"{$HYBRID_MONO_BLUE}")
//    case Mana.HybridMonoBlack(count) => List.fill(count)(s"{$HYBRID_MONO_BLACK}")
//    case Mana.HybridMonoRed(count) => List.fill(count)(s"{$HYBRID_MONO_RED}")
//    case Mana.HybridMonoGreen(count) => List.fill(count)(s"{$HYBRID_MONO_GREEN}")
//
//    case Mana.HybridWhiteBlue(count) => List.fill(count)(s"{$HYBRID_WHITE_BLUE}")
//    case Mana.HybridWhiteBlack(count) => List.fill(count)(s"{$HYBRID_WHITE_BLACK}")
//    case Mana.HybridWhiteRed(count) => List.fill(count)(s"{$HYBRID_WHITE_RED}")
//    case Mana.HybridWhiteGreen(count) => List.fill(count)(s"{$HYBRID_WHITE_GREEN}")
//    case Mana.HybridBlueBlack(count) => List.fill(count)(s"{$HYBRID_BLUE_BLACK}")
//    case Mana.HybridBlueRed(count) => List.fill(count)(s"{$HYBRID_BLUE_RED}")
//    case Mana.HybridBlueGreen(count) => List.fill(count)(s"{$HYBRID_BLUE_GREEN}")
//    case Mana.HybridBlackRed(count) => List.fill(count)(s"{$HYBRID_BLACK_RED}")
//    case Mana.HybridBlackGreen(count) => List.fill(count)(s"{$HYBRID_BLACK_GREEN}")
//    case Mana.HybridRedGreen(count) => List.fill(count)(s"{$HYBRID_RED_GREEN}")
//
//    case Mana.PhyrexianWhite(count) => List.fill(count)(s"{$PHYREXIAN_WHITE}")
//    case Mana.PhyrexianBlue(count) => List.fill(count)(s"{$PHYREXIAN_BLUE}")
//    case Mana.PhyrexianBlack(count) => List.fill(count)(s"{$PHYREXIAN_BLACK}")
//    case Mana.PhyrexianRed(count) => List.fill(count)(s"{$PHYREXIAN_RED}")
//    case Mana.PhyrexianGreen(count) => List.fill(count)(s"{$PHYREXIAN_GREEN}")
//
//    case Mana.White(count) => List.fill(count)(s"{$WHITE}")
//    case Mana.Blue(count) => List.fill(count)(s"{$BLUE}")
//    case Mana.Black(count) => List.fill(count)(s"{$BLACK}")
//    case Mana.Red(count) => List.fill(count)(s"{$RED}")
//    case Mana.Green(count) => List.fill(count)(s"{$GREEN}")
//  }
}
