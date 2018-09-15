package org.joaogsma.adapters.scryfall

import org.joaogsma.models.Type

object TypeAdapter
{
  def parseToSequence(str: String): Seq[Type] =
  {
    val orderedSeq = Seq(ENCHANTMENT, ARTIFACT, CREATURE, INSTANT, SORCERY, PLANESWALKER, LAND)
    orderedSeq.filter(str.contains).map(TypeAdapter.parse)
  }

  private val CREATURE: String = "Creature"
  private val INSTANT: String = "Instant"
  private val SORCERY: String = "Sorcery"
  private val ARTIFACT: String = "Artifact"
  private val ENCHANTMENT: String = "Enchantment"
  private val PLANESWALKER: String = "Planeswalker"
  private val LAND: String = "Land"

  private def parse(typeStr: String): Type = typeStr match
  {
    case ENCHANTMENT => Type.Enchantment
    case ARTIFACT => Type.Artifact
    case CREATURE => Type.Creature
    case INSTANT => Type.Instant
    case SORCERY => Type.Sorcery
    case PLANESWALKER => Type.Planeswalker
    case LAND => Type.Land
  }
}
