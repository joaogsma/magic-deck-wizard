package org.joaogsma.adapters.text

import scala.util.Failure
import scala.util.Try
import scala.util.matching.Regex

object CountAdapter
{
  val COUNT_REGEX: Regex = "\\[\\d+\\]".r

  def parse(str: String): Try[Int] =
  {
    if (!str.matches(COUNT_REGEX.toString))
      Failure(new IllegalArgumentException(s"Malformed count: $str"))
    else
    {
      Try(str.substring(1, str.length - 1).toInt)
          .filter(_ > 0)
          .recoverWith { case _ => Failure(new IllegalArgumentException(s"Malformed count: $str")) }
    }
  }
}
