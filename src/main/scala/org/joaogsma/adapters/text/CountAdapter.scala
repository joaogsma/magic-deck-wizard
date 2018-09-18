package org.joaogsma.adapters.text

import scala.util.Try
import scala.util.matching.Regex

object CountAdapter
{
  val COUNT_REGEX: Regex = "\\[\\d+\\]".r

  def parseCount(str: String): Try[Int] =
  {
    exactMatch(COUNT_REGEX, str)
        .map(string => string.substring(1, string.length - 1).toInt)
        .map
        {
          case n if n > 0 => n
          case n => throw new IllegalArgumentException(s"Count must be positive, but found $n")
        }
  }
}
