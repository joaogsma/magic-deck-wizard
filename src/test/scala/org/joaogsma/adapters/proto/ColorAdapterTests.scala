package org.joaogsma.adapters.proto

import org.joaogsma.models.Color
import org.joaogsma.models.proto.CacheProtos
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import scala.util.Success

class ColorAdapterTests extends AnyWordSpec with Matchers {
  "The fromProto function" when {
    "given white" should {
      "convert it correctly" in {
        ColorAdapter.fromProto(CacheProtos.Color.WHITE) shouldBe Success(Color.White)
      }
    }

    "given blue" should {
      "convert it correctly" in {
        ColorAdapter.fromProto(CacheProtos.Color.BLUE) shouldBe Success(Color.Blue)
      }
    }

    "given black" should {
      "convert it correctly" in {
        ColorAdapter.fromProto(CacheProtos.Color.BLACK) shouldBe Success(Color.Black)
      }
    }

    "given red" should {
      "convert it correctly" in {
        ColorAdapter.fromProto(CacheProtos.Color.RED) shouldBe Success(Color.Red)
      }
    }

    "given green" should {
      "convert it correctly" in {
        ColorAdapter.fromProto(CacheProtos.Color.GREEN) shouldBe Success(Color.Green)
      }
    }

    "given an unrecognized value" should {
      "throw an exception" in {
        ColorAdapter.fromProto(CacheProtos.Color.UNRECOGNIZED).isFailure shouldBe true
      }
    }

    "given null" should {
      "throw an exception" in {
        ColorAdapter.fromProto(null).isFailure shouldBe true
      }
    }
  }

  "The toProto function" when {
    "given white" should {
      "convert it correctly" in {
        ColorAdapter.toProto(Color.White) shouldBe CacheProtos.Color.WHITE
      }
    }

    "given blue" should {
      "convert it correctly" in {
        ColorAdapter.toProto(Color.Blue) shouldBe CacheProtos.Color.BLUE
      }
    }

    "given black" should {
      "convert it correctly" in {
        ColorAdapter.toProto(Color.Black) shouldBe CacheProtos.Color.BLACK
      }
    }

    "given red" should {
      "convert it correctly" in {
        ColorAdapter.toProto(Color.Red) shouldBe CacheProtos.Color.RED
      }
    }

    "given green" should {
      "convert it correctly" in {
        ColorAdapter.toProto(Color.Green) shouldBe CacheProtos.Color.GREEN
      }
    }

    "given null" should {
      "throw an exception" in {
        assertThrows[MatchError](ColorAdapter.toProto(null))
      }
    }
  }
}
