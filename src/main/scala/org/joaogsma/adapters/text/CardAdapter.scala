package org.joaogsma.adapters.text

import org.joaogsma.entities.models.Card
import org.joaogsma.entities.models.Color
import org.joaogsma.entities.models.Mana
import org.joaogsma.entities.models.Type

import scala.util.Failure
import scala.util.Try
import scala.util.matching.Regex

object CardAdapter {
//  private val NAME_REGEX = "\\S+( *\\S+)*".r
//  private val MANA_COST_BLOCK_REGEX: Regex = s"manacost: *${ManaAdapter.MANA_COST_REGEX} *[,\\}]".r
//  private val COLORS_BLOCK_REGEX: Regex = s"colors: *${ColorAdapter.COLORS_REGEX} *[,\\}]".r
//  private val TYPES_BLOCK_REGEX: Regex = s"types: *${TypeAdapter.TYPE_SEQUENCE_REGEX} *[,\\}]".r
//  private val CMC_BLOCK_REGEX: Regex = "cmc: *\\d+(.\\d+)? *[,\\}]".r
//
//  private val DATA_BLOCK_REGEX: Regex = (s"(($MANA_COST_BLOCK_REGEX)|($COLORS_BLOCK_REGEX)|"
//      + s"($TYPES_BLOCK_REGEX)|($CMC_BLOCK_REGEX))").r

//  val CARD_REGEX: Regex =
//      s"$NAME_REGEX* \\{ *$DATA_BLOCK_REGEX *$DATA_BLOCK_REGEX *$DATA_BLOCK_REGEX *$DATA_BLOCK_REGEX".r

  def parse(str: String): Try[Card] = {
    Failure(new RuntimeException("Not implemented"))
//    if (!str.matches(CARD_REGEX.toString)) {
//      Failure(new IllegalArgumentException(s"Malformed string: $str"))
//    } else {
//      val parsedManaCost: Try[Seq[Mana]] =
//          parseBlock(MANA_COST_BLOCK_REGEX, str, "manacost:".length, ManaAdapter.parseToSequence)
//      val parsedColors: Try[Seq[Color]] =
//          parseBlock(COLORS_BLOCK_REGEX, str, "colors:".length, ColorAdapter.parseToSequence)
//      val parsedTypes: Try[Seq[Type]] =
//          parseBlock(TYPES_BLOCK_REGEX, str, "types:".length, TypeAdapter.parseToSequence)
//      val parsedCmc: Try[Double] =
//          parseBlock(CMC_BLOCK_REGEX, str, "cmc: ".length, s => Try(s.toDouble))
//
//      Try(Card(parsedManaCost.get, parsedColors.get, parsedTypes.get, parsedCmc.get))
//    }
  }

//  def toString(card: Card): String = {
//    assert(card != null)
//    val manaCost: String = ManaAdapter.toString(card.manaCost)
//    val colors: String = ColorAdapter.toString(card.colors)
//    val types: String = TypeAdapter.toString(card.types)
//    val cmc: String = card.cmc.formatted("%.2f")
//    s"{manacost: $manaCost, colors: $colors, types: $types, cmc: $cmc}"
//  }

  private def parseBlock[A](
      regex: Regex,
      str: String,
      blockOffset: Int,
      parsingFunction: String => Try[A]): Try[A] = {
    regex.findAllMatchIn(str).toList match {
      case regexMatch :: Nil =>
        val matched = regexMatch.matched
        parsingFunction(matched.substring(blockOffset, matched.length - 1).trim)
      case _ => throw new IllegalArgumentException(s"Malformed string: $str")
    }
  }
}
