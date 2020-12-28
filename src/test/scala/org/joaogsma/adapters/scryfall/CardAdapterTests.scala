package org.joaogsma.adapters.scryfall

import io.circe.Json
import io.circe.parser.parse
import org.joaogsma.entities.models.Card
import org.joaogsma.entities.models.Color
import org.joaogsma.entities.models.Mana
import org.joaogsma.entities.models.Type
import org.scalatest.Ignore
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import scala.io.BufferedSource
import scala.util.Try

@Ignore
class CardAdapterTests extends AnyWordSpec with Matchers {
  "The jsonToCard function" when {
    "applied to Json.Null" should {
      "return a failure" in {
//        CardAdapter.jsonToCard(Json.Null).isFailure shouldBe true
      }
    }

    "applied to a complete JSON)" should {
      "read all the fields" in {
//        val expectedCard: Card = Card(
//          "Rampant Growth",
//          Seq(Mana.Generic(1), Mana.Green(1)),
//          Seq(Color.Green),
//          Seq(Type.Sorcery),
//          2.0)
//        val result = usingFileSource("RampantGrowth.json", parseAndTransformToCard(None))
//        result shouldEqual Try(expectedCard)
      }
    }

    "applied to a JSON missing the mana cost" should {
      "return a Failure" in {
//        val result = usingFileSource(
//          "Bereavement.json",
//          parseAndTransformToCard(Option("mana_cost")))
//        result.isFailure shouldBe true
      }
    }

    "applied ot a JSON missing the colors" should {
      "return a Failure" in {
//        val result = usingFileSource(
//          "CorpseAugur.json",
//          parseAndTransformToCard(Option("colors")))
//        result.isFailure shouldBe true
      }
    }

    "applied to a JSON missing the type line" should {
      "return a Failure" in {
//        val result = usingFileSource(
//          "CorpseAugur.json",
//          parseAndTransformToCard(Option("type_line")))
//        result.isFailure shouldBe true
      }
    }
  }

  private def usingFileSource[A](filename: String, f: BufferedSource => A): A = {
    FilePortImpl.usingFile(s"$RESOURCES_DIRECTORY/$filename", f)
  }

  def parseAndTransformToCard(deleteField: Option[String])(bs: BufferedSource): Try[Card] = {
    val json = parse(bs.mkString)
        .map(json => deleteField match {
          case Some(field) => json.hcursor.downField(field).delete.top.get
          case None => json
        })
        .toTry
        .get
    CardAdapter.jsonToCard(json)
  }
}
