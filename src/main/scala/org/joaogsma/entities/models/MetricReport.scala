package org.joaogsma.entities.models

case class MetricReport(
  cardCount: Int,
  tagCount: Map[String, Int],
  cardTypeCount: Map[Type, Int],
  colorCount: Map[Option[Color], Int],
  manaSymbolCount: Map[Option[Color], Int],
  manaCurve: Map[Double, Int])

object MetricReport {
  def empty: MetricReport = MetricReport(0, Map.empty, Map.empty, Map.empty, Map.empty, Map.empty)
}