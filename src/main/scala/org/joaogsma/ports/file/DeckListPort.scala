package org.joaogsma.ports.file

import org.joaogsma.adapters.text.DeckEntryAdapter
import org.joaogsma.models.DeckEntry

object DeckListPort extends FilePort
{
  def readCards(filename: String): Seq[DeckEntry] =
  {
    val deckLines: Iterator[String] = usingFile(filename, _.getLines.filter(_.nonEmpty))
    deckLines
        .map(_.trim)
        .map(DeckEntryAdapter.parse)
        .filter(_.isDefined)
        .map(_.get)
        .toList
  }
}
