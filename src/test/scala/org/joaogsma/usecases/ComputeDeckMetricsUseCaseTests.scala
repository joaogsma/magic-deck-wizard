package org.joaogsma.usecases

import org.joaogsma.entities.metrics.MetricReportFactory
import org.joaogsma.entities.models.Card
import org.joaogsma.entities.models.CompleteDeckEntry
import org.joaogsma.entities.models.DeckEntry
import org.joaogsma.entities.models.DecklistEntry
import org.joaogsma.entities.models.MetricReport
import org.joaogsma.fixtures.CompleteDeckEntryFixture
import org.joaogsma.fixtures.DeckEntryFixture
import org.joaogsma.fixtures.DecklistFixture
import org.joaogsma.fixtures.MetricReportFixture
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.anyString
import org.mockito.ArgumentMatchers.argThat
import org.mockito.Mockito.never
import org.mockito.Mockito.verify
import org.mockito.Mockito.when
import org.mockito.ArgumentCaptor
import org.mockito.ArgumentMatchers
import org.scalatest.BeforeAndAfterEach
import org.scalatest.enablers.Aggregating
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.mockito.MockitoSugar

import scala.collection.IterableOnce
import scala.reflect.ClassTag
import scala.reflect.classTag

class ComputeDeckMetricsUseCaseTests
    extends AnyWordSpec
    with Matchers
    with MockitoSugar
    with BeforeAndAfterEach {

  var cardRepository: CardRepository = _
  var metricReportFactory: MetricReportFactory = _
  var metricReporter: MetricReporter = _
  var deckWriter: DeckWriter = _
  var useCase: ComputeDeckMetricsUseCase = _

  override def beforeEach(): Unit = {
    cardRepository = mock[CardRepository]
    metricReportFactory = mock[MetricReportFactory]
    metricReporter = mock[MetricReporter]
    deckWriter = mock[DeckWriter]
    useCase =
        new ComputeDeckMetricsUseCase(
          cardRepository,
          metricReportFactory,
          metricReporter,
          deckWriter)
  }

  "The run function" when {
    "given an empty iterable" should {
      "report all metrics without fetching card info" in {
        useCase.run("a/path", Seq.empty)
        verify(cardRepository, never).findCards(any)
        verify(metricReportFactory, never).from(Seq.empty)
        verify(metricReporter).report(MetricReportFixture.buildEmpty)
        verify(deckWriter, never).write(anyString, any)
      }
    }

    "given a deck whose entries are all filled" should {
      "report all metrics without fetching card info" in {
        val deckPath: String = "a/path"
        val completeDeckEntries: Seq[CompleteDeckEntry] = CompleteDeckEntryFixture.buildSeq
        val metricReport: MetricReport = MetricReportFixture.build

        when(metricReportFactory.from(iterableEquals(completeDeckEntries)))
            .thenReturn(metricReport)

        useCase.run(deckPath, DeckEntryFixture.buildSeqOfComplete)

        verify(cardRepository, never).findCards(any)
        verify(metricReportFactory).from(iterableEquals(completeDeckEntries))
        verify(metricReporter).report(metricReport)
        verify(deckWriter, never).write(anyString, any)
      }
    }

    "given a deck with no entries filled" should {
      "fetch the missing info for all entries, report the metrics and write the decklist" in {
        val deckPath: String = "a/path"
        val deckEntries: Seq[DeckEntry] = DeckEntryFixture.buildSeq
        val cardsToFetch: Seq[String] = deckEntries.map(_.name)
        val completeDeckEntries: Seq[CompleteDeckEntry] = CompleteDeckEntryFixture.buildSeq
        val fetchedCards: Seq[Card] = completeDeckEntries.map(_.card)
        val metricReport: MetricReport = MetricReportFixture.build

        when(cardRepository.findCards(iterableEquals(cardsToFetch))).thenReturn(fetchedCards)
        when(metricReportFactory.from(iterableEquals(completeDeckEntries))).thenReturn(metricReport)

        useCase.run(deckPath, deckEntries)

        verify(metricReportFactory).from(iterableEquals(completeDeckEntries))
        verify(cardRepository).findCards(iterableEquals(cardsToFetch))
        verify(metricReporter).report(metricReport)
        val FinalDecklistCaptor: ArgumentCaptor[IterableOnce[DecklistEntry]] = buildArgCapture
        verify(deckWriter).write(ArgumentMatchers.eq(deckPath), FinalDecklistCaptor.capture())
        val actualFinalDecklist: Seq[DecklistEntry] = FinalDecklistCaptor.getValue().iterator.toSeq
        actualFinalDecklist should contain theSameElementsAs completeDeckEntries
      }
    }

    "given a deck with only some entries filled" should {
      "fetch the missing info only for the incomplete entries, report the metrics and write the decklist" in {
        val deckPath: String = "a/path"
        val deckEntries: Seq[DeckEntry] =
            makeFirstEntriesIncomplete(2, DeckEntryFixture.buildSeqOfComplete)
        val cardsToFetch: Seq[String] = (deckEntries take 2).map(_.name)
        val completeDeckEntries: Seq[CompleteDeckEntry] = CompleteDeckEntryFixture.buildSeq
        val fetchedCards: Seq[Card] = (completeDeckEntries take 2).map(_.card)
        val metricReport: MetricReport = MetricReportFixture.build

        when(cardRepository.findCards(iterableEquals(cardsToFetch))).thenReturn(fetchedCards)
        when(metricReportFactory.from(iterableEquals(completeDeckEntries))).thenReturn(metricReport)

        useCase.run(deckPath, deckEntries)

        verify(metricReportFactory).from(iterableEquals(completeDeckEntries))
        verify(cardRepository).findCards(iterableEquals(cardsToFetch))
        verify(metricReporter).report(metricReport)
        val FinalDecklistCaptor: ArgumentCaptor[IterableOnce[DecklistEntry]] = buildArgCapture
        verify(deckWriter).write(ArgumentMatchers.eq(deckPath), FinalDecklistCaptor.capture())
        val actualFinalDecklist: Seq[DecklistEntry] = FinalDecklistCaptor.getValue().iterator.toSeq
        actualFinalDecklist should contain theSameElementsAs completeDeckEntries
      }
    }

    "given a deck with no entries filled and including comments and empty lines" should {
      "fetch the missing info for all entries, report all the metrics and write the decklist (including comments and empty lines)" in {
        val deckPath: String = "a/path"
        val cardsToFetch: Seq[String] = DeckEntryFixture.buildSeq.map(_.name)
        val completeDeckEntries: Seq[CompleteDeckEntry] = CompleteDeckEntryFixture.buildSeq
        val fetchedCards: Seq[Card] = completeDeckEntries.map(_.card)
        val metricReport: MetricReport = MetricReportFixture.build

        when(cardRepository.findCards(iterableEquals(cardsToFetch))).thenReturn(fetchedCards)
        when(metricReportFactory.from(iterableEquals(completeDeckEntries))).thenReturn(metricReport)

        useCase.run(deckPath, DecklistFixture.buildInitial)

        verify(metricReportFactory).from(iterableEquals(completeDeckEntries))
        verify(cardRepository).findCards(iterableEquals(cardsToFetch))
        verify(metricReporter).report(metricReport)
        val FinalDecklistCaptor: ArgumentCaptor[IterableOnce[DecklistEntry]] = buildArgCapture
        verify(deckWriter).write(ArgumentMatchers.eq(deckPath), FinalDecklistCaptor.capture())
        val actualFinalDecklist: Seq[DecklistEntry] = FinalDecklistCaptor.getValue().iterator.toSeq
        actualFinalDecklist should contain theSameElementsAs DecklistFixture.buildFinal
      }
    }
  }

  def iterableEquals[A](
    expected: Iterable[A])(
    implicit aggregating: Aggregating[Iterable[A]]
  ): Iterable[A] = {
    argThat { arg: Iterable[A] =>
      arg.iterator.toSeq should contain theSameElementsAs expected
      true
    }
  }

  def buildArgCapture[A : ClassTag]: ArgumentCaptor[A] =
    ArgumentCaptor.forClass(classTag[A].runtimeClass.asInstanceOf[Class[A]])

  def makeFirstEntriesIncomplete(n: Int, deckEntries: IterableOnce[DeckEntry]): Seq[DeckEntry] = {
    val it: Iterator[DeckEntry] = deckEntries.iterator
    ((it take n).map(makeIncomplete) ++ it).toSeq
  }

  def makeIncomplete(de: DeckEntry): DeckEntry = DeckEntry(de.count, de.name, de.tags)
}
