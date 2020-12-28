package org.joaogsma.adapters.proto

import org.joaogsma.entities.models.Mana
import org.joaogsma.entities.models.proto.CacheProtos

import scala.util.Try

object ManaAdapter {
  def fromProto(manaCostEntry: CacheProtos.ManaCostEntry): Try[Mana] = {
    Try {
      val count: Int = manaCostEntry.getCount.ensuring(_ > 0)
      val manaApply: Int => Mana = manaCostEntry.getType match {
        case CacheProtos.ManaType.X_MANA => Mana.X.apply
        case CacheProtos.ManaType.GENERIC_MANA => Mana.Generic.apply
        case CacheProtos.ManaType.COLORLESS_MANA => Mana.Colorless.apply
        case CacheProtos.ManaType.WHITE_MANA => Mana.White.apply
        case CacheProtos.ManaType.BLUE_MANA => Mana.Blue.apply
        case CacheProtos.ManaType.BLACK_MANA => Mana.Black.apply
        case CacheProtos.ManaType.RED_MANA => Mana.Red.apply
        case CacheProtos.ManaType.GREEN_MANA => Mana.Green.apply

        case CacheProtos.ManaType.HYBRID_MONO_WHITE_MANA => Mana.HybridMonoWhite.apply
        case CacheProtos.ManaType.HYBRID_MONO_BLUE_MANA => Mana.HybridMonoBlue.apply
        case CacheProtos.ManaType.HYBRID_MONO_BLACK_MANA => Mana.HybridMonoBlack.apply
        case CacheProtos.ManaType.HYBRID_MONO_RED_MANA => Mana.HybridMonoRed.apply
        case CacheProtos.ManaType.HYBRID_MONO_GREEN_MANA => Mana.HybridMonoGreen.apply

        case CacheProtos.ManaType.HYBRID_WHITE_BLUE_MANA => Mana.HybridWhiteBlue.apply
        case CacheProtos.ManaType.HYBRID_WHITE_BLACK_MANA => Mana.HybridWhiteBlack.apply
        case CacheProtos.ManaType.HYBRID_WHITE_RED_MANA => Mana.HybridWhiteRed.apply
        case CacheProtos.ManaType.HYBRID_WHITE_GREEN_MANA => Mana.HybridWhiteGreen.apply
        case CacheProtos.ManaType.HYBRID_BLUE_BLACK_MANA => Mana.HybridBlueBlack.apply
        case CacheProtos.ManaType.HYBRID_BLUE_RED_MANA => Mana.HybridBlueRed.apply
        case CacheProtos.ManaType.HYBRID_BLUE_GREEN_MANA => Mana.HybridBlueGreen.apply
        case CacheProtos.ManaType.HYBRID_BLACK_RED_MANA => Mana.HybridBlackRed.apply
        case CacheProtos.ManaType.HYBRID_BLACK_GREEN_MANA => Mana.HybridBlackGreen.apply
        case CacheProtos.ManaType.HYBRID_RED_GREEN_MANA => Mana.HybridRedGreen.apply

        case CacheProtos.ManaType.PHYREXIAN_WHITE_MANA => Mana.PhyrexianWhite.apply
        case CacheProtos.ManaType.PHYREXIAN_BLUE_MANA => Mana.PhyrexianBlue.apply
        case CacheProtos.ManaType.PHYREXIAN_BLACK_MANA => Mana.PhyrexianBlack.apply
        case CacheProtos.ManaType.PHYREXIAN_RED_MANA => Mana.PhyrexianRed.apply
        case CacheProtos.ManaType.PHYREXIAN_GREEN_MANA => Mana.PhyrexianGreen.apply

        case _ => throw new MatchError("Unknown mana type")
      }
      manaApply(count)
    }
  }

  def toProto(mana: Mana): CacheProtos.ManaCostEntry = {
    val count: Int = mana.count
    val manaType: CacheProtos.ManaType = mana match {
      case _: Mana.X => CacheProtos.ManaType.X_MANA
      case _: Mana.Generic => CacheProtos.ManaType.GENERIC_MANA
      case _: Mana.Colorless => CacheProtos.ManaType.COLORLESS_MANA
      case _: Mana.White => CacheProtos.ManaType.WHITE_MANA
      case _: Mana.Blue => CacheProtos.ManaType.BLUE_MANA
      case _: Mana.Black => CacheProtos.ManaType.BLACK_MANA
      case _: Mana.Red => CacheProtos.ManaType.RED_MANA
      case _: Mana.Green => CacheProtos.ManaType.GREEN_MANA

      case _: Mana.HybridMonoWhite => CacheProtos.ManaType.HYBRID_MONO_WHITE_MANA
      case _: Mana.HybridMonoBlue => CacheProtos.ManaType.HYBRID_MONO_BLUE_MANA
      case _: Mana.HybridMonoBlack => CacheProtos.ManaType.HYBRID_MONO_BLACK_MANA
      case _: Mana.HybridMonoRed => CacheProtos.ManaType.HYBRID_MONO_RED_MANA
      case _: Mana.HybridMonoGreen => CacheProtos.ManaType.HYBRID_MONO_GREEN_MANA

      case _: Mana.HybridWhiteBlue => CacheProtos.ManaType.HYBRID_WHITE_BLUE_MANA
      case _: Mana.HybridWhiteBlack => CacheProtos.ManaType.HYBRID_WHITE_BLACK_MANA
      case _: Mana.HybridWhiteRed => CacheProtos.ManaType.HYBRID_WHITE_RED_MANA
      case _: Mana.HybridWhiteGreen => CacheProtos.ManaType.HYBRID_WHITE_GREEN_MANA
      case _: Mana.HybridBlueBlack => CacheProtos.ManaType.HYBRID_BLUE_BLACK_MANA
      case _: Mana.HybridBlueRed => CacheProtos.ManaType.HYBRID_BLUE_RED_MANA
      case _: Mana.HybridBlueGreen => CacheProtos.ManaType.HYBRID_BLUE_GREEN_MANA
      case _: Mana.HybridBlackRed => CacheProtos.ManaType.HYBRID_BLACK_RED_MANA
      case _: Mana.HybridBlackGreen => CacheProtos.ManaType.HYBRID_BLACK_GREEN_MANA
      case _: Mana.HybridRedGreen => CacheProtos.ManaType.HYBRID_RED_GREEN_MANA

      case _: Mana.PhyrexianWhite => CacheProtos.ManaType.PHYREXIAN_WHITE_MANA
      case _: Mana.PhyrexianBlue => CacheProtos.ManaType.PHYREXIAN_BLUE_MANA
      case _: Mana.PhyrexianBlack => CacheProtos.ManaType.PHYREXIAN_BLACK_MANA
      case _: Mana.PhyrexianRed => CacheProtos.ManaType.PHYREXIAN_RED_MANA
      case _: Mana.PhyrexianGreen => CacheProtos.ManaType.PHYREXIAN_GREEN_MANA

      case _ => throw new MatchError("Unknown mana type")
    }
    CacheProtos.ManaCostEntry.newBuilder().setCount(count).setType(manaType).build()
  }
}

