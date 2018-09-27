package org.joaogsma.controllers

import org.joaogsma.metrics.TestInputs
import org.joaogsma.models.Card
import org.joaogsma.models.DeckEntry
import org.scalatest.Matchers
import org.scalatest.WordSpec

class ConsoleControllerTests extends WordSpec with Matchers
{
  "The fillMissingField function" when
  {
    "given a DeckEntry which is not missing the card field" should
    {
      "return the same DeckEntry instance" in
      {
        val input = TestInputs.tatyovaStandaloneEntries.head
        ConsoleController.fillMissingField(input) shouldEqual input
      }
    }

    "given a DeckEntry which is missing the card field" should
    {
      "Query Scryfall to fill the missing field" in
      {
        val input = DeckEntry(
          1,
          "Thaumatic Compass // Spires of Orazca",
          Set("cmc_2", "colorless", "artifact", "land_to_hand")
        )
        ConsoleController.fillMissingField(input).card.isDefined shouldBe true
      }
    }
  }
}
