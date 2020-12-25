package org.joaogsma.adapters.proto

import org.joaogsma.models.Card
import org.joaogsma.models.proto.CacheProtos

import scala.jdk.CollectionConverters._
import scala.util.Try

object CacheAdapter {
  def fromProto(cacheProto: CacheProtos.Cache): Try[Seq[(String, Card)]] = {
    Try {
      cacheProto.getCardsList
          .asScala
          .map(CacheEntryAdapter.fromProto(_).get)
          .toSeq
    }
  }

  def toProto(cache: IterableOnce[(String, Card)]): CacheProtos.Cache = {
    CacheProtos.Cache.newBuilder()
        .addAllCards(cache.iterator.map(CacheEntryAdapter.toProto).to(Iterable).asJava)
        .build()
  }
}

