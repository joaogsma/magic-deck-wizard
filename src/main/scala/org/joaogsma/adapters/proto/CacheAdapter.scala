package org.joaogsma.adapters.proto

import org.joaogsma.models.Card
import org.joaogsma.models.proto.CacheProtos

import scala.collection.JavaConverters._
import scala.util.Try

object CacheAdapter {
  def fromProto(cacheProto: CacheProtos.Cache): Try[Seq[(String, Card)]] = {
    Try {
      cacheProto.getCardsList
          .asScala
          .map(CacheEntryAdapter.fromProto(_).get)
    }
  }

  def toProto(cache: TraversableOnce[(String, Card)]): CacheProtos.Cache = {
    CacheProtos.Cache.newBuilder()
        .addAllCards(cache.map(CacheEntryAdapter.toProto).toIterable.asJava)
        .build()
  }
}

