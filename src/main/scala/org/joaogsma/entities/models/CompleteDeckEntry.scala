package org.joaogsma.entities.models

case class CompleteDeckEntry(count: Int, card: Card, tags: Set[String]) extends DecklistEntry