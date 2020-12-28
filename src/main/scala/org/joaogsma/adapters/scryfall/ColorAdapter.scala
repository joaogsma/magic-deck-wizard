package org.joaogsma.adapters.scryfall

import org.joaogsma.entities.models.Color
import org.joaogsma.entities.models.Color.ColorOrdering

object ColorAdapter {
  def parseToSet(strSeq: Seq[String]): Set[Color] = strSeq.map(parse).toSet

  protected def parse(str: String): Color = str match {
    case "W" => Color.White
    case "U" => Color.Blue
    case "B" => Color.Black
    case "R" => Color.Red
    case "G" => Color.Green
    case _ => throw new IllegalArgumentException(s"String $str does not correspond to a Color")
  }
}
