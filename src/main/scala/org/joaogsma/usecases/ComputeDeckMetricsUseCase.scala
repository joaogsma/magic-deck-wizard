package org.joaogsma.usecases

import org.joaogsma.entities.metrics.MetricReportFactory
import org.joaogsma.entities.models.Card
import org.joaogsma.entities.models.CompleteDeckEntry
import org.joaogsma.entities.models.DeckEntry
import org.joaogsma.entities.models.DecklistEntry
import org.joaogsma.entities.models.MetricReport

class ComputeDeckMetricsUseCase(
  cardRepository: CardRepository,
  metricReportFactory: MetricReportFactory,
  metricReporter: MetricReporter,
  deckWriter: DeckWriter) {

  def run(deckPath: String, decklist: Iterable[DecklistEntry]): Unit = {
    val deckEntries: Seq[DeckEntry] =
        decklist.filter(_.isInstanceOf[DeckEntry]).map(_.asInstanceOf[DeckEntry]).toSeq
    if (deckEntries.isEmpty) {
      metricReporter.report(MetricReport.empty)
      return
    }

    val alreadyCompleteDeckEntries: Iterator[CompleteDeckEntry] =
        deckEntries.iterator.filter(isComplete).map(toComplete)
    val incompleteDeckEntries: Iterator[DeckEntry] = deckEntries.iterator.filterNot(isComplete)
    val decklistIsIncomplete: Boolean = incompleteDeckEntries.nonEmpty
    val foundDeckEntries: Iterator[CompleteDeckEntry] = findMissingCardData(incompleteDeckEntries)
    val completeDeckEntries: Seq[CompleteDeckEntry] =
        (alreadyCompleteDeckEntries ++ foundDeckEntries).toSeq

    if (decklistIsIncomplete) {
      deckWriter.write(deckPath, buildCompleteDecklist(decklist, completeDeckEntries))
    }
    val metricReport: MetricReport = metricReportFactory.from(completeDeckEntries)
    metricReporter.report(metricReport)
  }

  private def isComplete(deckEntry: DeckEntry): Boolean = {
    (deckEntry.manaCost.isDefined
        && deckEntry.colors.isDefined
        && deckEntry.types.isDefined
        && deckEntry.cmc.isDefined)
  }

  private def toComplete(de: DeckEntry): CompleteDeckEntry =
      CompleteDeckEntry(
        de.count,
        Card(de.name, de.manaCost.get, de.colors.get, de.types.get, de.cmc.get),
        de.tags)

  private def findMissingCardData(
    incompleteEntries: IterableOnce[DeckEntry]
  ): Iterator[CompleteDeckEntry] = {
    val cardMap: Map[String, DeckEntry] = incompleteEntries.iterator.map(de => (de.name, de)).toMap
    if (cardMap.isEmpty) {
      return Iterator()
    }
    cardRepository.findCards(cardMap.keys)
        .iterator
        .map { card =>
          val deckEntry: DeckEntry = cardMap(card.name)
          CompleteDeckEntry(deckEntry.count, card, deckEntry.tags)
        }
  }

  private def buildCompleteDecklist(
    originalDecklist: IterableOnce[DecklistEntry],
    completeDeckEntries: IterableOnce[CompleteDeckEntry]
  ): IterableOnce[DecklistEntry] = {
    val completeDeckEntryByName: Map[String, CompleteDeckEntry] =
        completeDeckEntries.iterator.map(e => e.card.name -> e).toMap
    originalDecklist.iterator
        .map {
          case de: DeckEntry => completeDeckEntryByName(de.name)
          case de => de
        }
  }
}



