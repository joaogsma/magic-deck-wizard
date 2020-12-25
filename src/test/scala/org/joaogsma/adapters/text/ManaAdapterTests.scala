package org.joaogsma.adapters.text

import org.joaogsma.models.Mana
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import scala.util.Success

class ManaAdapterTests extends AnyWordSpec with Matchers {
  "The parseToSequence function" when {
    "passed a X spell" should {
      "parse correctly" in {
        (ManaAdapter.parseToSequence("\"{X}{R}\"")
            shouldEqual Success(Seq(Mana.X(1), Mana.Red(1))))
        (ManaAdapter.parseToSequence("\"{X}{X}{G}\"")
            shouldEqual Success(Seq(Mana.X(2), Mana.Green(1))))
      }
    }

    "passed an empty sequence" should {
      "parse to an empty sequence" in {
        ManaAdapter.parseToSequence("\"\"") shouldEqual Success(Seq.empty)
      }
    }

    "passed an empty string" should {
      "return a Failure" in {
        ManaAdapter.parseToSequence("").isFailure shouldBe true
      }
    }

    "passed a mana cost string with multiple mana of a single color" should {
      "return the correct count" in {
        (ManaAdapter.parseToSequence("\"{W}{W}\"")
            shouldEqual Success(Seq(Mana.White(2))))
        (ManaAdapter.parseToSequence("\"{U}{U}{U}\"")
            shouldEqual Success(Seq(Mana.Blue(3))))
        (ManaAdapter.parseToSequence("\"{B}{B}{B}{B}\"")
            shouldEqual Success(Seq(Mana.Black(4))))
        (ManaAdapter.parseToSequence("\"{R}{R}{R}{R}{R}\"")
            shouldEqual Success(Seq(Mana.Red(5))))
        (ManaAdapter.parseToSequence("\"{G}{G}{G}{G}{G}{G}\"")
            shouldEqual Success(Seq(Mana.Green(6))))
      }
    }

    "passed a mana cost string with generic mana" should {
      "return the correct generic mana count" in {
        ManaAdapter.parseToSequence("\"{7}\"") shouldEqual Success(Seq(Mana.Generic(7)))
        (ManaAdapter.parseToSequence("\"{X}{2}{U}\"")
            shouldEqual Success(Seq(Mana.X(1), Mana.Generic(2), Mana.Blue(1))))
      }
    }

    "passed a mana cost string with phyrexian mana" should {
      "parse correctly" in {
        val inputStr =
            "\"{W/P}{U/P}{U/P}{B/P}{B/P}{B/P}{R/P}{R/P}{R/P}{R/P}{G/P}{G/P}{G/P}{G/P}{G/P}\""
        val expected = Seq(
          Mana.PhyrexianWhite(1),
          Mana.PhyrexianBlue(2),
          Mana.PhyrexianBlack(3),
          Mana.PhyrexianRed(4),
          Mana.PhyrexianGreen(5),
        )
        ManaAdapter.parseToSequence(inputStr) shouldEqual Success(expected)
      }
    }

    "passed a mana cost string with monocolored hybrid mana" should {
      "parse correctly" in {
        val inputStr =
            "\"{2/W}{2/U}{2/U}{2/B}{2/B}{2/B}{2/R}{2/R}{2/R}{2/R}{2/G}{2/G}{2/G}{2/G}{2/G}\""
        val expected = Seq(
          Mana.HybridMonoWhite(1),
          Mana.HybridMonoBlue(2),
          Mana.HybridMonoBlack(3),
          Mana.HybridMonoRed(4),
          Mana.HybridMonoGreen(5),
        )
        ManaAdapter.parseToSequence(inputStr) shouldEqual Success(expected)
      }
    }

    "passed a mana cost string with multicolored hybrid mana" should {
      "parse correctly" in {
        val inputStr = ("\"{W/U}"
            + "{W/B}{W/B}"
            + "{W/R}{W/R}{W/R}"
            + "{W/G}{W/G}{W/G}{W/G}"
            + "{U/B}{U/B}{U/B}{U/B}{U/B}"
            + "{U/R}{U/R}{U/R}{U/R}{U/R}{U/R}"
            + "{U/G}{U/G}{U/G}{U/G}{U/G}{U/G}{U/G}"
            + "{B/R}{B/R}{B/R}{B/R}{B/R}{B/R}{B/R}{B/R}"
            + "{B/G}{B/G}{B/G}{B/G}{B/G}{B/G}{B/G}{B/G}{B/G}"
            + "{R/G}{R/G}{R/G}{R/G}{R/G}{R/G}{R/G}{R/G}{R/G}{R/G}\"")
        val expected = Seq(
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
        ManaAdapter.parseToSequence(inputStr) shouldEqual Success(expected)
      }
    }
  }

  "The toString function" when {
    "given an empty sequence" should {
      "return \"\"" in {
        ManaAdapter.toString(Seq.empty) shouldEqual "\"\""
      }
    }

    "given an X spell" should {
      "return the correct string" in {
        ManaAdapter.toString(List(Mana.X(1), Mana.Red(1))) shouldEqual "\"{X}{R}\""
        ManaAdapter.toString(List(Mana.X(2), Mana.Green(1))) shouldEqual "\"{X}{X}{G}\""
      }
    }

    "given a mana cost with multiple mana of a single color" should {
      "return the correct string" in {
        ManaAdapter.toString(List(Mana.White(2))) shouldEqual "\"{W}{W}\""
        ManaAdapter.toString(List(Mana.Blue(3))) shouldEqual "\"{U}{U}{U}\""
        ManaAdapter.toString(List(Mana.Black(4))) shouldEqual "\"{B}{B}{B}{B}\""
        ManaAdapter.toString(List(Mana.Red(5))) shouldEqual "\"{R}{R}{R}{R}{R}\""
        ManaAdapter.toString(List(Mana.Green(6))) shouldEqual "\"{G}{G}{G}{G}{G}{G}\""
      }
    }

    "given a mana cost with with generic mana" should {
      "return the correct string" in {
        ManaAdapter.toString(List(Mana.Generic(7))) shouldEqual "\"{7}\""
        (ManaAdapter.toString(List(Mana.X(1), Mana.Generic(2), Mana.Blue(1)))
            shouldEqual "\"{X}{2}{U}\"")
      }
    }

    "given a mana cost with phyrexian mana" should {
      "return the correct string" in {
        val input = Seq(
          Mana.PhyrexianWhite(1),
          Mana.PhyrexianBlue(2),
          Mana.PhyrexianBlack(3),
          Mana.PhyrexianRed(4),
          Mana.PhyrexianGreen(5),
        )
        val expected =
            "\"{W/P}{U/P}{U/P}{B/P}{B/P}{B/P}{R/P}{R/P}{R/P}{R/P}{G/P}{G/P}{G/P}{G/P}{G/P}\""
        ManaAdapter.toString(input) shouldEqual expected
      }
    }

    "given a mana cost with monocolored hybrid mana" should {
      "return the correct string" in {
        val input = Seq(
          Mana.HybridMonoWhite(1),
          Mana.HybridMonoBlue(2),
          Mana.HybridMonoBlack(3),
          Mana.HybridMonoRed(4),
          Mana.HybridMonoGreen(5),
        )
        val expected =
          "\"{2/W}{2/U}{2/U}{2/B}{2/B}{2/B}{2/R}{2/R}{2/R}{2/R}{2/G}{2/G}{2/G}{2/G}{2/G}\""
        ManaAdapter.toString(input) shouldEqual expected
      }
    }

    "given a mana cost with multicolored hybrid mana" should {
      "return the correct string" in {
        val input = Seq(
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
        val expected = ("\"{W/U}"
            + "{W/B}{W/B}"
            + "{W/R}{W/R}{W/R}"
            + "{W/G}{W/G}{W/G}{W/G}"
            + "{U/B}{U/B}{U/B}{U/B}{U/B}"
            + "{U/R}{U/R}{U/R}{U/R}{U/R}{U/R}"
            + "{U/G}{U/G}{U/G}{U/G}{U/G}{U/G}{U/G}"
            + "{B/R}{B/R}{B/R}{B/R}{B/R}{B/R}{B/R}{B/R}"
            + "{B/G}{B/G}{B/G}{B/G}{B/G}{B/G}{B/G}{B/G}{B/G}"
            + "{R/G}{R/G}{R/G}{R/G}{R/G}{R/G}{R/G}{R/G}{R/G}{R/G}\"")
        ManaAdapter.toString(input) shouldEqual expected
      }
    }
  }
}
