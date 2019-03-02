package org.joaogsma.controllers

import cats.implicits._
import com.monovore.decline._
import org.joaogsma.metrics.countCards
import org.joaogsma.metrics.countManaCurve
import org.joaogsma.metrics.countTags
import org.joaogsma.metrics.countTypes
import org.joaogsma.metrics.countColors
import org.joaogsma.metrics.countManaSymbols
import org.joaogsma.models.DeckEntry
import org.joaogsma.ports.file.DeckPort
import org.joaogsma.ports.ui.ScalaFxPort
import org.joaogsma.ports.web.ScryfallPort
import org.joaogsma.ports.ui.ConsolePort

import scala.util.Failure
import scala.util.Success

object ConsoleController extends App {
  private val CONSOLE_MODE = 1
  private val WINDOWED_MODE = 2

  parseArguments(args) match {
    case Left(help) => println(help)
    case Right((mode, file)) =>
      readDeckList(file)
          .map(entries => {
            val filledEntries = entries.map(fillWithScryfallData)
            maybeWriteFilledDeckList(file, entries, filledEntries)
            filledEntries
          })
          .foreach(entries => {
            mode match {
              case CONSOLE_MODE => println(createMetricsString(entries))
              case WINDOWED_MODE => ScalaFxPort.initialize(entries)
            }
          })
  }

  def parseArguments(args: Seq[String]): Either[Help, (Int, String)] = {
    val consoleOpts = Opts
        .flag(long = "console", short = "c", help = "Print the results on the console")
        .map(_ => CONSOLE_MODE)
    val windowedOpts = Opts
        .flag(long = "windowed", short = "w", help = "Print the results on separate windows")
        .map(_ => WINDOWED_MODE)
    val fileOpts = Opts.argument[String]("file")

    Command("Parse the arguments", "Parse the initialization options")(
      (consoleOpts orElse windowedOpts, fileOpts).mapN(Tuple2.apply))
        .parse(args)
  }

  def readDeckList(filename: String): Option[Seq[DeckEntry]] = {
    DeckPort.read(filename) match {
      case Failure(exception) =>
        println(s"[ERROR] ${exception.getMessage}")
        None
      case Success(entries) => Option(entries)
    }
  }

  def fillWithScryfallData(entry: DeckEntry): DeckEntry = entry.card match {
    case Some(_) => entry
    case None =>
      print(s""" Searching Scryfall for missing information on card "${entry.name}"...""")
      ScryfallPort.searchCardName(entry.name) match {
        case Success(card) =>
          println("done")
          entry.copy(card = Some(card))
        case Failure(_) =>
          println(
            s"\n[ERROR] Could not get missing information of the card ${entry.name} from Scryfall.")
          entry
      }
  }

  private def maybeWriteFilledDeckList(
      originalFile: String,
      originalEntries: Seq[DeckEntry],
      filledEntries: Seq[DeckEntry]): Unit = {
    if (originalEntries.forall(_.card.nonEmpty) || filledEntries.exists(_.card.isEmpty))
      return

    val shouldWriteFilledDeckList = ConsolePort
        .queryUser("Save filled deck list? [y/n]", Set("y", "n"))
        .contains("y")

    if (!shouldWriteFilledDeckList)
      return

    val filledDeckListFile =
      originalFile.lastIndexOf('.') match {
        case -1 => originalFile + "_filled"
        case formatStart =>
          (originalFile.substring(0, formatStart) + "_filled"
              + originalFile.substring(formatStart))
      }

    DeckPort.write(filledEntries, filledDeckListFile)
    println(s"[INFO] Filled deck list saved at $filledDeckListFile")
  }

  private def createMetricsString(entries: Seq[DeckEntry]): String = {
    val result: StringBuilder = StringBuilder.newBuilder

    val totalCardCount: Int = countCards(entries)
    result.append(s"Total number of cards: $totalCardCount\n")

    val tagCount: Map[String, Int] = countTags(entries)

    if (tagCount.nonEmpty) {
      val maxTagLength: Int = tagCount.keys.map(_.length).max
      result.append("Tags:\n")
      tagCount
          .map { case (tag, count) =>
            val countStr = if (count < 10) "0" + count.toString else count.toString
            val padding = List.fill(maxTagLength - tag.length + 1)('=').mkString
            s"  - $tag $padding> count = $countStr\n"
          }
          .toSeq
          .sorted
          .foreach(result.append)
    } else {
      result.append("No tags to be shown.")
    }

    if (entries.forall(_.card.isDefined)) {
      result.append("Mana curve:\n")
      countManaCurve(entries)
          .filter(_._2 > 0)
          .toList
          .sorted
          .foreach { case (cost, count) => result.append(s"  - $cost: $count\n") }
    } else {
      result.append(
        "Mana curve cannot be shown because one or more cards have missing information.\n")
    }

    if (entries.forall(_.card.isDefined)) {
      result.append("Card Types:\n")
      countTypes(entries)
          .filter(_._2 > 0)
          .toList
          .sorted
          .foreach { case (cardType, count) => result.append(s"  - $cardType: $count\n") }
    } else {
      result.append(
        "Card types cannot be shown because one or more cards have missing information.\n")
    }

    if (entries.forall(_.card.isDefined)) {
      result.append("Colors:\n")
      countColors(entries)
          .filter(_._2 > 0)
          .toList
          .sorted
          .foreach {
            case (Some(color), count) => result.append(s"  - $color: $count\n")
            case (None, count) => result.append(s"  - Colorless: $count\n")
          }
    } else {
      result.append("Colors cannot be shown because one or more cards have missing information.\n")
    }

    if (entries.forall(_.card.isDefined)) {
      result.append("Mana Symbols:\n")
      countManaSymbols(entries)
          .filter(_._2 > 0)
          .toList
          .sorted
          .foreach {
            case (Some(color), count) => result.append(s"  - $color: $count\n")
            case (None, count) => result.append(s"  - Colorless: $count\n")
          }
    } else {
      result.append(
        "Mana symbols cannot be shown because one or more cards have missing information.\n")
    }

    result.toString
  }
}
