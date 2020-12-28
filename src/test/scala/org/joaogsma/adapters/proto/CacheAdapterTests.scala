package org.joaogsma.adapters.proto

import org.joaogsma.entities.models.Card
import org.joaogsma.entities.models.Color
import org.joaogsma.entities.models.Mana
import org.joaogsma.entities.models.Type
import org.joaogsma.entities.models.proto.CacheProtos
import org.scalatest.Ignore
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import scala.jdk.CollectionConverters._
import scala.util.Success

@Ignore
class CacheAdapterTests extends AnyWordSpec with Matchers {
  "The fromProto function" when {
    "given a regular input" should {
      "convert it correctly" in {
//        val input: CacheProtos.Cache = CacheProtos.Cache.newBuilder()
//            .addAllCards(
//              Seq(
//                CacheProtos.Card.newBuilder()
//                    .setName("Brainstorm")
//                    .addAllManaCost(
//                      Seq(
//                        CacheProtos.ManaCostEntry.newBuilder()
//                            .setCount(1)
//                            .setType(CacheProtos.ManaType.BLUE_MANA)
//                            .build())
//                          .asJava)
//                    .addAllColors(Seq(CacheProtos.Color.BLUE).asJava)
//                    .addAllTypes(Seq(CacheProtos.CardType.INSTANT).asJava)
//                    .setCmc(1.0)
//                    .build(),
//                CacheProtos.Card.newBuilder()
//                    .setName("Ancestral Vision")
//                    .addAllColors(Seq(CacheProtos.Color.BLUE).asJava)
//                    .addAllTypes(Seq(CacheProtos.CardType.SORCERY).asJava)
//                    .setCmc(0.0)
//                    .build(),
//                CacheProtos.Card.newBuilder()
//                    .setName("Sol Ring")
//                    .addAllManaCost(
//                      Seq(
//                        CacheProtos.ManaCostEntry.newBuilder()
//                            .setCount(1)
//                            .setType(CacheProtos.ManaType.GENERIC_MANA)
//                            .build())
//                          .asJava)
//                    .addAllTypes(Seq(CacheProtos.CardType.ARTIFACT).asJava)
//                    .setCmc(1.0)
//                    .build())
//                  .asJava)
//            .build()
//
//        val expected: Seq[(String, Card)] = Seq(
//          Tuple2(
//            "Brainstorm",
//            Card(Seq(Mana.Blue(1)), Seq(Color.Blue), Seq(Type.Instant), 1.0)),
//          Tuple2(
//            "Ancestral Vision",
//            Card(Seq.empty, Seq(Color.Blue), Seq(Type.Sorcery), 0.0)),
//          Tuple2(
//            "Sol Ring",
//            Card(Seq(Mana.Generic(1)), Seq.empty, Seq(Type.Artifact), 1.0)))
//
//        CacheAdapter.fromProto(input) shouldEqual Success(expected)
      }
    }

    "given an empty cache" should {
      "convert it correctly" in {
//        (CacheAdapter.fromProto(CacheProtos.Cache.newBuilder().build())
//            shouldEqual Success(Seq.empty))
      }
    }

    "given a null cache" should {
      "return a failure" in {
//        CacheAdapter.fromProto(null).isFailure shouldBe true
      }
    }
  }

  "The toProto function" when {
    "given a regular input" should {
      "convert it correctly" in {
//        val input: Seq[(String, Card)] = Seq(
//          Tuple2(
//            "Brainstorm",
//            Card(Seq(Mana.Blue(1)), Seq(Color.Blue), Seq(Type.Instant), 1.0)),
//          Tuple2(
//            "Ancestral Vision",
//            Card(Seq.empty, Seq(Color.Blue), Seq(Type.Sorcery), 0.0)),
//          Tuple2(
//            "Sol Ring",
//            Card(Seq(Mana.Generic(1)), Seq.empty, Seq(Type.Artifact), 1.0)))
//
//        val expected: CacheProtos.Cache = CacheProtos.Cache.newBuilder()
//            .addAllCards(
//              Seq(
//                CacheProtos.Card.newBuilder()
//                    .setName("Brainstorm")
//                    .addAllManaCost(
//                      Seq(
//                        CacheProtos.ManaCostEntry.newBuilder()
//                            .setCount(1)
//                            .setType(CacheProtos.ManaType.BLUE_MANA)
//                            .build())
//                          .asJava)
//                    .addAllColors(Seq(CacheProtos.Color.BLUE).asJava)
//                    .addAllTypes(Seq(CacheProtos.CardType.INSTANT).asJava)
//                    .setCmc(1.0)
//                    .build(),
//                CacheProtos.Card.newBuilder()
//                    .setName("Ancestral Vision")
//                    .addAllColors(Seq(CacheProtos.Color.BLUE).asJava)
//                    .addAllTypes(Seq(CacheProtos.CardType.SORCERY).asJava)
//                    .setCmc(0.0)
//                    .build(),
//                CacheProtos.Card.newBuilder()
//                    .setName("Sol Ring")
//                    .addAllManaCost(
//                      Seq(
//                        CacheProtos.ManaCostEntry.newBuilder()
//                            .setCount(1)
//                            .setType(CacheProtos.ManaType.GENERIC_MANA)
//                            .build())
//                          .asJava)
//                    .addAllTypes(Seq(CacheProtos.CardType.ARTIFACT).asJava)
//                    .setCmc(1.0)
//                    .build())
//                  .asJava)
//            .build()
//
//        CacheAdapter.toProto(input) shouldEqual expected
      }
    }

    "given an empty cache" should {
      "convert it correctly" in {
//        CacheAdapter.toProto(Seq.empty) shouldEqual CacheProtos.Cache.newBuilder().build()
      }
    }

    "given a null cache" should {
      "throw an exception" in {
//        CacheAdapter.fromProto(null).isFailure shouldBe true
      }
    }
  }
}
