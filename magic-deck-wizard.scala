import scala.io.Source
import scala.io.BufferedSource
import scala.util.matching.Regex

case class Card(count: Int, tags: Set[String])
object Card {
  def apply(line: String): Option[Card] = {
    Option(getCount(line))
        .filter(_ > 0)
        .map(Card(_, getTags(line)))
  }

  private val CARD_COUNT_REGEX = "^\\[\\d+\\]".r
  private val TAG_GROUP_REGEX = "(@[^ @]+( @[^ @]+)*)*$".r
  private val CARD_TAG_REGEX = "@[^ @]+".r

  private def getTags(line: String) = {
    TAG_GROUP_REGEX
        .findFirstIn(line)
        .filterNot(_.isEmpty)
        .map(CARD_TAG_REGEX.findAllIn(_).toSet[String].map(_.replaceFirst("@", "")))
        .getOrElse(Set.empty[String])
  }

  private def getCount(line: String) = {
    CARD_COUNT_REGEX
        .findFirstIn(line)
        .getOrElse("0")
        .replaceAll("\\]|\\[", "")
        .toInt
  }
}

object Metrics extends App {
  args.toList match {
    case List() => println("Pass the filename as a parameter")
    case List(filename) => 
      val bufferedSource = Source.fromFile(filename)
      val cards: Seq[Card] = usingFile(bufferedSource, readCards).toSeq
      print(metricsString(cards))
  }

  private def usingFile[A](bufferedSource: BufferedSource, f: BufferedSource => A): A = {
    try {
      f(bufferedSource)
    } finally {
      bufferedSource.close()
    }
  }

  private def readCards(bufferedSource: BufferedSource): Seq[Card] = {
    val deckLines: Iterator[String] = bufferedSource.getLines.filter(_.nonEmpty)
    deckLines
        .map(_.trim)
        .map(Card.apply)
        .filter(_.isDefined)
        .map(_.get)
        .toList
  }

  private def metricsString(cards: Seq[Card]): String = {
    val result: StringBuilder = StringBuilder.newBuilder

    val totalCardCount: Int = cards.map(_.count).reduce(_ + _)
    result.append(s"Total number of cards: $totalCardCount\n")

    val tagCount: Map[String, Int] = countTags(cards)
    if (!tagCount.isEmpty) {
      val maxTagLength: Int = tagCount.keys.map(_.size).max
      result.append("Tags:\n")
      tagCount
          .map { case (tag, count) => 
            val countStr = if (count < 10) "0" + count.toString else count.toString
            val ratioStr = f"${count.toDouble / totalCardCount}%.2f"
            val padding = List.fill(maxTagLength - tag.size + 1)('=').mkString
            s"  - $tag ${padding}> count = $countStr; ratio = $ratioStr\n"
          }
          .toSeq
          .sorted
          .foreach(result.append)
    }

    return result.toString
  }

  private def countTags(cards: Seq[Card]): Map[String, Int] = {
    cards
        .flatMap(card => card.tags.map(_ -> card.count))
        .groupBy(_._1)
        .mapValues(_.map(_._2).reduce(_ + _))
  }
}