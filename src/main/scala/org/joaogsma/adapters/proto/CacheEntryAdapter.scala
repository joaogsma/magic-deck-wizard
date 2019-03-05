package org.joaogsma.adapters.proto

import org.joaogsma.models.Card
import org.joaogsma.models.Color
import org.joaogsma.models.Mana
import org.joaogsma.models.Type
import org.joaogsma.models.proto.CacheProtos

import scala.collection.JavaConverters._
import scala.util.Try

object CacheEntryAdapter {
  def fromProto(cardProto: CacheProtos.Card): Try[(String, Card)] = {
    Try {
      val name: String = cardProto.getName
      val manaCost: Seq[Mana] = cardProto.getManaCostList.asScala.map(ManaAdapter.fromProto(_).get)
      val colors: Seq[Color] = cardProto.getColorsList.asScala.map(ColorAdapter.fromProto(_).get)
      val types: Seq[Type] = cardProto
          .getTypesList
          .asScala
          .map(TypeAdapter.fromProto(_).get)
      val cmc: Double = cardProto.getCmc

      require(name.nonEmpty, "Card names must not be empty")
      require(types.nonEmpty, "Cards must have at least one type")
      require(isValidCmc(cmc), "CMC must be positive and finite")

      name -> Card(manaCost, colors, types, cmc)
    }
  }

  def toProto(cacheEntry: (String, Card)): CacheProtos.Card = {
    val name = cacheEntry._1
    val card = cacheEntry._2

    require(name.nonEmpty, "Card names must not be empty")
    require(card.types.nonEmpty, "Cards must have at least one type")
    require(isValidCmc(card.cmc), "CMCs must be positive and finite")

    CacheProtos.Card.newBuilder()
        .setName(name)
        .addAllManaCost(cacheEntry._2.manaCost.map(ManaAdapter.toProto).asJava)
        .addAllColors(cacheEntry._2.colors.map(ColorAdapter.toProto).asJava)
        .addAllTypes(card.types.map(TypeAdapter.toProto).asJava)
        .setCmc(card.cmc)
        .build()
  }

  private def isValidCmc(value: Double): Boolean = !value.isNaN && !value.isInfinity && value >= 0
}
