package org.joaogsma.adapters.text

import org.joaogsma.models.Card
import org.joaogsma.models.DeckEntry
import org.joaogsma.models.Type
import org.scalatest.Matchers
import org.scalatest.WordSpec

import scala.util.Random
import scala.util.Success

class DeckEntryAdapterTests extends WordSpec with Matchers
{
  "The parse function" when
  {
    "given a valid deck entry" should
    {
      "parse it successfully" in
      {
        val input = "[1] Llanowar Scout @cmc_2 @green @creature @ramp @extra_land_drop"
        val expected = DeckEntry(
          1,
          "Llanowar Scout",
          Set("cmc_2", "green", "creature", "ramp", "extra_land_drop"))
        DeckEntryAdapter.parse(input) shouldEqual Success(expected)
      }
    }

    "given a deck entry with arbitrary spacing" should
    {
      "parse it successfully" in
      {
        def randomSpacing: String = List.fill(Random.nextInt(99) + 1)(' ').mkString
        val input = (s"$randomSpacing[1]${randomSpacing}Llanowar Scout$randomSpacing@cmc_2"
            + s"$randomSpacing@green$randomSpacing@creature$randomSpacing@ramp$randomSpacing"
            + s"@extra_land_drop$randomSpacing")
        val expected = DeckEntry(
          1,
          "Llanowar Scout",
          Set("cmc_2", "green", "creature", "ramp", "extra_land_drop"))
        DeckEntryAdapter.parse(input) shouldEqual Success(expected)
      }
    }

    "given a deck entry with an empty name" should
    {
      "return a Failure" in
      {
        DeckEntryAdapter.parse("[1] @cmc_2 @green @creature @ramp @extra_land_drop")
            .isFailure shouldBe true
        DeckEntryAdapter.parse("[1]  @cmc_2 @green @creature @ramp @extra_land_drop")
            .isFailure shouldBe true
        DeckEntryAdapter.parse("[1] @cmc_2")
            .isFailure shouldBe true
      }
    }

    "given a valid deck entry without tags" should
    {
      "parse successfully" in
      {
        (DeckEntryAdapter.parse( "[08] Island")
            shouldEqual Success(DeckEntry(8, "Island", Set.empty)))
        DeckEntryAdapter
            .parse("[08] Island {manacost: \"\", colors: \"\", types: [land], cmc: 0}")
            .shouldEqual(
              Success(
                DeckEntry(
                  8,
                  "Island",
                  Some(Card(Seq.empty, Seq.empty, Seq(Type.Land), 0)),
                  Set.empty)))
      }
    }
  }
}
