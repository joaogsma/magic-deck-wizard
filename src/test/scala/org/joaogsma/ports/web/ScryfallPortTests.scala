package org.joaogsma.ports.web

import org.joaogsma.entities.models.Card
import org.joaogsma.entities.models.Color
import org.joaogsma.entities.models.Mana
import org.joaogsma.entities.models.Type
import org.scalatest.Ignore
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import scala.util.Success

@Ignore
class ScryfallPortTests extends AnyWordSpec with Matchers {
  "The searchCardName function" when {
    "given a valid card name" should {
      "connect to Scryfall and return a complete Card" in {
        val expectedCard =
            Card("Counterspell", Set(Mana.Blue(2)), Set(Color.Blue), Set(Type.Instant), 2.0)
        ScryfallPort.searchCardName("Counterspell") shouldEqual Success(expectedCard)
      }
    }

    "given an invalid card name" should {
      "return Failure" in {
        val result0 = ScryfallPort.searchCardName("foo")
        val result1 = ScryfallPort.searchCardName("foo", exact = false)
        result0.isFailure shouldBe true
        result0.isFailure shouldBe true
        assertThrows[java.io.FileNotFoundException](result0.get)
        assertThrows[java.io.FileNotFoundException](result1.get)
      }
    }

    "given a card name that matches multiple cards" should {
      "return Failure" in {
        val result = ScryfallPort.searchCardName("Counter")
        result.isFailure shouldBe true
        assertThrows[java.io.FileNotFoundException](result.get)
      }
    }
  }
}
