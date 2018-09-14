package org.joaogsma.adapters.scryfall

import org.joaogsma.models.Mana

import scala.util.matching.Regex

object ManaAdapter
{
  // TODO: ensure that the string contains nothing but the mana symbols
  def parseSequence(str: String): Seq[Mana] =
  {
    val orderedSeq = Seq(GENERIC, COLORLESS, WHITE, BLUE, BLACK, RED, GREEN)
    orderedSeq
        .map(regex => toMana(regex, regex.findAllMatchIn(str).size))
        .filter(_.count > 0)
  }

  private val GENERIC: Regex = "{\\d+}".r
  private val COLORLESS: Regex = "{C}".r
  private val WHITE: Regex = "{W}".r
  private val BLUE: Regex = "{U}".r
  private val BLACK: Regex = "{B}".r
  private val RED: Regex = "{R}".r
  private val GREEN: Regex = "{G}".r

  private def toMana(manaRegex: Regex, count: Int): Mana = manaRegex match
  {
    case GENERIC => Mana.Generic(count)
    case COLORLESS => Mana.Colorless(count)
    case WHITE => Mana.White(count)
    case BLUE => Mana.Blue(count)
    case BLACK => Mana.Black(count)
    case RED => Mana.Red(count)
    case GREEN => Mana.Green(count)
  }
}
