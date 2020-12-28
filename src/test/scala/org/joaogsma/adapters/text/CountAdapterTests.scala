package org.joaogsma.adapters.text

import org.scalatest.Ignore
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import scala.util.Random
import scala.util.Success

@Ignore
class CountAdapterTests extends AnyWordSpec with Matchers {
  "The parse function" when {
    "given a valid count string" should {
      "parse it correctly" in {
//        CountAdapter.parse("[2]") shouldEqual Success(2)
      }
    }

    "given a string with arbitrary spacing" should {
      "return a Failure" in {
//        CountAdapter.parse("[   2]").isFailure shouldBe true
//        CountAdapter.parse("[2  ]").isFailure shouldBe true
//        CountAdapter.parse("[ 2 ]").isFailure shouldBe true
      }
    }

    "given a valid count with an arbitrary number of left zeros" should {
      "return a Failure" in {
//        (CountAdapter.parse(s"[${List.fill(Random.nextInt(99) + 1)('0').mkString}1]")
//            shouldEqual Success(1))
      }
    }

    "given a string not enclosed in []" should {
      "return a Failure" in {
//        CountAdapter.parse("1").isFailure shouldBe true
//        CountAdapter.parse("[1").isFailure shouldBe true
//        CountAdapter.parse("1]").isFailure shouldBe true
//        CountAdapter.parse("foo").isFailure shouldBe true
      }
    }

    "given a string which is not a number" should {
      "return a Failure" in {
//        CountAdapter.parse("foo").isFailure shouldBe true
//        CountAdapter.parse("[foo]").isFailure shouldBe true
      }
    }
  }

  "The toString function" when {
    "given a number" should {
      "return the correct string" in {
//        CountAdapter.toString(4) shouldEqual "[4]"
      }
    }
  }
}
