package org.joaogsma.usecases

import org.joaogsma.entities.metrics.MetricReportFactory
import org.joaogsma.entities.models.{Card, CompleteDeckEntry, DeckEntry, MetricReport}
import org.joaogsma.fixtures.{CompleteDeckEntryFixture, DeckEntryFixture, MetricReportFixture}
import org.mockito.ArgumentMatchers.{any, argThat}
import org.mockito.Mockito.{never, reset, verify, when}
import org.scalatest.BeforeAndAfterEach
import org.scalatest.enablers.Aggregating
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.mockito.MockitoSugar

class ComputeDeckMetricsUseCaseTests
    extends AnyWordSpec
    with Matchers
    with MockitoSugar
    with BeforeAndAfterEach {

  var cardRepository: CardRepository = _
  var metricReportFactory: MetricReportFactory = _
  var metricReporter: MetricReporter = _
  var useCase: ComputeDeckMetricsUseCase = _

  override def beforeEach(): Unit = {
    cardRepository = mock[CardRepository]
    metricReportFactory = mock[MetricReportFactory]
    metricReporter = mock[MetricReporter]
    useCase = new ComputeDeckMetricsUseCase(cardRepository, metricReportFactory, metricReporter)
  }

  "The run function" when {
    "given an empty iterable" should {
      "report all metrics without fetching card info" in {
        useCase.run(Seq.empty)
        verify(cardRepository, never).findCards(any)
        verify(metricReportFactory, never).from(Seq.empty)
        verify(metricReporter).report(MetricReportFixture.buildEmpty)
      }
    }

    "given a deck whose entries are all filled" should {
      "report all metrics without fetching card info" in {
        val completeDeckEntries: Seq[CompleteDeckEntry] = CompleteDeckEntryFixture.buildSeq
        val metricReport: MetricReport = MetricReportFixture.build

        when(metricReportFactory.from(iterableEquals(completeDeckEntries)))
            .thenReturn(metricReport)

        useCase.run(DeckEntryFixture.buildSeqOfComplete)

        verify(cardRepository, never).findCards(any)
        verify(metricReportFactory).from(iterableEquals(completeDeckEntries))
        verify(metricReporter).report(metricReport)
      }
    }

    "given a deck with only some entries filled" should {
      "fetch the missing info only for the incomplete entries and report the metrics" in {
        val deckEntries: Seq[DeckEntry] =
            makeFirstEntriesIncomplete(2, DeckEntryFixture.buildSeqOfComplete)
        val cardsToFetch: Seq[String] = (deckEntries take 2).map(_.name)
        val completeDeckEntries: Seq[CompleteDeckEntry] = CompleteDeckEntryFixture.buildSeq
        val fetchedCards: Seq[Card] = (completeDeckEntries take 2).map(_.card)
        val metricReport: MetricReport = MetricReportFixture.build

        when(cardRepository.findCards(iterableEquals(cardsToFetch))).thenReturn(fetchedCards)
        when(metricReportFactory.from(iterableEquals(completeDeckEntries)))
            .thenReturn(metricReport)

        useCase.run(deckEntries)

        verify(metricReportFactory).from(iterableEquals(completeDeckEntries))
        verify(cardRepository).findCards(iterableEquals(cardsToFetch))
        verify(metricReporter).report(metricReport)
      }
    }
  }

  def iterableEquals[A](
    expected: Iterable[A])(
    implicit aggregating: Aggregating[Iterable[A]]
  ): Iterable[A] = {
    argThat { arg: Iterable[A] =>
      arg should contain theSameElementsAs expected
      true
    }
  }

  def makeFirstEntriesIncomplete(n: Int, deckEntries: IterableOnce[DeckEntry]): Seq[DeckEntry] = {
    val it: Iterator[DeckEntry] = deckEntries.iterator
    ((it take n).map(makeIncomplete) ++ it).toSeq
  }

  def makeIncomplete(de: DeckEntry): DeckEntry = DeckEntry(de.count, de.name, de.tags)
}
