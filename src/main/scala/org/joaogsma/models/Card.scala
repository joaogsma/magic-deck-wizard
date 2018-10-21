package org.joaogsma.models

case class Card(
    manaCost: Seq[Mana],
    colors: Seq[Color],
    types: Seq[Type],
    cmc: Double)
