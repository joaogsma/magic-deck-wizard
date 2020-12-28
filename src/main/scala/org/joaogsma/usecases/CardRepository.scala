package org.joaogsma.usecases

import org.joaogsma.entities.models.Card

trait CardRepository {
  def findCards(cardNames: IterableOnce[String]): Seq[Card]
}
