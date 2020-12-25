package org.joaogsma.adapters.proto

import org.joaogsma.models.Card
import org.joaogsma.models.Color
import org.joaogsma.models.Mana
import org.joaogsma.models.Type
import org.joaogsma.models.proto.CacheProtos
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import scala.jdk.CollectionConverters._
import scala.util.Failure
import scala.util.Success

class CacheEntryAdapterTests extends AnyWordSpec with Matchers {
  "The fromProto function" when {
    "given a regular card" should {
      "convert it correctly" in {
        val input: CacheProtos.Card = CacheProtos.Card.newBuilder()
            .setName("Brainstorm")
            .addAllManaCost(
              Seq(
                CacheProtos.ManaCostEntry.newBuilder()
                    .setCount(1)
                    .setType(CacheProtos.ManaType.BLUE_MANA)
                    .build())
                  .asJava)
            .addAllColors(Seq(CacheProtos.Color.BLUE).asJava)
            .addAllTypes(Seq(CacheProtos.CardType.INSTANT).asJava)
            .setCmc(1.0)
            .build()

        val expected: (String, Card) = Tuple2(
          "Brainstorm",
          Card(Seq(Mana.Blue(1)), Seq(Color.Blue), Seq(Type.Instant), 1.0))

        CacheEntryAdapter.fromProto(input) shouldBe Success(expected)
      }
    }

    "given a card with no mana cost" should {
      "convert it correctly" in {
        val input: CacheProtos.Card = CacheProtos.Card.newBuilder()
            .setName("Ancestral Vision")
            .addAllColors(Seq(CacheProtos.Color.BLUE).asJava)
            .addAllTypes(Seq(CacheProtos.CardType.SORCERY).asJava)
            .setCmc(0.0)
            .build()

        val expected: (String, Card) = Tuple2(
          "Ancestral Vision",
          Card(Seq.empty, Seq(Color.Blue), Seq(Type.Sorcery), 0.0))

        CacheEntryAdapter.fromProto(input) shouldBe Success(expected)
      }
    }

    "given a card with no colors" should {
      "convert it correctly" in {
        val input: CacheProtos.Card = CacheProtos.Card.newBuilder()
            .setName("Sol Ring")
            .addAllManaCost(
              Seq(
                CacheProtos.ManaCostEntry.newBuilder()
                    .setCount(1)
                    .setType(CacheProtos.ManaType.GENERIC_MANA)
                    .build())
                  .asJava)
            .addAllTypes(Seq(CacheProtos.CardType.ARTIFACT).asJava)
            .setCmc(1.0)
            .build()

        val expected: (String, Card) = Tuple2(
          "Sol Ring",
          Card(Seq(Mana.Generic(1)), Seq.empty, Seq(Type.Artifact), 1.0))

        CacheEntryAdapter.fromProto(input) shouldBe Success(expected)
      }
    }

    "given a card with no card types" should {
      "return a failure with an IllegalArgumentException" in {
        val input: CacheProtos.Card = CacheProtos.Card.newBuilder()
            .setName("some-name")
            .addAllManaCost(
              Seq(
                CacheProtos.ManaCostEntry.newBuilder()
                    .setCount(1)
                    .setType(CacheProtos.ManaType.BLUE_MANA)
                    .build())
                  .asJava)
            .addAllColors(Seq(CacheProtos.Color.BLUE).asJava)
            .setCmc(1.0)
            .build()

        val result = CacheEntryAdapter.fromProto(input)
        result.isFailure shouldBe true
        result.asInstanceOf[Failure[_]].exception.isInstanceOf[IllegalArgumentException]
            .shouldBe(true)
      }
    }

    "given a card with an empty name" should {
      "return a failure with an IllegalArgumentException" in {
        val input: CacheProtos.Card = CacheProtos.Card.newBuilder()
            .setName("")
            .addAllManaCost(
              Seq(
                CacheProtos.ManaCostEntry.newBuilder()
                    .setCount(1)
                    .setType(CacheProtos.ManaType.BLUE_MANA)
                    .build())
                  .asJava)
            .addAllColors(Seq(CacheProtos.Color.BLUE).asJava)
            .addAllTypes(Seq(CacheProtos.CardType.INSTANT).asJava)
            .setCmc(1.0)
            .build()

        val result = CacheEntryAdapter.fromProto(input)
        result.isFailure shouldBe true
        result.asInstanceOf[Failure[_]].exception.isInstanceOf[IllegalArgumentException]
            .shouldBe(true)
      }
    }

    "given a card with NaN as the CMC" should {
      "return a failure with an IllegalArgumentException" in {
        val input: CacheProtos.Card = CacheProtos.Card.newBuilder()
            .setName("some-name")
            .addAllManaCost(
              Seq(
                CacheProtos.ManaCostEntry.newBuilder()
                    .setCount(1)
                    .setType(CacheProtos.ManaType.BLUE_MANA)
                    .build())
                  .asJava)
            .addAllColors(Seq(CacheProtos.Color.BLUE).asJava)
            .addAllTypes(Seq(CacheProtos.CardType.INSTANT).asJava)
            .setCmc(Double.NaN)
            .build()

        val result = CacheEntryAdapter.fromProto(input)
        result.isFailure shouldBe true
        result.asInstanceOf[Failure[_]].exception.isInstanceOf[IllegalArgumentException]
            .shouldBe(true)
      }
    }

    "given a card with +/- infinity as the CMC" should {
      "return a failure with an IllegalArgumentException" in {
        val inputPosInf: CacheProtos.Card = CacheProtos.Card.newBuilder()
            .setName("some-name")
            .addAllManaCost(
              Seq(
                CacheProtos.ManaCostEntry.newBuilder()
                    .setCount(1)
                    .setType(CacheProtos.ManaType.BLUE_MANA)
                    .build())
                  .asJava)
            .addAllColors(Seq(CacheProtos.Color.BLUE).asJava)
            .addAllTypes(Seq(CacheProtos.CardType.INSTANT).asJava)
            .setCmc(Double.PositiveInfinity)
            .build()

        val inputNegInf: CacheProtos.Card = CacheProtos.Card.newBuilder()
            .setName("some-name")
            .addAllManaCost(
              Seq(
                CacheProtos.ManaCostEntry.newBuilder()
                    .setCount(1)
                    .setType(CacheProtos.ManaType.BLUE_MANA)
                    .build())
                  .asJava)
            .addAllColors(Seq(CacheProtos.Color.BLUE).asJava)
            .addAllTypes(Seq(CacheProtos.CardType.INSTANT).asJava)
            .setCmc(Double.NegativeInfinity)
            .build()

        val resultPosInf = CacheEntryAdapter.fromProto(inputPosInf)
        resultPosInf.isFailure shouldBe true
        resultPosInf.asInstanceOf[Failure[_]].exception.isInstanceOf[IllegalArgumentException]
            .shouldBe(true)

        val resultNegInf = CacheEntryAdapter.fromProto(inputNegInf)
        resultNegInf.isFailure shouldBe true
        resultNegInf.asInstanceOf[Failure[_]].exception.isInstanceOf[IllegalArgumentException]
            .shouldBe(true)
      }
    }

    "given a card with a negative CMC" should {
      "return a failure with an IllegalArgumentException" in {
        val input: CacheProtos.Card = CacheProtos.Card.newBuilder()
            .setName("some-name")
            .addAllManaCost(
              Seq(
                CacheProtos.ManaCostEntry.newBuilder()
                    .setCount(1)
                    .setType(CacheProtos.ManaType.BLUE_MANA)
                    .build())
                  .asJava)
            .addAllColors(Seq(CacheProtos.Color.BLUE).asJava)
            .addAllTypes(Seq(CacheProtos.CardType.INSTANT).asJava)
            .setCmc(-1)
            .build()

        val result = CacheEntryAdapter.fromProto(input)
        result.isFailure shouldBe true
        result.asInstanceOf[Failure[_]].exception.isInstanceOf[IllegalArgumentException]
            .shouldBe(true)
      }
    }

    "given a null card" should {
      "return a failure with a NullPointerException" in {
        val result = CacheEntryAdapter.fromProto(null)
        result.isFailure shouldBe true
        result.asInstanceOf[Failure[_]].exception.isInstanceOf[NullPointerException]
            .shouldBe(true)
      }
    }
  }

  "The toProto function" when {
    "given a regular card" should {
      "convert it correctly" in {
        val input: (String, Card) = Tuple2(
          "Brainstorm",
          Card(Seq(Mana.Blue(1)), Seq(Color.Blue), Seq(Type.Instant), 1.0))

        val expected: CacheProtos.Card = CacheProtos.Card.newBuilder()
            .setName("Brainstorm")
            .addAllManaCost(
              Seq(
                CacheProtos.ManaCostEntry.newBuilder()
                    .setCount(1)
                    .setType(CacheProtos.ManaType.BLUE_MANA)
                    .build())
                  .asJava)
            .addAllColors(Seq(CacheProtos.Color.BLUE).asJava)
            .addAllTypes(Seq(CacheProtos.CardType.INSTANT).asJava)
            .setCmc(1.0)
            .build()

        CacheEntryAdapter.toProto(input) shouldBe expected
      }
    }

    "given a card with no mana cost" should {
      "convert it correctly" in {
        val input: (String, Card) = Tuple2(
          "Ancestral Vision",
          Card(Seq.empty, Seq(Color.Blue), Seq(Type.Sorcery), 0.0))

        val expected: CacheProtos.Card = CacheProtos.Card.newBuilder()
            .setName("Ancestral Vision")
            .addAllColors(Seq(CacheProtos.Color.BLUE).asJava)
            .addAllTypes(Seq(CacheProtos.CardType.SORCERY).asJava)
            .setCmc(0.0)
            .build()

        CacheEntryAdapter.toProto(input) shouldBe expected
      }
    }

    "given a card with no colors" should {
      "convert it correctly" in {
        val input: (String, Card) = Tuple2(
          "Sol Ring",
          Card(Seq(Mana.Generic(1)), Seq.empty, Seq(Type.Artifact), 1.0))

        val expected: CacheProtos.Card = CacheProtos.Card.newBuilder()
            .setName("Sol Ring")
            .addAllManaCost(
              Seq(
                CacheProtos.ManaCostEntry.newBuilder()
                    .setCount(1)
                    .setType(CacheProtos.ManaType.GENERIC_MANA)
                    .build())
                  .asJava)
            .addAllTypes(Seq(CacheProtos.CardType.ARTIFACT).asJava)
            .setCmc(1.0)
            .build()

        CacheEntryAdapter.toProto(input) shouldBe expected
      }
    }

    "given a card with no card types" should {
      "throw an IllegalArgumentException" in {
        val input: (String, Card) = Tuple2(
          "Sol Ring-ish",
          Card(Seq(Mana.Generic(1)), Seq.empty, Seq.empty, 1.0))
        assertThrows[IllegalArgumentException](CacheEntryAdapter.toProto(input))
      }
    }

    "given a card with an empty name" should {
      "throw an IllegalArgumentException" in {
        val input: (String, Card) = Tuple2(
          "",
          Card(Seq(Mana.Generic(1)), Seq.empty, Seq(Type.Artifact), 1.0))
        assertThrows[IllegalArgumentException](CacheEntryAdapter.toProto(input))
      }
    }

    "given a card with NaN as the CMC" should {
      "throw an IllegalArgumentException" in {
        val input: (String, Card) = Tuple2(
          "Sol Ring-ish",
          Card(Seq(Mana.Generic(1)), Seq.empty, Seq(Type.Artifact), Double.NaN))
        assertThrows[IllegalArgumentException](CacheEntryAdapter.toProto(input))
      }
    }

    "given a card with +/- infinity as the CMC" should {
      "throw an IllegalArgumentException" in {
        val inputPosInf: (String, Card) = Tuple2(
          "Sol Ring-ish",
          Card(Seq(Mana.Generic(1)), Seq.empty, Seq(Type.Artifact), Double.PositiveInfinity))
        val inputNegInf: (String, Card) = Tuple2(
          "Sol Ring-ish",
          Card(Seq(Mana.Generic(1)), Seq.empty, Seq(Type.Artifact), Double.NegativeInfinity))
        assertThrows[IllegalArgumentException](CacheEntryAdapter.toProto(inputPosInf))
        assertThrows[IllegalArgumentException](CacheEntryAdapter.toProto(inputNegInf))
      }
    }

    "given a card with a negative CMC" should {
      "throw an IllegalArgumentException" in {
        val input: (String, Card) = Tuple2(
          "Sol Ring-ish",
          Card(Seq(Mana.Generic(1)), Seq.empty, Seq(Type.Artifact), -1))
        assertThrows[IllegalArgumentException](CacheEntryAdapter.toProto(input))
      }
    }

    "given a null card" should {
      "throw a NullPointerException" in {
        assertThrows[NullPointerException](CacheEntryAdapter.toProto(null))
      }
    }
  }
}
