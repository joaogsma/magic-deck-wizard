package org.joaogsma.fixtures

import org.joaogsma.entities.models.{Card, Color, Comment, CompleteDeckEntry, DeckEntry, DecklistEntry, EmptyLine, Mana, Type}

object DecklistFixture {
  def buildInitial: Seq[DecklistEntry] =
    buildFinal
        .map {
          case de: CompleteDeckEntry => DeckEntry(de.count, de.card.name, de.tags)
          case de => de
        }

  def buildFinal: Seq[DecklistEntry] =
      Seq(
        Comment("This is an initial comment"),
        CompleteDeckEntry(
          4,
          Card(
            "Kodama's Reach",
            Set(Mana.Generic(2), Mana.Green(1)),
            Set(Color.Green),
            Set(Type.Sorcery),
            3.0),
          Set("ramp")),
        EmptyLine,
        CompleteDeckEntry(
          4,
          Card(
            "Solemn Simulacrum",
            Set(Mana.Generic(4)),
            Set.empty,
            Set(Type.Creature, Type.Artifact),
            4.0),
          Set("ramp", "draw")),
        EmptyLine,
        CompleteDeckEntry(
          3,
          Card(
            "Doomblade",
            Set(Mana.Generic(1), Mana.Black(1)),
            Set(Color.Black),
            Set(Type.Instant),
            2.0),
          Set("removal")),
        Comment("This is a final comment"))
}
