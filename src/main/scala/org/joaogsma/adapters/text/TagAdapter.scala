package org.joaogsma.adapters.text

import scala.util.Failure
import scala.util.Try
import scala.util.matching.Regex

object TagAdapter {
  private val TAG_REGEX: Regex = "@[^ @]+".r

  val TAG_GROUP_REGEX: Regex = s"$TAG_REGEX( +$TAG_REGEX)*".r

  def parseToSequence(str: String): Try[Set[String]] = {
    if (!str.matches(TAG_GROUP_REGEX.toString)) {
      Failure(new IllegalArgumentException("Malformed tags: $str"))
    } else {
      Try {
        TAG_REGEX
            .findAllMatchIn(str)
            .map(_.matched)
            .toSet
            .map((_: String).substring(1))
      }
    }
  }

  def toString(tags: Set[String]): String = tags.ensuring(_ != null).map('@' + _).mkString(" ")
}
