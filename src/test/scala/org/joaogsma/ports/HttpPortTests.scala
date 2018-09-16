package org.joaogsma.ports

import org.scalatest.Matchers
import org.scalatest.WordSpec

class HttpPortTests extends WordSpec with Matchers
{
  object Test extends HttpPort

  "The get function" when
  {
    "given a valid url" should
    {
      "connect successfully" in
      {
        Test.get("https://httpbin.org/get").nonEmpty shouldBe true
      }
    }
  }

  "The get function" when
  {
    "given an invalid url" should
    {
      "throw an exception" in
      {
        assertThrows[java.net.UnknownHostException](Test.get("https://some-invalid-url"))
      }
    }
  }
}
