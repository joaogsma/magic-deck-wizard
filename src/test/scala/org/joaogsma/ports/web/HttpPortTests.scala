package org.joaogsma.ports.web

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class HttpPortTests extends AnyWordSpec with Matchers {

  object Test extends HttpPort

  "The get function" when {
    "given a valid url" should {
      "connect successfully" in {
        val result = Test.get(
          "https://httpbin.org/get",
          readTimeout = 10000,
          connectTimeout = 10000)
        result.nonEmpty shouldBe true
      }
    }
  }

  "The get function" when {
    "given an invalid url" should {
      "throw an exception" in {
        assertThrows[java.net.UnknownHostException](Test.get("https://some-invalid-url"))
      }
    }
  }
}
