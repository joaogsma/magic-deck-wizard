package org.joaogsma.adapters

import scala.util.Try
import scala.util.matching.Regex

package object text
{
  def exactMatch(regex: Regex, str: String): Try[String] =
  {
    Try(regex.findAllMatchIn(str).toList)
        .map
        {
          case regexMatch :: Nil if (regexMatch.end - regexMatch.start) == str.length =>
            regexMatch.matched
          case _ => throw new IllegalArgumentException(s"Malformed string: $str")
        }
  }
}
