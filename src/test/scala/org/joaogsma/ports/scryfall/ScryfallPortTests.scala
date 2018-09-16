package org.joaogsma.ports.scryfall

import org.joaogsma.models.Card
import org.joaogsma.models.Color
import org.joaogsma.models.Mana
import org.joaogsma.models.Type
import org.scalatest.Matchers
import org.scalatest.WordSpec

import scala.util.Success

class ScryfallPortTests extends WordSpec with Matchers
{
  "The searchCardName function" when
  {
    "given a valid card name" should
    {
      "connect to Scryfall and return a complete Card" in
      {
        val expectedCard = Card(Seq(Mana.Blue(2)), Seq(Color.Blue), Seq(Type.Instant), 2.0)
        ScryfallPort.searchCardName("Counterspell") shouldEqual Success(expectedCard)
      }
    }

    "given an invalid card name" should
    {
      "return Failure" in
      {
        val result0 = ScryfallPort.searchCardName("foo")
        val result1 = ScryfallPort.searchCardName("foo", exact = false)
        result0.isFailure shouldBe true
        result0.isFailure shouldBe true
        assertThrows[java.io.FileNotFoundException](result0.get)
        assertThrows[java.io.FileNotFoundException](result1.get)
      }
    }

    "given a card name that matches multiple cards" should
    {
      "return Failure" in
      {
        val result = ScryfallPort.searchCardName("Counter")
        result.isFailure shouldBe true
        assertThrows[java.io.FileNotFoundException](result.get)
      }
    }
  }
}
