package org.joaogsma.adapters.text

import scala.util.Try
import scala.util.matching.Regex

object TagAdapter
{
  val TAG_GROUP_REGEX: Regex = "@[^ @]+( @[^ @]+)*".r

  private val TAG_REGEX: Regex = "@[^ @]+".r

  def parseToSequence(str: String): Try[Set[String]] =
  {
    exactMatch(TAG_GROUP_REGEX, str)
        .map(string =>
            TAG_REGEX
                .findAllMatchIn(string)
                .map(_.matched)
                .toSet
                .map((_: String).replaceFirst("@", ""))
        )
  }
}
