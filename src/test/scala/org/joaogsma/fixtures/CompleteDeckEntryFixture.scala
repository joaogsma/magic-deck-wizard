package org.joaogsma.fixtures

import org.joaogsma.entities.models.{Card, Color, CompleteDeckEntry, Mana, Type}

object CompleteDeckEntryFixture {
  def build: CompleteDeckEntry = buildSeq.head

  def buildSeq: Seq[CompleteDeckEntry] = {
    Seq(
      CompleteDeckEntry(
        4,
        Card(
          "Kodama's Reach",
          Set(Mana.Generic(2), Mana.Green(1)),
          Set(Color.Green),
          Set(Type.Sorcery),
          3.0),
        Set("ramp")),
      CompleteDeckEntry(
        4,
        Card(
          "Solemn Simulacrum",
          Set(Mana.Generic(4)),
          Set.empty,
          Set(Type.Creature, Type.Artifact),
          4.0),
        Set("ramp", "draw")),
      CompleteDeckEntry(
        3,
        Card(
          "Doomblade",
          Set(Mana.Generic(1), Mana.Black(1)),
          Set(Color.Black),
          Set(Type.Instant),
          2.0),
        Set("removal"))
      )
  }
}
