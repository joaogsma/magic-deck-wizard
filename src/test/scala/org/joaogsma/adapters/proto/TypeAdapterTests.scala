package org.joaogsma.adapters.proto

import org.joaogsma.models.proto.CacheProtos
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import scala.util.Success
import org.joaogsma.models.Type

class TypeAdapterTests extends AnyWordSpec with Matchers {
  "The fromProto function" when {
    "given a creature card" should {
      "convert it correctly" in {
        TypeAdapter.fromProto(CacheProtos.CardType.CREATURE) shouldEqual Success(Type.Creature)
      }
    }

    "given an instant card" should {
      "convert it correctly" in {
        TypeAdapter.fromProto(CacheProtos.CardType.INSTANT) shouldEqual Success(Type.Instant)
      }
    }

    "given a sorcery card" should {
      "convert it correctly" in {
        TypeAdapter.fromProto(CacheProtos.CardType.SORCERY) shouldEqual Success(Type.Sorcery)
      }
    }

    "given an artifact card" should {
      "convert it correctly" in {
        TypeAdapter.fromProto(CacheProtos.CardType.ARTIFACT) shouldEqual Success(Type.Artifact)
      }
    }

    "given a enchantment card" should {
      "convert it correctly" in {
        (TypeAdapter.fromProto(CacheProtos.CardType.ENCHANTMENT)
            shouldEqual Success(Type.Enchantment))
      }
    }

    "given a planeswalker card" should {
      "convert it correctly" in {
        (TypeAdapter.fromProto(CacheProtos.CardType.PLANESWALKER)
            shouldEqual Success(Type.Planeswalker))
      }
    }

    "given a land card" should {
      "convert it correctly" in {
        TypeAdapter.fromProto(CacheProtos.CardType.LAND) shouldEqual Success(Type.Land)
      }
    }

    "given an unrecognized value" should {
      "return failure" in {
        TypeAdapter.fromProto(CacheProtos.CardType.UNRECOGNIZED).isFailure shouldBe true
      }
    }
  }

  "The toProto function" when {
    "given a creature card" should {
      "convert it correctly" in {
        TypeAdapter.toProto(Type.Creature) shouldBe CacheProtos.CardType.CREATURE
      }
    }

    "given an instant card" should {
      "convert it correctly" in {
        TypeAdapter.toProto(Type.Instant) shouldBe CacheProtos.CardType.INSTANT
      }
    }

    "given a sorcery card" should {
      "convert it correctly" in {
        TypeAdapter.toProto(Type.Sorcery) shouldBe CacheProtos.CardType.SORCERY
      }
    }

    "given an artifact card" should {
      "convert it correctly" in {
        TypeAdapter.toProto(Type.Artifact) shouldBe CacheProtos.CardType.ARTIFACT
      }
    }

    "given a enchantment card" should {
      "convert it correctly" in {
        TypeAdapter.toProto(Type.Enchantment) shouldBe CacheProtos.CardType.ENCHANTMENT
      }
    }

    "given a planeswalker card" should {
      "convert it correctly" in {
        TypeAdapter.toProto(Type.Planeswalker) shouldBe CacheProtos.CardType.PLANESWALKER
      }
    }

    "given a land card" should {
      "convert it correctly" in {
        TypeAdapter.toProto(Type.Land) shouldBe CacheProtos.CardType.LAND
      }
    }
  }
}
