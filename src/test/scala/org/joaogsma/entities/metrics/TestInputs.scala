package org.joaogsma.entities.metrics

import org.joaogsma.entities.models.Card
import org.joaogsma.entities.models.Color
import org.joaogsma.entities.models.DeckEntry
import org.joaogsma.entities.models.Mana
import org.joaogsma.entities.models.Type

//object TestInputs {
//  def tatyovaStandaloneEntries: Seq[DeckEntry] = List(
//    DeckEntry(
//      1,
//      "Kefnet the Mindful",
//      Some(
//        Card(
//          List(Mana.Generic(2), Mana.Blue(1)),
//          List(Color.Blue),
//          List(Type.Creature),
//          3)),
//      Set("cmc_3", "blue", "creature", "draw")),
//    DeckEntry(
//      1,
//      "Sylvan Offering",
//      Some(
//        Card(
//          List(Mana.X(1), Mana.Green(1)),
//          List(Color.Green),
//          List(Type.Sorcery),
//          1)),
//      Set("cmc_1", "green", "sorcery", "political", "token_generator")),
//    DeckEntry(
//      1,
//      "Coiling Oracle",
//      Some(
//        Card(
//          List(Mana.Blue(1), Mana.Green(1)),
//          List(Color.Blue, Color.Green),
//          List(Type.Creature),
//          2)),
//      Set("cmc_2", "blue", "green", "creature")),
//    DeckEntry(
//      1,
//      "Blackblade Reforged",
//      Some(
//        Card(
//          List(Mana.Generic(2)),
//          List.empty,
//          List(Type.Artifact),
//          2)),
//      Set("cmc_2", "colorless", "artifact")),
//    DeckEntry(
//      1,
//      "Nissa, Steward of Elements",
//      Some(
//        Card(
//          List(Mana.X(1), Mana.Blue(1), Mana.Green(1)),
//          List(Color.Blue, Color.Green),
//          List(Type.Planeswalker),
//          2)),
//      Set("cmc_2", "blue", "green", "planeswalker", "scry", "land_to_battlefield")),
//    DeckEntry(
//      1,
//      "Retreat to Kazandu",
//      Some(
//        Card(
//          List(Mana.Generic(2), Mana.Green(1)),
//          List(Color.Green),
//          List(Type.Enchantment),
//          3)),
//      Set("cmc_3", "green", "enchantment", "life_gain", "landfall")),
//    DeckEntry(
//      1,
//      "Retreat to Coralhelm",
//      Some(
//        Card(
//          List(Mana.Generic(2), Mana.Blue(1)),
//          List(Color.Blue),
//          List(Type.Enchantment),
//          3)),
//      Set("cmc_3", "blue", "enchantment", "scry", "landfall")),
//    DeckEntry(
//      1,
//      "Seed the Land",
//      Some(
//        Card(
//          List(Mana.Generic(2), Mana.Green(2)),
//          List(Color.Green),
//          List(Type.Enchantment),
//          4)),
//      Set("cmc_4", "green", "enchantment", "token_generator", "landfall")),
//    DeckEntry(
//      1,
//      "Centaur Glade",
//      Some(
//        Card(
//          List(Mana.Generic(3), Mana.Green(2)),
//          List(Color.Green),
//          List(Type.Enchantment),
//          5)),
//      Set("cmc_5", "green", "enchantment", "token_generator", "mana_sink")),
//    DeckEntry(
//      1,
//      "Venser's Journal",
//      Some(
//        Card(
//          List(Mana.Generic(5)),
//          List.empty,
//          List(Type.Artifact),
//          5)),
//      Set("cmc_5", "colorless", "artifact", "life_gain")),
//    DeckEntry(
//      1,
//      "Future Sight",
//      Some(
//        Card(
//          List(Mana.Generic(2), Mana.Blue(3)),
//          List(Color.Blue),
//          List(Type.Enchantment),
//          5)),
//      Set("cmc_5", "blue", "enchantment")),
//    DeckEntry(
//      1,
//      "Mystic Confluence",
//      Some(
//        Card(
//          List(Mana.Generic(3), Mana.Blue(2)),
//          List(Color.Blue),
//          List(Type.Instant),
//          5)),
//      Set("cmc_5", "blue", "instant", "draw", "counter", "bounce")),
//    DeckEntry(
//      1,
//      "Zendikar's Roil",
//      Some(
//        Card(
//          List(Mana.Generic(3), Mana.Green(2)),
//          List(Color.Green),
//          List(Type.Enchantment),
//          5)),
//      Set("cmc_5", "green", "enchantment", "landfall", "token_generator")),
//    DeckEntry(
//      1,
//      "Seasons Past",
//      Some(
//        Card(
//          List(Mana.Generic(4), Mana.Green(2)),
//          List(Color.Green),
//          List(Type.Sorcery),
//          6)),
//      Set("cmc_6", "green", "sorcery", "graveyard_recursion")),
//    DeckEntry(
//      1,
//      "Multani, Yavimaya's Avatar",
//      Some(
//        Card(
//          List(Mana.Generic(4), Mana.Green(2)),
//          List(Color.Green),
//          List(Type.Creature),
//          6)),
//      Set("cmc_6", "green", "creature")),
//    DeckEntry(
//      1,
//      "Rampaging Baloths",
//      Some(
//        Card(
//          List(Mana.Generic(4), Mana.Green(2)),
//          List(Color.Green),
//          List(Type.Creature),
//          6)),
//      Set("cmc_6", "green", "creature", "token_generator")),
//    DeckEntry(
//      1,
//      "Protean Hulk",
//      Some(
//        Card(
//          List(Mana.Generic(5), Mana.Green(2)),
//          List(Color.Green),
//          List(Type.Creature),
//          7)),
//      Set("cmc_7", "green", "creature")),
//    DeckEntry(
//      1,
//      "Howl of the Night Pack",
//      Some(
//        Card(
//          List(Mana.Generic(6), Mana.Green(1)),
//          List(Color.Green),
//          List(Type.Sorcery),
//          7)),
//      Set("cmc_7", "green", "sorcery", "token_generator")),
//    DeckEntry(
//      1,
//      "Praetor's Counsel",
//      Some(
//        Card(
//          List(Mana.Generic(5), Mana.Green(3)),
//          List(Color.Green),
//          List(Type.Sorcery),
//          8)),
//      Set("cmc_8", "green", "sorcery", "graveyard_recursion")))
//}
