package org.joaogsma.adapters.text

import org.joaogsma.models.Mana
import org.scalatest.Matchers
import org.scalatest.WordSpec

import scala.util.Success

class ManaAdapterTests extends WordSpec with Matchers
{
  "The parseToSequence function" when
  {
    "passed a X spell" should
    {
      "parse correctly" in
      {
        (ManaAdapter.parseToSequence("\"{X}{R}\"")
            shouldEqual Success(Seq(Mana.X(1), Mana.Red(1))))
        (ManaAdapter.parseToSequence("\"{X}{X}{G}\"")
            shouldEqual Success(Seq(Mana.X(2), Mana.Green(1))))
      }
    }

    "passed an empty sequence" should
     {
      "parse to an empty sequence" in
      {
        ManaAdapter.parseToSequence("\"\"") shouldEqual Success(Seq.empty)
      }
    }

    "passed an empty string" should
     {
      "return a Failure" in
      {
        ManaAdapter.parseToSequence("").isFailure shouldBe true
      }
    }

    "passed a mana cost string with multiple mana of a single color" should
    {
      "return the correct count" in
      {
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

    "passed a mana cost string with generic mana" should
    {
      "return the correct generic mana count" in
      {
        ManaAdapter.parseToSequence("\"{7}\"") shouldEqual Success(Seq(Mana.Generic(7)))
        (ManaAdapter.parseToSequence("\"{X}{2}{U}\"")
            shouldEqual Success(Seq(Mana.X(1), Mana.Generic(2), Mana.Blue(1))))
      }
    }
  }
}
