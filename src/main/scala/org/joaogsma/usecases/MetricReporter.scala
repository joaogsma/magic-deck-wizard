package org.joaogsma.usecases

import org.joaogsma.entities.models.MetricReport

trait MetricReporter {
  def report(metricReport: MetricReport): Unit;
}