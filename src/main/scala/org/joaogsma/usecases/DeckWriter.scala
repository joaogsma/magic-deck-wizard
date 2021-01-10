package org.joaogsma.usecases

import org.joaogsma.entities.models.DecklistEntry

trait DeckWriter {
  def write(deckPath: String, cards: IterableOnce[DecklistEntry]): Unit
}
