package org.joaogsma.adapters.text

import org.joaogsma.entities.models.Card
import org.joaogsma.entities.models.Color
import org.joaogsma.entities.models.Mana
import org.joaogsma.entities.models.Type
import org.scalatest.Ignore
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import scala.util.Try

@Ignore
class CardAdapterTests extends AnyWordSpec with Matchers {
  "The parse function" when {
    "applied to an empty string" should {
      "return a Failure" in {
//        CardAdapter.parse("").isFailure shouldBe true
      }
    }

    "applied to \"{}\"" should {
      "return a Failure" in {
//        CardAdapter.parse("{}").isFailure shouldBe true
      }
    }

    "applied to a valid string" should {
      "read all the fields" in {
//        val input: String = """{manacost: "{1}{G}", colors: "G", types: [Sorcery], cmc: 2}"""
//        val expectedCard: Card = Card(
//          Seq(Mana.Generic(1), Mana.Green(1)),
//          Seq(Color.Green),
//          Seq(Type.Sorcery),
//          2.0)
//        CardAdapter.parse(input) shouldEqual Try(expectedCard)
      }
    }

    "applied to a valid string with arbitrary spacing" should {
      "read all the fields" in {
//        val input: String =
//            """{  manacost:         "{1}{G}" ,colors:        "G",types:   [Sorcery], cmc: 2 }"""
//        val expectedCard: Card = Card(
//          Seq(Mana.Generic(1), Mana.Green(1)),
//          Seq(Color.Green),
//          Seq(Type.Sorcery),
//          2.0)
//        CardAdapter.parse(input) shouldEqual Try(expectedCard)
      }
    }

    "applied to a valid string with more than one type" should {
      "read all the fields" in {
//        val input: String =
//            """{ manacost: "{1}{G}", colors: "G", types: [artifact, Creature], cmc: 2 }"""
//        val expectedCard: Card = Card(
//          Seq(Mana.Generic(1), Mana.Green(1)),
//          Seq(Color.Green),
//          Seq(Type.Artifact, Type.Creature),
//          2.0)
//        CardAdapter.parse(input) shouldEqual Try(expectedCard)
      }
    }

    "applied to a string missing the mana cost" should {
      "return a Failure" in {
//        val input: String = """{ colors: "G", types: [Sorcery], cmc: 2 }"""
//        CardAdapter.parse(input).isFailure shouldBe true
      }
    }

    "applied ot a string missing the colors" should {
      "return a Failure" in {
//        val input: String = """{ manacost: "{1}{G}", types: [Sorcery], cmc: 2 }"""
//        CardAdapter.parse(input).isFailure shouldBe true
      }
    }

    "applied to a string missing the type line" should {
      "return a Failure" in {
//        val input: String = """{ manacost: "{1}{G}", colors: "G", cmc: 2 }"""
//        CardAdapter.parse(input).isFailure shouldBe true
      }
    }

    "applied to a string missing the cmc" should {
      "return a Failure" in {
//        val input: String = """{ manacost: "{1}{G}", colors: "G", types: [Sorcery] }"""
//        CardAdapter.parse(input).isFailure shouldBe true
      }
    }

    "applied to a with the parameters out of order" should {
      "parse successfully" in {
//        val input: String = """{ colors: "G", manacost: "{1}{G}", cmc: 2, types: [Sorcery] }"""
//        val expectedCard: Card = Card(
//          Seq(Mana.Generic(1), Mana.Green(1)),
//          Seq(Color.Green),
//          Seq(Type.Sorcery),
//          2.0)
//        CardAdapter.parse(input) shouldEqual Try(expectedCard)
      }
    }
  }

  "the toString function" when {
    "given a card" should {
      "return the correct string" in {
//        val input =
//            Card(List(Mana.Generic(1), Mana.Green(1)), List(Color.Green), List(Type.Sorcery), 2)
//        (CardAdapter.toString(input)
//            shouldEqual
//            """{manacost: "{1}{G}", colors: "G", types: [Sorcery], cmc: 2.00}""")
      }
    }
  }
}
