package org.joaogsma.adapters.scryfall

import io.circe.parser.parse
import org.joaogsma.models.Mana
import org.joaogsma.ports.files.usingFile
import org.scalatest.Matchers
import org.scalatest.WordSpec

import scala.io.BufferedSource
import scala.io.Source

class ManaAdapterTests extends WordSpec with Matchers
{
  "The parseSequence function" when
  {
    "passed a X spell" should
    {
      "parse correctly" in
      {
        (ManaAdapter.parseSequence(parseFileAndGetManaCost("Fireball.json"))
            should contain theSameElementsAs Seq(Mana.X(1), Mana.Red(1)))
        (ManaAdapter.parseSequence(parseFileAndGetManaCost("GelatinousGenesis.json"))
            should contain theSameElementsAs Seq(Mana.X(2), Mana.Green(1)))
      }
    }

    "passed an empty string" should
    {
      "parse to an empty sequence" in
      {
        ManaAdapter.parseSequence("") shouldBe empty
      }
    }

    "passed a mana cost string with multiple mana of a single color" should
    {
      "return the correct count" in
      {
        (ManaAdapter.parseSequence("{W}{W}")
            should contain theSameElementsAs Seq(Mana.White(2)))
        (ManaAdapter.parseSequence("{U}{U}{U}")
            should contain theSameElementsAs Seq(Mana.Blue(3)))
        (ManaAdapter.parseSequence("{B}{B}{B}{B}")
            should contain theSameElementsAs Seq(Mana.Black(4)))
        (ManaAdapter.parseSequence("{R}{R}{R}{R}{R}")
            should contain theSameElementsAs Seq(Mana.Red(5)))
        (ManaAdapter.parseSequence("{G}{G}{G}{G}{G}{G}")
            should contain theSameElementsAs Seq(Mana.Green(6)))
      }
    }

    "passed a mana cost string with generic mana" should
    {
      "return the correct generic mana count" in
      {
        (ManaAdapter.parseSequence(parseFileAndGetManaCost("ScourFromExistence.json"))
            should contain theSameElementsAs Seq(Mana.Generic(7)))
        (ManaAdapter.parseSequence(parseFileAndGetManaCost("StrokeOfGenius.json"))
            should contain theSameElementsAs Seq(Mana.X(1), Mana.Generic(2), Mana.Blue(1)))
      }
    }
  }

  private val RESOURCES_DIRECTORY = "src/test/resources/org/joaogsma/adapters/scryfall"

  private def parseFileAndGetManaCost(filename: String): String =
  {
    val bs: BufferedSource = Source.fromFile(s"$RESOURCES_DIRECTORY/$filename")
    usingFile(bs, parseAndGetManaCost)
  }

  private def parseAndGetManaCost(bs: BufferedSource): String =
  {
    parse(bs.mkString)
        .map(json => json.hcursor.get[String]("mana_cost").toTry.get)
        .toTry
        .get
  }
}
