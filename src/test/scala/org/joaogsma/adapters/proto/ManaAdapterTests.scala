package org.joaogsma.adapters.proto

import org.joaogsma.models.proto.CacheProtos
import org.joaogsma.models.Mana
import org.scalatest.Matchers
import org.scalatest.WordSpec

import scala.util.Random
import scala.math.abs
import scala.util.Success

class ManaAdapterTests extends WordSpec with Matchers {
  "The fromProto function" when {
    "given a X mana cost" should {
      "convert it correctly" in {
        val count: Int = randomCount
        val manaType: CacheProtos.ManaType = CacheProtos.ManaType.X_MANA
        val manaCostEntry: CacheProtos.ManaCostEntry =
            CacheProtos.ManaCostEntry.newBuilder().setCount(count).setType(manaType).build()
        ManaAdapter.fromProto(manaCostEntry) shouldEqual Success(Mana.X(count))
      }
    }

    "given a generic mana cost" should {
      "convert it correctly" in {
        val count: Int = randomCount
        val manaType: CacheProtos.ManaType = CacheProtos.ManaType.GENERIC_MANA
        val manaCostEntry: CacheProtos.ManaCostEntry =
          CacheProtos.ManaCostEntry.newBuilder().setCount(count).setType(manaType).build()
        ManaAdapter.fromProto(manaCostEntry) shouldEqual Success(Mana.Generic(count))
      }
    }

    "given a colorless mana cost" should {
      "convert it correctly" in {
        val count: Int = randomCount
        val manaType: CacheProtos.ManaType = CacheProtos.ManaType.COLORLESS_MANA
        val manaCostEntry: CacheProtos.ManaCostEntry =
          CacheProtos.ManaCostEntry.newBuilder().setCount(count).setType(manaType).build()
        ManaAdapter.fromProto(manaCostEntry) shouldEqual Success(Mana.Colorless(count))
      }
    }

    "given a white mana cost" should {
      "convert it correctly" in {
        val count: Int = randomCount
        val manaType: CacheProtos.ManaType = CacheProtos.ManaType.WHITE_MANA
        val manaCostEntry: CacheProtos.ManaCostEntry =
          CacheProtos.ManaCostEntry.newBuilder().setCount(count).setType(manaType).build()
        ManaAdapter.fromProto(manaCostEntry) shouldEqual Success(Mana.White(count))
      }
    }

    "given a blue mana cost" should {
      "convert it correctly" in {
        val count: Int = randomCount
        val manaType: CacheProtos.ManaType = CacheProtos.ManaType.BLUE_MANA
        val manaCostEntry: CacheProtos.ManaCostEntry =
          CacheProtos.ManaCostEntry.newBuilder().setCount(count).setType(manaType).build()
        ManaAdapter.fromProto(manaCostEntry) shouldEqual Success(Mana.Blue(count))
      }
    }

    "given a black mana cost" should {
      "convert it correctly" in {
        val count: Int = randomCount
        val manaType: CacheProtos.ManaType = CacheProtos.ManaType.BLACK_MANA
        val manaCostEntry: CacheProtos.ManaCostEntry =
          CacheProtos.ManaCostEntry.newBuilder().setCount(count).setType(manaType).build()
        ManaAdapter.fromProto(manaCostEntry) shouldEqual Success(Mana.Black(count))
      }
    }

    "given a red mana cost" should {
      "convert it correctly" in {
        val count: Int = randomCount
        val manaType: CacheProtos.ManaType = CacheProtos.ManaType.RED_MANA
        val manaCostEntry: CacheProtos.ManaCostEntry =
          CacheProtos.ManaCostEntry.newBuilder().setCount(count).setType(manaType).build()
        ManaAdapter.fromProto(manaCostEntry) shouldEqual Success(Mana.Red(count))
      }
    }

    "given a green mana cost" should {
      "convert it correctly" in {
        val count: Int = randomCount
        val manaType: CacheProtos.ManaType = CacheProtos.ManaType.GREEN_MANA
        val manaCostEntry: CacheProtos.ManaCostEntry =
          CacheProtos.ManaCostEntry.newBuilder().setCount(count).setType(manaType).build()
        ManaAdapter.fromProto(manaCostEntry) shouldEqual Success(Mana.Green(count))
      }
    }

    "given a hybrid mono white mana cost" should {
      "convert it correctly" in {
        val count: Int = randomCount
        val manaType: CacheProtos.ManaType = CacheProtos.ManaType.HYBRID_MONO_WHITE_MANA
        val manaCostEntry: CacheProtos.ManaCostEntry =
          CacheProtos.ManaCostEntry.newBuilder().setCount(count).setType(manaType).build()
        ManaAdapter.fromProto(manaCostEntry) shouldEqual Success(Mana.HybridMonoWhite(count))
      }
    }

    "given a hybrid mono blue mana cost" should {
      "convert it correctly" in {
        val count: Int = randomCount
        val manaType: CacheProtos.ManaType = CacheProtos.ManaType.HYBRID_MONO_BLUE_MANA
        val manaCostEntry: CacheProtos.ManaCostEntry =
          CacheProtos.ManaCostEntry.newBuilder().setCount(count).setType(manaType).build()
        ManaAdapter.fromProto(manaCostEntry) shouldEqual Success(Mana.HybridMonoBlue(count))
      }
    }

    "given a hybrid mono black mana cost" should {
      "convert it correctly" in {
        val count: Int = randomCount
        val manaType: CacheProtos.ManaType = CacheProtos.ManaType.HYBRID_MONO_BLACK_MANA
        val manaCostEntry: CacheProtos.ManaCostEntry =
          CacheProtos.ManaCostEntry.newBuilder().setCount(count).setType(manaType).build()
        ManaAdapter.fromProto(manaCostEntry) shouldEqual Success(Mana.HybridMonoBlack(count))
      }
    }

    "given a hybrid mono red mana cost" should {
      "convert it correctly" in {
        val count: Int = randomCount
        val manaType: CacheProtos.ManaType = CacheProtos.ManaType.HYBRID_MONO_RED_MANA
        val manaCostEntry: CacheProtos.ManaCostEntry =
          CacheProtos.ManaCostEntry.newBuilder().setCount(count).setType(manaType).build()
        ManaAdapter.fromProto(manaCostEntry) shouldEqual Success(Mana.HybridMonoRed(count))
      }
    }

    "given a hybrid mono green mana cost" should {
      "convert it correctly" in {
        val count: Int = randomCount
        val manaType: CacheProtos.ManaType = CacheProtos.ManaType.HYBRID_MONO_GREEN_MANA
        val manaCostEntry: CacheProtos.ManaCostEntry =
          CacheProtos.ManaCostEntry.newBuilder().setCount(count).setType(manaType).build()
        ManaAdapter.fromProto(manaCostEntry) shouldEqual Success(Mana.HybridMonoGreen(count))
      }
    }

    "given a hybrid white-blue mana cost" should {
      "convert it correctly" in {
        val count: Int = randomCount
        val manaType: CacheProtos.ManaType = CacheProtos.ManaType.HYBRID_WHITE_BLUE_MANA
        val manaCostEntry: CacheProtos.ManaCostEntry =
          CacheProtos.ManaCostEntry.newBuilder().setCount(count).setType(manaType).build()
        ManaAdapter.fromProto(manaCostEntry) shouldEqual Success(Mana.HybridWhiteBlue(count))
      }
    }

    "given a hybrid white-black mana cost" should {
      "convert it correctly" in {
        val count: Int = randomCount
        val manaType: CacheProtos.ManaType = CacheProtos.ManaType.HYBRID_WHITE_BLACK_MANA
        val manaCostEntry: CacheProtos.ManaCostEntry =
          CacheProtos.ManaCostEntry.newBuilder().setCount(count).setType(manaType).build()
        ManaAdapter.fromProto(manaCostEntry) shouldEqual Success(Mana.HybridWhiteBlack(count))
      }
    }

    "given a hybrid white-red mana cost" should {
      "convert it correctly" in {
        val count: Int = randomCount
        val manaType: CacheProtos.ManaType = CacheProtos.ManaType.HYBRID_WHITE_RED_MANA
        val manaCostEntry: CacheProtos.ManaCostEntry =
          CacheProtos.ManaCostEntry.newBuilder().setCount(count).setType(manaType).build()
        ManaAdapter.fromProto(manaCostEntry) shouldEqual Success(Mana.HybridWhiteRed(count))
      }
    }

    "given a hybrid white-green mana cost" should {
      "convert it correctly" in {
        val count: Int = randomCount
        val manaType: CacheProtos.ManaType = CacheProtos.ManaType.HYBRID_WHITE_GREEN_MANA
        val manaCostEntry: CacheProtos.ManaCostEntry =
          CacheProtos.ManaCostEntry.newBuilder().setCount(count).setType(manaType).build()
        ManaAdapter.fromProto(manaCostEntry) shouldEqual Success(Mana.HybridWhiteGreen(count))
      }
    }

    "given a hybrid blue-black mana cost" should {
      "convert it correctly" in {
        val count: Int = randomCount
        val manaType: CacheProtos.ManaType = CacheProtos.ManaType.HYBRID_BLUE_BLACK_MANA
        val manaCostEntry: CacheProtos.ManaCostEntry =
          CacheProtos.ManaCostEntry.newBuilder().setCount(count).setType(manaType).build()
        ManaAdapter.fromProto(manaCostEntry) shouldEqual Success(Mana.HybridBlueBlack(count))
      }
    }

    "given a hybrid blue-red  mana cost" should {
      "convert it correctly" in {
        val count: Int = randomCount
        val manaType: CacheProtos.ManaType = CacheProtos.ManaType.HYBRID_BLUE_RED_MANA
        val manaCostEntry: CacheProtos.ManaCostEntry =
          CacheProtos.ManaCostEntry.newBuilder().setCount(count).setType(manaType).build()
        ManaAdapter.fromProto(manaCostEntry) shouldEqual Success(Mana.HybridBlueRed(count))
      }
    }

    "given a hybrid blue-green mana cost" should {
      "convert it correctly" in {
        val count: Int = randomCount
        val manaType: CacheProtos.ManaType = CacheProtos.ManaType.HYBRID_BLUE_GREEN_MANA
        val manaCostEntry: CacheProtos.ManaCostEntry =
          CacheProtos.ManaCostEntry.newBuilder().setCount(count).setType(manaType).build()
        ManaAdapter.fromProto(manaCostEntry) shouldEqual Success(Mana.HybridBlueGreen(count))
      }
    }

    "given a hybrid black-red mana cost" should {
      "convert it correctly" in {
        val count: Int = randomCount
        val manaType: CacheProtos.ManaType = CacheProtos.ManaType.HYBRID_BLACK_RED_MANA
        val manaCostEntry: CacheProtos.ManaCostEntry =
          CacheProtos.ManaCostEntry.newBuilder().setCount(count).setType(manaType).build()
        ManaAdapter.fromProto(manaCostEntry) shouldEqual Success(Mana.HybridBlackRed(count))
      }
    }

    "given a hybrid black-green mana cost" should {
      "convert it correctly" in {
        val count: Int = randomCount
        val manaType: CacheProtos.ManaType = CacheProtos.ManaType.HYBRID_BLACK_GREEN_MANA
        val manaCostEntry: CacheProtos.ManaCostEntry =
          CacheProtos.ManaCostEntry.newBuilder().setCount(count).setType(manaType).build()
        ManaAdapter.fromProto(manaCostEntry) shouldEqual Success(Mana.HybridBlackGreen(count))
      }
    }

    "given a hybrid red-green mana cost" should {
      "convert it correctly" in {
        val count: Int = randomCount
        val manaType: CacheProtos.ManaType = CacheProtos.ManaType.HYBRID_RED_GREEN_MANA
        val manaCostEntry: CacheProtos.ManaCostEntry =
          CacheProtos.ManaCostEntry.newBuilder().setCount(count).setType(manaType).build()
        ManaAdapter.fromProto(manaCostEntry) shouldEqual Success(Mana.HybridRedGreen(count))
      }
    }

    "given a phyrexian white mana cost" should {
      "convert it correctly" in {
        val count: Int = randomCount
        val manaType: CacheProtos.ManaType = CacheProtos.ManaType.PHYREXIAN_WHITE_MANA
        val manaCostEntry: CacheProtos.ManaCostEntry =
          CacheProtos.ManaCostEntry.newBuilder().setCount(count).setType(manaType).build()
        ManaAdapter.fromProto(manaCostEntry) shouldEqual Success(Mana.PhyrexianWhite(count))
      }
    }

    "given a phyrexian blue mana cost" should {
      "convert it correctly" in {
        val count: Int = randomCount
        val manaType: CacheProtos.ManaType = CacheProtos.ManaType.PHYREXIAN_BLUE_MANA
        val manaCostEntry: CacheProtos.ManaCostEntry =
          CacheProtos.ManaCostEntry.newBuilder().setCount(count).setType(manaType).build()
        ManaAdapter.fromProto(manaCostEntry) shouldEqual Success(Mana.PhyrexianBlue(count))
      }
    }

    "given a phyrexian black mana cost" should {
      "convert it correctly" in {
        val count: Int = randomCount
        val manaType: CacheProtos.ManaType = CacheProtos.ManaType.PHYREXIAN_BLACK_MANA
        val manaCostEntry: CacheProtos.ManaCostEntry =
          CacheProtos.ManaCostEntry.newBuilder().setCount(count).setType(manaType).build()
        ManaAdapter.fromProto(manaCostEntry) shouldEqual Success(Mana.PhyrexianBlack(count))
      }
    }

    "given a phyrexian red mana cost" should {
      "convert it correctly" in {
        val count: Int = randomCount
        val manaType: CacheProtos.ManaType = CacheProtos.ManaType.PHYREXIAN_RED_MANA
        val manaCostEntry: CacheProtos.ManaCostEntry =
          CacheProtos.ManaCostEntry.newBuilder().setCount(count).setType(manaType).build()
        ManaAdapter.fromProto(manaCostEntry) shouldEqual Success(Mana.PhyrexianRed(count))
      }
    }

    "given a phyrexian green mana cost" should {
      "convert it correctly" in {
        val count: Int = randomCount
        val manaType: CacheProtos.ManaType = CacheProtos.ManaType.PHYREXIAN_GREEN_MANA
        val manaCostEntry: CacheProtos.ManaCostEntry =
          CacheProtos.ManaCostEntry.newBuilder().setCount(count).setType(manaType).build()
        ManaAdapter.fromProto(manaCostEntry) shouldEqual Success(Mana.PhyrexianGreen(count))
      }
    }

    "given an unrecognized mana type" should {
      "return a Failure" in {
        val count: Int = randomCount
        val manaType: CacheProtos.ManaType = CacheProtos.ManaType.UNRECOGNIZED
        val manaCostEntry: CacheProtos.ManaCostEntry =
          CacheProtos.ManaCostEntry.newBuilder().setCount(count).setTypeValue(-1).build()
        ManaAdapter.fromProto(manaCostEntry).isFailure shouldBe true
      }
    }

    "given a default message as the mana cost" should {
      "return a Failure" in {
        val manaCostEntry: CacheProtos.ManaCostEntry =
            CacheProtos.ManaCostEntry.newBuilder().build()
        ManaAdapter.fromProto(manaCostEntry).isFailure shouldBe true
      }
    }

    "given a mana cost with count less than 0" should {
      "return a Failure" in {
        val count: Int = randomCount * -1
        val manaType: CacheProtos.ManaType = CacheProtos.ManaType.BLUE_MANA
        val manaCostEntry: CacheProtos.ManaCostEntry =
          CacheProtos.ManaCostEntry.newBuilder().setCount(count).setType(manaType).build()
        ManaAdapter.fromProto(manaCostEntry).isFailure shouldBe true
      }
    }
  }

  "the toProto function" when {
    "given a X mana cost" should {
      "convert it correctly" in {
        val count: Int = randomCount
        ManaAdapter.toProto(Mana.X(count)).shouldEqual(
          CacheProtos.ManaCostEntry.newBuilder()
              .setCount(count)
              .setType(CacheProtos.ManaType.X_MANA)
              .build())
      }
    }

    "given a generic mana cost" should {
      "convert it correctly" in {
        val count: Int = randomCount
        ManaAdapter.toProto(Mana.Generic(count)).shouldEqual(
          CacheProtos.ManaCostEntry.newBuilder()
              .setCount(count)
              .setType(CacheProtos.ManaType.GENERIC_MANA)
              .build())
      }
    }

    "given a colorless mana cost" should {
      "convert it correctly" in {
        val count: Int = randomCount
        ManaAdapter.toProto(Mana.Colorless(count)).shouldEqual(
          CacheProtos.ManaCostEntry.newBuilder()
              .setCount(count)
              .setType(CacheProtos.ManaType.COLORLESS_MANA)
              .build())
      }
    }

    "given a white mana cost" should {
      "convert it correctly" in {
        val count: Int = randomCount
        ManaAdapter.toProto(Mana.White(count)).shouldEqual(
          CacheProtos.ManaCostEntry.newBuilder()
              .setCount(count)
              .setType(CacheProtos.ManaType.WHITE_MANA)
              .build())
      }
    }

    "given a blue mana cost" should {
      "convert it correctly" in {
        val count: Int = randomCount
        ManaAdapter.toProto(Mana.Blue(count)).shouldEqual(
          CacheProtos.ManaCostEntry.newBuilder()
              .setCount(count)
              .setType(CacheProtos.ManaType.BLUE_MANA)
              .build())
      }
    }

    "given a black mana cost" should {
      "convert it correctly" in {
        val count: Int = randomCount
        ManaAdapter.toProto(Mana.Black(count)).shouldEqual(
          CacheProtos.ManaCostEntry.newBuilder()
              .setCount(count)
              .setType(CacheProtos.ManaType.BLACK_MANA)
              .build())
      }
    }

    "given a red mana cost" should {
      "convert it correctly" in {
        val count: Int = randomCount
        ManaAdapter.toProto(Mana.Red(count)).shouldEqual(
          CacheProtos.ManaCostEntry.newBuilder()
              .setCount(count)
              .setType(CacheProtos.ManaType.RED_MANA)
              .build())
      }
    }

    "given a green mana cost" should {
      "convert it correctly" in {
        val count: Int = randomCount
        ManaAdapter.toProto(Mana.Green(count)).shouldEqual(
          CacheProtos.ManaCostEntry.newBuilder()
              .setCount(count)
              .setType(CacheProtos.ManaType.GREEN_MANA)
              .build())
      }
    }

    "given a hybrid mono white mana cost" should {
      "convert it correctly" in {
        val count: Int = randomCount
        ManaAdapter.toProto(Mana.HybridMonoWhite(count)).shouldEqual(
          CacheProtos.ManaCostEntry.newBuilder()
              .setCount(count)
              .setType(CacheProtos.ManaType.HYBRID_MONO_WHITE_MANA)
              .build())
      }
    }

    "given a hybrid mono blue mana cost" should {
      "convert it correctly" in {
        val count: Int = randomCount
        ManaAdapter.toProto(Mana.HybridMonoBlue(count)).shouldEqual(
          CacheProtos.ManaCostEntry.newBuilder()
              .setCount(count)
              .setType(CacheProtos.ManaType.HYBRID_MONO_BLUE_MANA)
              .build())
      }
    }

    "given a hybrid mono black mana cost" should {
      "convert it correctly" in {
        val count: Int = randomCount
        ManaAdapter.toProto(Mana.HybridMonoBlack(count)).shouldEqual(
          CacheProtos.ManaCostEntry.newBuilder()
              .setCount(count)
              .setType(CacheProtos.ManaType.HYBRID_MONO_BLACK_MANA)
              .build())
      }
    }

    "given a hybrid mono red mana cost" should {
      "convert it correctly" in {
        val count: Int = randomCount
        ManaAdapter.toProto(Mana.HybridMonoRed(count)).shouldEqual(
          CacheProtos.ManaCostEntry.newBuilder()
              .setCount(count)
              .setType(CacheProtos.ManaType.HYBRID_MONO_RED_MANA)
              .build())
      }
    }

    "given a hybrid mono green mana cost" should {
      "convert it correctly" in {
        val count: Int = randomCount
        ManaAdapter.toProto(Mana.HybridMonoGreen(count)).shouldEqual(
          CacheProtos.ManaCostEntry.newBuilder()
              .setCount(count)
              .setType(CacheProtos.ManaType.HYBRID_MONO_GREEN_MANA)
              .build())
      }
    }

    "given a hybrid white-blue mana cost" should {
      "convert it correctly" in {
        val count: Int = randomCount
        ManaAdapter.toProto(Mana.HybridWhiteBlue(count)).shouldEqual(
          CacheProtos.ManaCostEntry.newBuilder()
              .setCount(count)
              .setType(CacheProtos.ManaType.HYBRID_WHITE_BLUE_MANA)
              .build())
      }
    }

    "given a hybrid white-black mana cost" should {
      "convert it correctly" in {
        val count: Int = randomCount
        ManaAdapter.toProto(Mana.HybridWhiteBlack(count)).shouldEqual(
          CacheProtos.ManaCostEntry.newBuilder()
              .setCount(count)
              .setType(CacheProtos.ManaType.HYBRID_WHITE_BLACK_MANA)
              .build())
      }
    }

    "given a hybrid white-red mana cost" should {
      "convert it correctly" in {
        val count: Int = randomCount
        ManaAdapter.toProto(Mana.HybridWhiteRed(count)).shouldEqual(
          CacheProtos.ManaCostEntry.newBuilder()
              .setCount(count)
              .setType(CacheProtos.ManaType.HYBRID_WHITE_RED_MANA)
              .build())
      }
    }

    "given a hybrid white-green mana cost" should {
      "convert it correctly" in {
        val count: Int = randomCount
        ManaAdapter.toProto(Mana.HybridWhiteGreen(count)).shouldEqual(
          CacheProtos.ManaCostEntry.newBuilder()
              .setCount(count)
              .setType(CacheProtos.ManaType.HYBRID_WHITE_GREEN_MANA)
              .build())
      }
    }

    "given a hybrid blue-black mana cost" should {
      "convert it correctly" in {
        val count: Int = randomCount
        ManaAdapter.toProto(Mana.HybridBlueBlack(count)).shouldEqual(
          CacheProtos.ManaCostEntry.newBuilder()
              .setCount(count)
              .setType(CacheProtos.ManaType.HYBRID_BLUE_BLACK_MANA)
              .build())
      }
    }

    "given a hybrid blue-red  mana cost" should {
      "convert it correctly" in {
        val count: Int = randomCount
        ManaAdapter.toProto(Mana.HybridBlueRed(count)).shouldEqual(
          CacheProtos.ManaCostEntry.newBuilder()
              .setCount(count)
              .setType(CacheProtos.ManaType.HYBRID_BLUE_RED_MANA)
              .build())
      }
    }

    "given a hybrid blue-green mana cost" should {
      "convert it correctly" in {
        val count: Int = randomCount
        ManaAdapter.toProto(Mana.HybridBlueGreen(count)).shouldEqual(
          CacheProtos.ManaCostEntry.newBuilder()
              .setCount(count)
              .setType(CacheProtos.ManaType.HYBRID_BLUE_GREEN_MANA)
              .build())
      }
    }

    "given a hybrid black-red mana cost" should {
      "convert it correctly" in {
        val count: Int = randomCount
        ManaAdapter.toProto(Mana.HybridBlackRed(count)).shouldEqual(
          CacheProtos.ManaCostEntry.newBuilder()
              .setCount(count)
              .setType(CacheProtos.ManaType.HYBRID_BLACK_RED_MANA)
              .build())
      }
    }

    "given a hybrid black-green mana cost" should {
      "convert it correctly" in {
        val count: Int = randomCount
        ManaAdapter.toProto(Mana.HybridBlackGreen(count)).shouldEqual(
          CacheProtos.ManaCostEntry.newBuilder()
              .setCount(count)
              .setType(CacheProtos.ManaType.HYBRID_BLACK_GREEN_MANA)
              .build())
      }
    }

    "given a hybrid red-green mana cost" should {
      "convert it correctly" in {
        val count: Int = randomCount
        ManaAdapter.toProto(Mana.HybridRedGreen(count)).shouldEqual(
          CacheProtos.ManaCostEntry.newBuilder()
              .setCount(count)
              .setType(CacheProtos.ManaType.HYBRID_RED_GREEN_MANA)
              .build())
      }
    }

    "given a phyrexian white mana cost" should {
      "convert it correctly" in {
        val count: Int = randomCount
        ManaAdapter.toProto(Mana.PhyrexianWhite(count)).shouldEqual(
          CacheProtos.ManaCostEntry.newBuilder()
              .setCount(count)
              .setType(CacheProtos.ManaType.PHYREXIAN_WHITE_MANA)
              .build())
      }
    }

    "given a phyrexian blue mana cost" should {
      "convert it correctly" in {
        val count: Int = randomCount
        ManaAdapter.toProto(Mana.PhyrexianBlue(count)).shouldEqual(
          CacheProtos.ManaCostEntry.newBuilder()
              .setCount(count)
              .setType(CacheProtos.ManaType.PHYREXIAN_BLUE_MANA)
              .build())
      }
    }

    "given a phyrexian black mana cost" should {
      "convert it correctly" in {
        val count: Int = randomCount
        ManaAdapter.toProto(Mana.PhyrexianBlack(count)).shouldEqual(
          CacheProtos.ManaCostEntry.newBuilder()
              .setCount(count)
              .setType(CacheProtos.ManaType.PHYREXIAN_BLACK_MANA)
              .build())
      }
    }

    "given a phyrexian red mana cost" should {
      "convert it correctly" in {
        val count: Int = randomCount
        ManaAdapter.toProto(Mana.PhyrexianRed(count)).shouldEqual(
          CacheProtos.ManaCostEntry.newBuilder()
              .setCount(count)
              .setType(CacheProtos.ManaType.PHYREXIAN_RED_MANA)
              .build())
      }
    }

    "given a phyrexian green mana cost" should {
      "convert it correctly" in {
        val count: Int = randomCount
        ManaAdapter.toProto(Mana.PhyrexianGreen(count)).shouldEqual(
          CacheProtos.ManaCostEntry.newBuilder()
              .setCount(count)
              .setType(CacheProtos.ManaType.PHYREXIAN_GREEN_MANA)
              .build())
      }
    }

    "given null" should {
      "throw an exception" in {
        assertThrows[NullPointerException](ManaAdapter.toProto(null))
      }
    }
  }

  private val random: Random = new Random()

  private def randomCount: Int = abs(random.nextInt())
}
