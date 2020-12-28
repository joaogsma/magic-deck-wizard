package org.joaogsma.controllers

//import org.joaogsma.entities.metrics.TestInputs
import org.joaogsma.entities.models.DeckEntry
import org.scalatest.Ignore
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

@Ignore
class ConsoleControllerTests extends AnyWordSpec with Matchers {
  "The fillMissingField function" when {
    "given a DeckEntry which is not missing the card field" should {
      "return the same DeckEntry instance" in {
//        val input = TestInputs.tatyovaStandaloneEntries.head
//        ConsoleController.fillWithScryfallData(input) shouldEqual input
      }
    }

    "given a DeckEntry which is missing the card field" should {
      "Query Scryfall to fill the missing field" in {
//        val input = DeckEntry(
//          1,
//          "Thaumatic Compass // Spires of Orazca",
//          Set("cmc_2", "colorless", "artifact", "land_to_hand"))
//        ConsoleController.fillWithScryfallData(input).card.isDefined shouldBe true
      }
    }
  }
}
