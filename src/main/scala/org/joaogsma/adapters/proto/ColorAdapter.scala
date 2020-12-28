package org.joaogsma.adapters.proto

import org.joaogsma.entities.models.Color
import org.joaogsma.entities.models.proto.CacheProtos

import scala.util.Try

object ColorAdapter {
  def fromProto(color: CacheProtos.Color): Try[Color] = {
    Try {
      color match {
        case CacheProtos.Color.WHITE => Color.White
        case CacheProtos.Color.BLUE => Color.Blue
        case CacheProtos.Color.BLACK => Color.Black
        case CacheProtos.Color.RED => Color.Red
        case CacheProtos.Color.GREEN => Color.Green
        case _ => throw new MatchError("Unknown color")
      }
    }
  }

  def toProto(color: Color): CacheProtos.Color = {
    color match {
      case Color.White => CacheProtos.Color.WHITE
      case Color.Blue => CacheProtos.Color.BLUE
      case Color.Black => CacheProtos.Color.BLACK
      case Color.Red => CacheProtos.Color.RED
      case Color.Green => CacheProtos.Color.GREEN
      case _ => throw new MatchError("Unknown color")
    }
  }
}

