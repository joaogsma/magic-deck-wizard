package org.joaogsma.adapters.scryfall

import io.circe.Json
import io.circe.parser.parse
import org.joaogsma.models.Card
import org.joaogsma.models.Color
import org.joaogsma.models.Mana
import org.joaogsma.models.Type
import org.joaogsma.ports.files.usingFile
import org.scalatest.FunSpec
import org.scalatest.Matchers

import scala.io.BufferedSource
import scala.io.Source
import scala.util.Try

class CardAdapterTests extends FunSpec with Matchers
{
  describe("jsonToCard")
  {
    describe("(when the json is Json.Null)")
    {
      it("must return a failure")
      {
        val result = CardAdapter.jsonToCard(Json.Null)
      }
    }

    describe("(when the json is complete)")
    {
      it("must read all the fields")
      {
        val expectedCard: Card = Card(
          Seq(Mana.Generic(1), Mana.Green(1)),
          Seq(Color.Green),
          Seq(Type.Sorcery),
          2.0
        )
        val result = usingFileSource("RampantGrowth.json", parseAndTransformToCard(None))
        result shouldEqual Try(expectedCard)
      }
    }

    describe("(when the mana cost is missing)")
    {
      it("must return a Failure")
      {
        val result = usingFileSource(
          "Bereavement.json",
          parseAndTransformToCard(Option("mana_cost"))
        )
        result.isFailure shouldBe true
      }
    }

    describe("(when the colors are missing)")
    {
      it("must return a Failure")
      {
        val result = usingFileSource(
          "CorpseAugur.json",
          parseAndTransformToCard(Option("colors"))
        )
        result.isFailure shouldBe true
      }
    }

    describe("(when the type line is missing)")
    {
      it("must return a Failure")
      {
        val result = usingFileSource(
          "CorpseAugur.json",
          parseAndTransformToCard(Option("type_line")))
        result.isFailure shouldBe true
      }
    }
  }

  private val RESOURCES_DIRECTORY = "src/test/resources/org/joaogsma/adapters/scryfall"

  private def usingFileSource[A](filename: String, f: BufferedSource => A): A =
  {
    val bs: BufferedSource = Source.fromFile(s"$RESOURCES_DIRECTORY/$filename")
    usingFile(bs, f)
  }

  def parseAndTransformToCard(deleteField: Option[String])(bs: BufferedSource): Try[Card] =
  {
    val json = parse(bs.mkString)
        .map(json => deleteField match
        {
          case Some(field) => json.hcursor.downField(field).delete.top.get
          case None => json
        })
        .toTry
        .get
    CardAdapter.jsonToCard(json)
  }
}
