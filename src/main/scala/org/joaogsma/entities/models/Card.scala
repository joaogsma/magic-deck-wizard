package org.joaogsma.entities.models

case class Card(
  name: String,
  manaCost: Set[Mana],
  colors: Set[Color],
  types: Set[Type],
  cmc: Double)