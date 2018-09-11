import scala.io.Source
import scala.io.BufferedSource
import scala.util.matching.Regex

object Metrics extends App {
  val CARD_COUNT_REGEX = "\\[\\d\\]".r
  val TAG_REGEX = "@[^ @]+".r

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
    val deckLines = bufferedSource.getLines.toList.filter(_.nonEmpty)
    val tags = deckLines.flatMap(line => TAG_REGEX.findAllIn(line)).toList.distinct

    println(tags)

    val totalCardCount = deckLines.map(getCardCount).reduce(_ + _)
    println(s"Total number of cards: $totalCardCount")

    val tagCounts = processTags(tags, deckLines)
    println("Tags:")
    val maxTagLength = tags.map(_.size).max
    tagCounts
        .toSeq
        .sorted
        .foreach { case (tag, count) => 
          val countStr = if (count < 10) "0" + count.toString else count.toString
          val ratioStr = f"${count.toDouble/totalCardCount}%.2f"
          val padding = (0 to maxTagLength - tag.size).map(_ => "=").reduce(_ ++ _)
          println(s"  - $tag ${padding}> count = $countStr; ratio = $ratioStr")
        }
  }

  private def processTags(tags: Seq[String], deckLines: Seq[String]): Map[String, Int] = {
    tags
        .map(tag => {
          val regex = tag.r
          val count = deckLines
              .filter(line => regex.findFirstIn(line).isDefined)
              .map(getCardCount)
              .reduce(_ + _)
          (tag, count)
        })
        .toMap
  }

  private def getCardCount(line: String) = {
    CARD_COUNT_REGEX
        .findFirstIn(line)
        .getOrElse("0")
        .replaceAll("\\]|\\[", "")
        .toInt
  }
}