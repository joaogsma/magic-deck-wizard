package org.joaogsma.adapters.scryfall

import org.joaogsma.models.Color
import org.joaogsma.models.Color.ColorOrdering

object ColorAdapter {
  def parseToSequence(strSeq: Seq[String]): Seq[Color] = strSeq.map(parse).distinct.sorted

  protected def parse(str: String): Color = str match {
    case "W" => Color.White
    case "U" => Color.Blue
    case "B" => Color.Black
    case "R" => Color.Red
    case "G" => Color.Green
    case _ => throw new IllegalArgumentException(s"String $str does not correspond to a Color")
  }
}
