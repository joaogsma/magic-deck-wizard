package org.joaogsma.adapters.scryfall

import io.circe.parser.parse
import org.joaogsma.models.Mana
import org.scalatest.Matchers
import org.scalatest.WordSpec

import scala.io.BufferedSource

class ManaAdapterTests extends WordSpec with Matchers {
  "The parseToSequence function" when {
    "passed a X spell" should {
      "parse correctly" in {
        (ManaAdapter.parseToSequence(parseFileAndGetManaCost("Fireball.json"))
            should contain theSameElementsInOrderAs Seq(Mana.X(1), Mana.Red(1)))
        (ManaAdapter.parseToSequence(parseFileAndGetManaCost("GelatinousGenesis.json"))
            should contain theSameElementsInOrderAs Seq(Mana.X(2), Mana.Green(1)))
      }
    }

    "passed an empty string" should {
      "parse to an empty sequence" in {
        ManaAdapter.parseToSequence("") shouldBe empty
      }
    }

    "passed a mana cost string with multiple mana of a single color" should {
      "return the correct count" in {
        (ManaAdapter.parseToSequence("{W}{W}")
            should contain theSameElementsInOrderAs Seq(Mana.White(2)))
        (ManaAdapter.parseToSequence("{U}{U}{U}")
            should contain theSameElementsInOrderAs Seq(Mana.Blue(3)))
        (ManaAdapter.parseToSequence("{B}{B}{B}{B}")
            should contain theSameElementsInOrderAs Seq(Mana.Black(4)))
        (ManaAdapter.parseToSequence("{R}{R}{R}{R}{R}")
            should contain theSameElementsInOrderAs Seq(Mana.Red(5)))
        (ManaAdapter.parseToSequence("{G}{G}{G}{G}{G}{G}")
            should contain theSameElementsInOrderAs Seq(Mana.Green(6)))
      }
    }

    "passed a mana cost string with generic mana" should {
      "return the correct generic mana count" in {
        (ManaAdapter.parseToSequence(parseFileAndGetManaCost("ScourFromExistence.json"))
            should contain theSameElementsInOrderAs Seq(Mana.Generic(7)))
        (ManaAdapter.parseToSequence(parseFileAndGetManaCost("StrokeOfGenius.json"))
            should contain theSameElementsInOrderAs Seq(Mana.X(1), Mana.Generic(2), Mana.Blue(1)))
      }
    }

    "passed a mana cost string with phyrexian mana" should {
      "parse correctly" in {
        val inputStr = "{W/P}{U/P}{U/P}{B/P}{B/P}{B/P}{R/P}{R/P}{R/P}{R/P}{G/P}{G/P}{G/P}{G/P}{G/P}"
        val expected = Seq(
          Mana.PhyrexianWhite(1),
          Mana.PhyrexianBlue(2),
          Mana.PhyrexianBlack(3),
          Mana.PhyrexianRed(4),
          Mana.PhyrexianGreen(5),
        )
        ManaAdapter.parseToSequence(inputStr) should contain theSameElementsInOrderAs expected
      }
    }

    "passed a mana cost string with monocolored hybrid mana" should {
      "parse correctly" in {
        val inputStr = "{2/W}{2/U}{2/U}{2/B}{2/B}{2/B}{2/R}{2/R}{2/R}{2/R}{2/G}{2/G}{2/G}{2/G}{2/G}"
        val expected = Seq(
          Mana.HybridMonoWhite(1),
          Mana.HybridMonoBlue(2),
          Mana.HybridMonoBlack(3),
          Mana.HybridMonoRed(4),
          Mana.HybridMonoGreen(5),
        )
        ManaAdapter.parseToSequence(inputStr) should contain theSameElementsInOrderAs expected
      }
    }
  }

  private def parseFileAndGetManaCost(filename: String): String = {
    FilePortImpl.usingFile(s"$RESOURCES_DIRECTORY/$filename", parseAndGetManaCost)
  }

  private def parseAndGetManaCost(bs: BufferedSource): String = {
    parse(bs.mkString)
        .map(json => json.hcursor.get[String]("mana_cost").toTry.get)
        .toTry
        .get
  }
}
