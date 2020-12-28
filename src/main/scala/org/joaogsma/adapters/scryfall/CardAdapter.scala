package org.joaogsma.adapters.scryfall

import io.circe.Decoder
import io.circe.HCursor
import io.circe.Json
import org.joaogsma.entities.models.Card

import scala.util.Try

object CardAdapter {
  private val NAME_FIELD: String = "name"
  private val MANA_COST_FIELD: String = "mana_cost"
  private val CMC_FIELD: String = "cmc"
  private val TYPE_LINE_FIELD: String = "type_line"
  private val COLORS_FIELD: String = "colors"
  private val CARD_FACES_FIELD: String = "card_faces"

  def jsonToCard(json: Json): Try[Card] = {
    val cursor: HCursor = json.hcursor

    val name: String = getFieldFromCardOrFirstCardFace[String](cursor, NAME_FIELD)
    val manaCost: String = getFieldFromCardOrFirstCardFace[String](cursor, MANA_COST_FIELD)
    val cmc: Double = getFieldFromCardOrFirstCardFace[Double](cursor, CMC_FIELD)
    val typeLine: String = getFieldFromCardOrFirstCardFace[String](cursor, TYPE_LINE_FIELD)
    val colors: Seq[String] = getFieldFromCardOrFirstCardFace[Seq[String]](cursor, COLORS_FIELD)

    Try(
      Card(
        name,
        ManaAdapter.parseToSet(manaCost),
        ColorAdapter.parseToSet(colors),
        TypeAdapter.parseToSet(typeLine),
        cmc))
  }

  def getFieldFromCardOrFirstCardFace[A : Decoder](cursor: HCursor, field: String): A = {
    cursor
        .get[A](field)
        .toTry
        .recoverWith { case _ => cursor.downField(CARD_FACES_FIELD).downArray.get[A](field).toTry }
        .get
  }
}
