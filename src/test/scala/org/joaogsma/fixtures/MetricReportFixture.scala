package org.joaogsma.fixtures

import org.joaogsma.entities.models.{Color, MetricReport, Type}

object MetricReportFixture {
  def buildEmpty: MetricReport =
      MetricReport(0, Map.empty, Map.empty, Map.empty, Map.empty, Map.empty)

  def build: MetricReport =
      MetricReport(
        cardCount = 11,
        tagCount = Map("ramp" -> 2, "draw" -> 1, "removal" -> 1),
        cardTypeCount =
            Map(Type.Sorcery -> 1, Type.Instant -> 1, Type.Creature -> 1, Type.Artifact -> 1),
        colorCount = Map(Some(Color.Green) -> 1, Some(Color.Black) -> 1, None -> 1),
        manaSymbolCount = Map(Some(Color.Green) -> 1, Some(Color.Black) -> 1),
        manaCurve = Map(2.0 -> 1, 3.0 -> 1, 4.0 -> 1))
}
