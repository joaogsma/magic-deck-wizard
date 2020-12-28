package org.joaogsma.adapters.scryfall

import io.circe.parser.parse
import org.joaogsma.entities.models.Mana
import org.scalatest.Ignore
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import scala.io.BufferedSource

@Ignore
class ManaAdapterTests extends AnyWordSpec with Matchers {
  "The parseToSequence function" when {
    "passed a X spell" should {
      "parse correctly" in {
        (ManaAdapter.parseToSet(parseFileAndGetManaCost("Fireball.json"))
            shouldEqual Set(Mana.X(1), Mana.Red(1)))
        (ManaAdapter.parseToSet(parseFileAndGetManaCost("GelatinousGenesis.json"))
            shouldEqual Set(Mana.X(2), Mana.Green(1)))
      }
    }

    "passed an empty string" should {
      "parse to an empty sequence" in {
        ManaAdapter.parseToSet("") shouldBe empty
      }
    }

    "passed a mana cost string with multiple mana of a single color" should {
      "return the correct count" in {
        (ManaAdapter.parseToSet("{W}{W}")
            shouldEqual Set(Mana.White(2)))
        (ManaAdapter.parseToSet("{U}{U}{U}")
            shouldEqual Set(Mana.Blue(3)))
        (ManaAdapter.parseToSet("{B}{B}{B}{B}")
            shouldEqual Set(Mana.Black(4)))
        (ManaAdapter.parseToSet("{R}{R}{R}{R}{R}")
            shouldEqual Set(Mana.Red(5)))
        (ManaAdapter.parseToSet("{G}{G}{G}{G}{G}{G}")
            shouldEqual Set(Mana.Green(6)))
      }
    }

    "passed a mana cost string with generic mana" should {
      "return the correct generic mana count" in {
        (ManaAdapter.parseToSet(parseFileAndGetManaCost("ScourFromExistence.json"))
            shouldEqual Set(Mana.Generic(7)))
        (ManaAdapter.parseToSet(parseFileAndGetManaCost("StrokeOfGenius.json"))
            shouldEqual Set(Mana.X(1), Mana.Generic(2), Mana.Blue(1)))
      }
    }

    "passed a mana cost string with phyrexian mana" should {
      "parse correctly" in {
        val inputStr = "{W/P}{U/P}{U/P}{B/P}{B/P}{B/P}{R/P}{R/P}{R/P}{R/P}{G/P}{G/P}{G/P}{G/P}{G/P}"
        val expected = Set(
          Mana.PhyrexianWhite(1),
          Mana.PhyrexianBlue(2),
          Mana.PhyrexianBlack(3),
          Mana.PhyrexianRed(4),
          Mana.PhyrexianGreen(5),
        )
        ManaAdapter.parseToSet(inputStr) shouldEqual expected
      }
    }

    "passed a mana cost string with monocolored hybrid mana" should {
      "parse correctly" in {
        val inputStr = "{2/W}{2/U}{2/U}{2/B}{2/B}{2/B}{2/R}{2/R}{2/R}{2/R}{2/G}{2/G}{2/G}{2/G}{2/G}"
        val expected = Set(
          Mana.HybridMonoWhite(1),
          Mana.HybridMonoBlue(2),
          Mana.HybridMonoBlack(3),
          Mana.HybridMonoRed(4),
          Mana.HybridMonoGreen(5),
        )
        ManaAdapter.parseToSet(inputStr) shouldEqual expected
      }
    }

    "passed a mana cost string with multicolored hybrid mana" should {
      "parse correctly" in {
        val inputStr = ("{W/U}"
            + "{W/B}{W/B}"
            + "{W/R}{W/R}{W/R}"
            + "{W/G}{W/G}{W/G}{W/G}"
            + "{U/B}{U/B}{U/B}{U/B}{U/B}"
            + "{U/R}{U/R}{U/R}{U/R}{U/R}{U/R}"
            + "{U/G}{U/G}{U/G}{U/G}{U/G}{U/G}{U/G}"
            + "{B/R}{B/R}{B/R}{B/R}{B/R}{B/R}{B/R}{B/R}"
            + "{B/G}{B/G}{B/G}{B/G}{B/G}{B/G}{B/G}{B/G}{B/G}"
            + "{R/G}{R/G}{R/G}{R/G}{R/G}{R/G}{R/G}{R/G}{R/G}{R/G}")
        val expected = Set(
          Mana.HybridWhiteBlue(1),
          Mana.HybridWhiteBlack(2),
          Mana.HybridWhiteRed(3),
          Mana.HybridWhiteGreen(4),
          Mana.HybridBlueBlack(5),
          Mana.HybridBlueRed(6),
          Mana.HybridBlueGreen(7),
          Mana.HybridBlackRed(8),
          Mana.HybridBlackGreen(9),
          Mana.HybridRedGreen(10))
        ManaAdapter.parseToSet(inputStr) shouldEqual expected
      }
    }
  }

  private def parseFileAndGetManaCost(filename: String): String = {
    FilePortImpl.usingFile(s"$RESOURCES_DIRECTORY/$filename", parseAndGetManaCost _)
  }

  private def parseAndGetManaCost(bs: BufferedSource): String = {
    parse(bs.mkString)
        .map(json => json.hcursor.get[String]("mana_cost").toTry.get)
        .toTry
        .get
  }
}
