package org.joaogsma.adapters.proto

import org.joaogsma.models.Type
import org.joaogsma.models.proto.CacheProtos

import scala.util.Try

object TypeAdapter {
  def fromProto(cardType: CacheProtos.CardType): Try[Type] = {
    Try {
      cardType match {
        case CacheProtos.CardType.LAND => Type.Land
        case CacheProtos.CardType.CREATURE => Type.Creature
        case CacheProtos.CardType.INSTANT => Type.Instant
        case CacheProtos.CardType.SORCERY => Type.Sorcery
        case CacheProtos.CardType.ARTIFACT => Type.Artifact
        case CacheProtos.CardType.ENCHANTMENT => Type.Enchantment
        case CacheProtos.CardType.PLANESWALKER => Type.Planeswalker
        case _ => throw new MatchError("Unknown card type")
      }
    }
  }

  def toProto(cardType: Type): CacheProtos.CardType = cardType match {
    case Type.Land => CacheProtos.CardType.LAND
    case Type.Creature => CacheProtos.CardType.CREATURE
    case Type.Instant => CacheProtos.CardType.INSTANT
    case Type.Sorcery => CacheProtos.CardType.SORCERY
    case Type.Artifact => CacheProtos.CardType.ARTIFACT
    case Type.Enchantment => CacheProtos.CardType.ENCHANTMENT
    case Type.Planeswalker => CacheProtos.CardType.PLANESWALKER
    case _ => throw new MatchError("Unknown card type")
  }
}
