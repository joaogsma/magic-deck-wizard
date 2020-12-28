package org.joaogsma.entities.metrics

import org.joaogsma.entities.models.{CompleteDeckEntry, MetricReport}

class MetricReportFactory() {
  def from(deckEntries: Iterable[CompleteDeckEntry]): MetricReport = {
    MetricReport.empty
  }
}
