package org.joaogsma.usecases

import org.joaogsma.entities.metrics.{MetricReportFactory, countCards, countColors, countManaCurve, countManaSymbols, countTags, countTypes}
import org.joaogsma.entities.models.{Card, Color, CompleteDeckEntry, DeckEntry, Mana, MetricReport, Type}

import scala.collection.View

class ComputeDeckMetricsUseCase(
  cardRepository: CardRepository,
  metricReportFactory: MetricReportFactory,
  metricReporter: MetricReporter) {

  def run(deckEntries: Iterable[DeckEntry]): Unit = {
    println(deckEntries)
    if (deckEntries.isEmpty) {
      metricReporter.report(MetricReport.empty)
      return
    }
    val alreadyCompleteDeckEntries: View[CompleteDeckEntry] =
        deckEntries.view.filter(isComplete).map(toComplete)
    val foundDeckEntries: View[CompleteDeckEntry] =
        findMissingCardData(deckEntries.view.filterNot(isComplete))
    val metricReport: MetricReport =
        metricReportFactory.from(alreadyCompleteDeckEntries ++ foundDeckEntries)
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

  private def findMissingCardData(deckEntries: View[DeckEntry]): View[CompleteDeckEntry] = {
    if (deckEntries.isEmpty) {
      return View()
    }
    val cardMap: Map[String, DeckEntry] = deckEntries.map(de => (de.name, de)).toMap
    cardRepository.findCards(cardMap.keys)
        .view
        .map { card =>
          val deckEntry: DeckEntry = cardMap(card.name)
          CompleteDeckEntry(deckEntry.count, card, deckEntry.tags)
        }
  }
}



