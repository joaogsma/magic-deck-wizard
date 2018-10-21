package org.joaogsma.adapters.text

import org.scalatest.Matchers
import org.scalatest.WordSpec

import scala.util.Success

class TagAdapterTests extends WordSpec with Matchers {
  "The parseToSequence function" when {
    "given valid tags" should {
      "parse them correctly" in {
        val inputs = List(
          "@cmc_3 @colorless @artifact @draw @political",
          "@cmc_4 @colorless @artifact @draw",
          "@cmc_5 @blue @instant @draw @political",
          "@cmc_5 @blue @green @sorcery @draw @ramp @extra_land_drop",
          "@cmc_1 @blue @sorcery @draw @join_forces @political",
          "@foo")

        val expectedValues = List(
          Set("cmc_3", "colorless", "artifact", "draw", "political"),
          Set("cmc_4", "colorless", "artifact", "draw"),
          Set("cmc_5", "blue", "instant", "draw", "political"),
          Set("cmc_5", "blue", "green", "sorcery", "draw", "ramp", "extra_land_drop"),
          Set("cmc_1", "blue", "sorcery", "draw", "join_forces", "political"),
          Set("foo"))
            .map(Success.apply)

        inputs
            .zip(expectedValues)
            .foreach {
              case (input, expected) => TagAdapter.parseToSequence(input) shouldEqual expected
            }
      }
    }

    "given a non-tag word among valid tags" should {
      "return a Failure" in {
        TagAdapter.parseToSequence("foo").isFailure shouldBe true
        TagAdapter.parseToSequence("@some_tag foo").isFailure shouldBe true
        TagAdapter.parseToSequence("@some_tag foo @some_other_tag").isFailure shouldBe true
        TagAdapter.parseToSequence("foo @some_tag").isFailure shouldBe true
      }
    }

    "given an invalid tag" should {
      "return a Failure" in {
        TagAdapter.parseToSequence("@some_inv@alid_tag").isFailure shouldBe true
      }
    }

    "given repeated tags" should {
      "parse successfully, but only return the repeated tags once" in {
        TagAdapter.parseToSequence("@tag @tag @tag") shouldEqual Success(Set("tag"))
      }
    }

    "given valid tags with arbitrary spacing between them" should {
      "parse successfully" in {
        (TagAdapter.parseToSequence("@tag    @other_tag              @yet_another_tag")
            shouldEqual Success(Set("tag", "other_tag", "yet_another_tag")))
      }
    }

    "given a a tag group with prefixing whitespace" should {
      "return a Failure" in {
        TagAdapter.parseToSequence("      @tag").isFailure shouldBe true
      }
    }
  }

  "the toString function" when {
    "given an empty set of tags" should {
      "return an empty string" in {
        TagAdapter.toString(Set.empty) shouldEqual ""
      }
    }

    "given a set of tags" should {
      "return the correct string" in {
        val input = Set("tag", "other_tag", "yet-another-tag")
        TagAdapter.toString(input) shouldEqual "@tag @other_tag @yet-another-tag"
      }
    }
  }
}
