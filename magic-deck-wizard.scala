import scala.io.Source
import scala.io.BufferedSource
import scala.util.matching.Regex

object Metrics extends App {
  val CARD_COUNT_REGEX = "^\\[\\d+\\]".r
  val TAG_REGEX = "@[^ @]+$".r

  private case class Card(count: Int, tags: Set[String])

  args.toList match {
    case List() => println("Pass the filename and tags as a command line parameters")
    case List(filename) => 
      val bufferedSource = Source.fromFile(filename)
      usingFile(bufferedSource, processFile)
  }

  private def usingFile[A](bufferedSource: BufferedSource, f: BufferedSource => A): A = {
    try {
      f(bufferedSource)
    } finally {
      bufferedSource.close()
    }
  }

  private def processFile(bufferedSource: BufferedSource): Unit = {
    val deckLines: Iterator[String] = bufferedSource.getLines.filter(_.nonEmpty)
    val cards: Seq[Card] = deckLines
        .map(_.trim)
        .map(getCard)
        .filter(_.isDefined)
        .map(_.get)
        .toSeq

    val result: StringBuilder = StringBuilder.newBuilder

    val tags: Seq[String] = cards.flatMap(_.tags).distinct
    result.append("Tags found:\n")
    tags.sorted.foreach(tag => result.append(s"  - $tag\n"))

    val totalCardCount: Int = cards.map(_.count).reduce(_ + _)
    result.append(s"Total number of cards: $totalCardCount\n")

    val tagCounts: Map[String, Int] = processTags(cards)
    val maxTagLength: Int = tags.map(_.size).max
    result.append("Tags:\n")
    tagCounts
        .map { case (tag, count) => 
          val countStr = if (count < 10) "0" + count.toString else count.toString
          val ratioStr = f"${count.toDouble / totalCardCount}%.2f"
          val padding = List.fill(maxTagLength - tag.size + 1)('=').mkString
          s"  - $tag ${padding}> count = $countStr; ratio = $ratioStr\n"
        }
        .toSeq
        .sorted
        .foreach(result.append)

    println(result.toString)
  }

  private def getCard(line: String): Option[Card] = {
    val count = getCardCount(line)
    val tags = getCardTags(line)
    Option(Card(count, tags)).filter(_.count > 0)
  }

  private def processTags(cards: Seq[Card]): Map[String, Int] = {
    cards
        .flatMap(card => card.tags.map(_ -> card.count))
        .groupBy(_._1)
        .mapValues(_.map(_._2).reduce(_ + _))
  }

  private def getCardTags(line: String) = {
    TAG_REGEX.findAllIn(line).toSet[String].map(_.replaceFirst("@", ""))
  }

  private def getCardCount(line: String) = {
    CARD_COUNT_REGEX
        .findFirstIn(line)
        .getOrElse("0")
        .replaceAll("\\]|\\[", "")
        .toInt
  }
}