package org.joaogsma.fixtures

import org.joaogsma.entities.models.{Color, DeckEntry, Mana, Type}

object DeckEntryFixture {
  def build: DeckEntry = buildSeq.head

  def buildSeq: Seq[DeckEntry] = buildSeqOfComplete.map(de => DeckEntry(de.count, de.name, de.tags))

  def buildComplete: DeckEntry = buildSeqOfComplete.head

  def buildSeqOfComplete: Seq[DeckEntry] = {
    Seq(
      DeckEntry(
        4,
        "Kodama's Reach",
        Some(Set(Mana.Generic(2), Mana.Green(1))),
        Some(Set(Color.Green)),
        Some(Set(Type.Sorcery)),
        Some(3.0),
        Set("ramp")),
      DeckEntry(
        4,
        "Solemn Simulacrum",
        Some(Set(Mana.Generic(4))),
        Some(Set.empty),
        Some(Set(Type.Creature, Type.Artifact)),
        Some(4.0),
        Set("ramp", "draw")),
      DeckEntry(
        3,
        "Doomblade",
        Some(Set(Mana.Generic(1), Mana.Black(1))),
        Some(Set(Color.Black)),
        Some(Set(Type.Instant)),
        Some(2.0),
        Set("removal"))
    )
  }
}
