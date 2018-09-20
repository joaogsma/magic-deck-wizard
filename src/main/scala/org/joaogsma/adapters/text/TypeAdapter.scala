package org.joaogsma.adapters.text

import org.joaogsma.models.Type
import org.joaogsma.models.Type.TypeOrdering

import scala.util.Failure
import scala.util.Try
import scala.util.matching.Regex

object TypeAdapter
{
  private val CREATURE: Regex = "[Cc]reature".r
  private val INSTANT: Regex = "[Ii]nstant".r
  private val SORCERY: Regex = "[Ss]orcery".r
  private val ARTIFACT: Regex = "[Aa]rtifact".r
  private val ENCHANTMENT: Regex = "[Ee]nchantment".r
  private val PLANESWALKER: Regex = "[Pp]laneswalker".r
  private val LAND: Regex = "[Ll]and".r

  private val TYPE_REGEX: Regex =
      s"($CREATURE|$INSTANT|$SORCERY|$ARTIFACT|$ENCHANTMENT|$PLANESWALKER|$LAND)".r

  val TYPE_SEQUENCE_REGEX: Regex = s"\\[ *$TYPE_REGEX(, *$TYPE_REGEX)* *\\]".r

  private val TYPES = List(ENCHANTMENT, ARTIFACT, CREATURE, INSTANT, SORCERY, PLANESWALKER, LAND)

  def parseToSequence(str: String): Try[Seq[Type]] =
  {
    if (!str.matches(TYPE_SEQUENCE_REGEX.toString))
      Failure(new IllegalArgumentException(s"Malformed type: $str"))
    else
      Try(str.substring(1, str.length - 1).split(',').map(s => parse(s.trim)).sorted)
  }

  def parse(str: String): Type =
  {
    TYPES.find(regex => str.matches(regex.toString)) match
    {
      case Some(ENCHANTMENT) => Type.Enchantment
      case Some(ARTIFACT) => Type.Artifact
      case Some(CREATURE) => Type.Creature
      case Some(INSTANT) => Type.Instant
      case Some(SORCERY) => Type.Sorcery
      case Some(PLANESWALKER) => Type.Planeswalker
      case Some(LAND) => Type.Land
      case _ => throw new IllegalArgumentException(s"Malformed type: $str")
    }
  }
}
