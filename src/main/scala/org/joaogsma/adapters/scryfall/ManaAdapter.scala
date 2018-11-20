package org.joaogsma.adapters.scryfall

import org.joaogsma.models.Mana
import org.joaogsma.models.Mana.ManaOrdering

import scala.util.matching.Regex

object ManaAdapter {
  // TODO: ensure that the string contains nothing but the mana symbols
  def parseToSequence(str: String): Seq[Mana] = {
    val orderedSeq = Seq(
      X,
      GENERIC,
      COLORLESS,
      HYBRID_MONO_WHITE,
      HYBRID_MONO_BLUE,
      HYBRID_MONO_BLACK,
      HYBRID_MONO_RED,
      HYBRID_MONO_GREEN,
      HYBRID_WHITE_BLUE,
      HYBRID_WHITE_BLACK,
      HYBRID_WHITE_RED,
      HYBRID_WHITE_GREEN,
      HYBRID_BLUE_BLACK,
      HYBRID_BLUE_RED,
      HYBRID_BLUE_GREEN,
      HYBRID_BLACK_RED,
      HYBRID_BLACK_GREEN,
      HYBRID_RED_GREEN,
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
    orderedSeq
        .map(regex => {
          val count = regex
              .findAllMatchIn(str)
              .map(_.matched)
              .map(matchToManaCount(regex, _))
              .sum
          toMana(regex, count)
        })
        .filter(_.count > 0)
        .sorted
  }

  private val X: Regex = "\\{X\\}".r
  private val GENERIC: Regex = "\\{\\d+\\}".r
  private val COLORLESS: Regex = "\\{C\\}".r

  private val HYBRID_MONO_WHITE: Regex = "\\{2/W\\}".r
  private val HYBRID_MONO_BLUE: Regex = "\\{2/U\\}".r
  private val HYBRID_MONO_BLACK: Regex = "\\{2/B\\}".r
  private val HYBRID_MONO_RED: Regex = "\\{2/R\\}".r
  private val HYBRID_MONO_GREEN: Regex = "\\{2/G\\}".r

  private val HYBRID_WHITE_BLUE: Regex = "\\{W/U\\}".r
  private val HYBRID_WHITE_BLACK: Regex = "\\{W/B\\}".r
  private val HYBRID_WHITE_RED: Regex = "\\{W/R\\}".r
  private val HYBRID_WHITE_GREEN: Regex = "\\{W/G\\}".r
  private val HYBRID_BLUE_BLACK: Regex = "\\{U/B\\}".r
  private val HYBRID_BLUE_RED: Regex = "\\{U/R\\}".r
  private val HYBRID_BLUE_GREEN: Regex = "\\{U/G\\}".r
  private val HYBRID_BLACK_RED: Regex = "\\{B/R\\}".r
  private val HYBRID_BLACK_GREEN: Regex = "\\{B/G\\}".r
  private val HYBRID_RED_GREEN: Regex = "\\{R/G\\}".r

  private val PHYREXIAN_WHITE: Regex = "\\{W/P\\}".r
  private val PHYREXIAN_BLUE: Regex = "\\{U/P\\}".r
  private val PHYREXIAN_BLACK: Regex = "\\{B/P\\}".r
  private val PHYREXIAN_RED: Regex = "\\{R/P\\}".r
  private val PHYREXIAN_GREEN: Regex = "\\{G/P\\}".r

  private val WHITE: Regex = "\\{W\\}".r
  private val BLUE: Regex = "\\{U\\}".r
  private val BLACK: Regex = "\\{B\\}".r
  private val RED: Regex = "\\{R\\}".r
  private val GREEN: Regex = "\\{G\\}".r

  private def matchToManaCount(manaRegex: Regex, str: String): Int = manaRegex match {
    case GENERIC => str.substring(1, str.length - 1).toInt
    case _ => 1
  }

  private def toMana(manaRegex: Regex, count: Int): Mana = manaRegex match {
    case X => Mana.X(count)
    case GENERIC => Mana.Generic(count)
    case COLORLESS => Mana.Colorless(count)

    case HYBRID_MONO_WHITE => Mana.HybridMonoWhite(count)
    case HYBRID_MONO_BLUE => Mana.HybridMonoBlue(count)
    case HYBRID_MONO_BLACK => Mana.HybridMonoBlack(count)
    case HYBRID_MONO_RED => Mana.HybridMonoRed(count)
    case HYBRID_MONO_GREEN => Mana.HybridMonoGreen(count)

    case HYBRID_WHITE_BLUE => Mana.HybridWhiteBlue(count)
    case HYBRID_WHITE_BLACK => Mana.HybridWhiteBlack(count)
    case HYBRID_WHITE_RED => Mana.HybridWhiteRed(count)
    case HYBRID_WHITE_GREEN => Mana.HybridWhiteGreen(count)
    case HYBRID_BLUE_BLACK => Mana.HybridBlueBlack(count)
    case HYBRID_BLUE_RED => Mana.HybridBlueRed(count)
    case HYBRID_BLUE_GREEN => Mana.HybridBlueGreen(count)
    case HYBRID_BLACK_RED => Mana.HybridBlackRed(count)
    case HYBRID_BLACK_GREEN => Mana.HybridBlackGreen(count)
    case HYBRID_RED_GREEN => Mana.HybridRedGreen(count)

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
}
