package org.joaogsma

import org.joaogsma.models.DeckEntry

package object metrics
{
  def countCards(entries: Seq[DeckEntry]): Int = entries.map(_.count).sum

  def countTags(entries: Seq[DeckEntry]): Map[String, Int] =
  {
    entries
        .flatMap(card => card.tags.map(_ -> card.count))
        .groupBy(_._1)
        .mapValues(_.map(_._2).sum)
  }
}
