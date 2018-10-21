package org.joaogsma.metrics

import org.joaogsma.models.Color
import org.joaogsma.ports.file.DeckListPort
import org.scalatest.Matchers
import org.scalatest.WordSpec

class MetricsPackageTests extends WordSpec with Matchers {
  private val RESOURCES_DIRECTORY = "src/test/resources/org/joaogsma/metrics"

  "The countTags function" when {
    "given an empty list" should {
      "return an empty map" in {
        countTags(Seq.empty) shouldBe empty
      }
    }

    "given a valid deck entry sequence" should {
      "count the tag occurrences correctly" in {
        val input = DeckListPort.read(s"$RESOURCES_DIRECTORY/tatyova.txt").get

        val expected = Map(
          "cmc_8" -> 2,
          "cmc_2" -> 16,
          "creature" -> 11,
          "extra_land_drop" -> 4,
          "buyback" -> 1,
          "enchantment_removal" -> 3,
          "land" -> 39,
          "enchantment" -> 12,
          "wipe" -> 3,
          "blue" -> 19,
          "life_gain" -> 2,
          "mana_src_blue" -> 21,
          "planeswalker" -> 1,
          "mana_src_green" -> 26,
          "cmc_6" -> 3,
          "graveyard_recursion" -> 2,
          "colorless" -> 9,
          "ramp" -> 13,
          "bounce" -> 2,
          "green" -> 36,
          "land_to_battlefield" -> 11,
          "artifact_removal" -> 3,
          "draw" -> 9,
          "cmc_5" -> 8,
          "political" -> 8,
          "land_to_hand" -> 4,
          "instant" -> 13,
          "scry" -> 2,
          "cmc_1" -> 6,
          "counter" -> 6,
          "mana_sink" -> 1,
          "sorcery" -> 15,
          "cmc_4" -> 5,
          "graveyard_hate" -> 1,
          "cmc_7" -> 2,
          "join_forces" -> 2,
          "token_generator" -> 7,
          "artifact" -> 9,
          "landfall" -> 4,
          "cmc_3" -> 18,
          "creature_removal" -> 5)

        countTags(input) shouldEqual expected
      }
    }
  }

  "The countColors function" when {
    "given an empty deck entry list" should {
      "return a zero count to each color" in {
        val expected: Map[Option[Color], Int] = Map(
          None -> 0,
          Some(Color.White) -> 0,
          Some(Color.Blue) -> 0,
          Some(Color.Black) -> 0,
          Some(Color.Red) -> 0,
          Some(Color.Green) -> 0
        )
        countColors(Seq.empty) shouldEqual expected
      }
    }

    "given a valid deck entry list" should {
      "return the correct counts in a map" in {
        val expected: Map[Option[Color], Int] = Map(
          None -> 2,
          Some(Color.White) -> 0,
          Some(Color.Blue) -> 6,
          Some(Color.Black) -> 0,
          Some(Color.Red) -> 0,
          Some(Color.Green) -> 13
        )
        countColors(TestInputs.tatyovaStandaloneEntries) shouldEqual expected
      }
    }
  }

  "The countManaSymbols function" when {
    "given an empty deck entry list" should {
      "return a zero count to each color" in {
        val expected: Map[Color, Int] = Map(
          Color.White -> 0,
          Color.Blue -> 0,
          Color.Black -> 0,
          Color.Red -> 0,
          Color.Green -> 0
        )
        countManaSymbols(Seq.empty) shouldEqual expected
      }
    }

    "given a valid deck entry list" should {
      "return the correct counts in a map" in {
        val expected: Map[Color, Int] = Map(
          Color.White -> 0,
          Color.Blue -> 9,
          Color.Black -> 0,
          Color.Red -> 0,
          Color.Green -> 22
        )
        countManaSymbols(TestInputs.tatyovaStandaloneEntries) shouldEqual expected
      }
    }
  }

  "The countManaCurve function" when {
    "given an empty deck entry list" should {
      "return a zero count for each cmc" in {
        val expected = Range.BigDecimal.inclusive(0.0, 10.0, 1.0).map(_.toDouble -> 0).toMap
        countManaCurve(Seq.empty) shouldEqual expected
      }
    }

    "given a valid deck entry list" should {
      "return the correct count for each CMC" in {
        val expected = Map(
          0.0 -> 0,
          1.0 -> 1,
          2.0 -> 3,
          3.0 -> 3,
          4.0 -> 1,
          5.0 -> 5,
          6.0 -> 3,
          7.0 -> 2,
          8.0 -> 1,
          9.0 -> 0,
          10.0 -> 0)
        countManaCurve(TestInputs.tatyovaStandaloneEntries) shouldEqual expected
      }
    }
  }
}
