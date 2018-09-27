package org.joaogsma.ports.file

import org.joaogsma.models.DeckEntry
import org.scalatest.Matchers
import org.scalatest.WordSpec

import scala.util.Failure
import scala.util.Success

class DeckListPortTests extends WordSpec with Matchers
{
  "The read function" when
  {
    "given a valid deck list" should
    {
      "read the entries successfully" in
      {
        val expected = List(
          DeckEntry(
            1,
            "Horn of Greed",
            Set("cmc_3", "colorless", "artifact", "draw", "political")),
          DeckEntry(
            1,
            "Seer's Sundial",
            Set("cmc_4", "colorless", "artifact", "draw")),
          DeckEntry(
            1,
            "Intellectual Offering",
            Set("cmc_5", "blue", "instant", "draw", "political")),
          DeckEntry(
            1,
            "Urban Evolution",
            Set("cmc_5", "blue", "green", "sorcery", "draw", "ramp",
              "extra_land_drop")),
          DeckEntry(
            1,
            "Minds Aglow",
            Set("cmc_1", "blue", "sorcery", "draw", "join_forces", "political")),
          DeckEntry(
            1,
            "Sol Ring",
            Set("cmc_1", "colorless", "artifact", "ramp")),
          DeckEntry(
            1,
            "Llanowar Scout",
            Set("cmc_2", "green", "creature", "ramp", "extra_land_drop")),
          DeckEntry(
            1,
            "Khalni Heart Expedition",
            Set("cmc_2", "green", "enchantment", "ramp", "land_to_battlefield")),
          DeckEntry(
            1,
            "Cultivate",
            Set("cmc_3", "green", "sorcery", "ramp", "land_to_hand",
              "land_to_battlefield")),
          DeckEntry(
            1,
            "Kodama's Reach",
            Set("cmc_3", "green", "sorcery", "ramp", "land_to_hand",
              "land_to_battlefield")),
          DeckEntry(
            1,
            "Harrow",
            Set("cmc_3", "green", "sorcery", "ramp", "land_to_hand",
              "land_to_battlefield")),
          DeckEntry(
            1,
            "Spring // Mind",
            Set("cmc_3", "blue", "green", "instant", "sorcery", "ramp",
              "land_to_battlefield", "draw")),
          DeckEntry(
            1,
            "Yavimaya Dryad",
            Set("cmc_3", "green", "creature", "ramp")),
          DeckEntry(
            1,
            "Wayward Swordtooth",
            Set("cmc_3", "green", "creature", "ramp", "extra_land_drop")),
          DeckEntry(
            1,
            "Explosive Vegetation",
            Set("cmc_4", "green", "ramp", "sorcery", "land_to_battlefield")),
          DeckEntry(
            1,
            "Tempt With Discovery",
            Set("cmc_4", "green", "sorcery", "land_to_battlefield", "political")),
          DeckEntry(
            1,
            "Curse of the Swine",
            Set("cmc_2", "blue", "sorcery", "wipe", "creature_removal")),
          DeckEntry(
            1,
            "Consign to Dust",
            Set("cmc_3", "green", "instant", "wipe", "artifact_removal",
              "enchantment_removal")),
          DeckEntry(
            1,
            "Ezuri's Predation",
            Set("cmc_8", "green", "sorcery", "wipe", "creature_removal",
              "token_generator")),
          DeckEntry(
            1,
            "Pongify",
            Set("cmc_1", "blue", "instant", "creature_removal")),
          DeckEntry(
            1,
            "Scavenging Ooze",
            Set("cmc_2", "green", "creature", "graveyard_hate")),
          DeckEntry(
            1,
            "Lignify",
            Set("cmc_2", "green", "enchantment", "creature_removal")),
          DeckEntry(
            1,
            "Beast Within",
            Set("cmc_3", "green", "instant", "creature_removal")),
          DeckEntry(
            1,
            "Capsize",
            Set("cmc_3", "blue", "instant", "bounce", "buyback")),
          DeckEntry(
            1,
            "Krosan Grip",
            Set("cmc_3", "green", "instant", "artifact_removal",
              "enchantment_removal")),
          DeckEntry(
            1,
            "Acidic Slime",
            Set("cmc_5", "green", "creature", "artifact_removal",
              "enchantment_removal")),
          DeckEntry(
            1,
            "Swan Song",
            Set("cmc_1", "blue", "instant", "counter")),
          DeckEntry(
            1,
            "Counterspell",
            Set("cmc_2", "blue", "instant", "counter")),
          DeckEntry(
            1,
            "Deprive",
            Set("cmc_2", "blue", "instant", "counter")),
          DeckEntry(
            1,
            "Muddle the Mixture",
            Set("cmc_2", "blue", "instant", "counter")),
          DeckEntry(
            1,
            "Swiftfoot Boots",
            Set("cmc_2", "colorless", "artifact")),
          DeckEntry(
            1,
            "Lightning Greaves",
            Set("cmc_2", "colorless", "artifact")),
          DeckEntry(
            1,
            "Dissolve",
            Set("cmc_3", "blue", "instant", "counter")),
          DeckEntry(
            1,
            "Collective Voyage",
            Set("cmc_1", "green", "sorcery", "join_forces", "land_to_battlefield",
              "political")),
          DeckEntry(
            1,
            "Thaumatic Compass // Spires of Orazca",
            Set("cmc_2", "colorless", "artifact", "land_to_hand")),
          DeckEntry(
            1,
            "Elfhame Sanctuary",
            Set("cmc_2", "green", "enchantment")),
          DeckEntry(
            1,
            "Strionic Resonator",
            Set("cmc_2", "colorless", "artifact")),
          DeckEntry(
            1,
            "Rites of Flourishing",
            Set("cmc_3", "green", "enchantment", "draw", "ramp", "political",
              "extra_land_drop")),
          DeckEntry(
            1,
            "Courser of Kruphix",
            Set("cmc_3", "green", "creature", "ramp")),
          DeckEntry(
            1,
            "Mana Breach",
            Set("cmc_3", "blue", "enchantment")),
          DeckEntry(
            1,
            "Abundance",
            Set("cmc_4", "green", "enchantment")),
          DeckEntry(
            1,
            "Kefnet the Mindful",
            Set("cmc_3", "blue", "creature", "draw")),
          DeckEntry(
            1,
            "Sylvan Offering",
            Set("cmc_1", "green", "sorcery", "political", "token_generator")),
          DeckEntry(
            1,
            "Coiling Oracle",
            Set("cmc_2", "blue", "green", "creature")),
          DeckEntry(
            1,
            "Blackblade Reforged",
            Set("cmc_2", "colorless", "artifact")),
          DeckEntry(
            1,
            "Nissa, Steward of Elements",
            Set("cmc_2", "blue", "green", "planeswalker", "scry",
              "land_to_battlefield")),
          DeckEntry(
            1,
            "Retreat to Kazandu",
            Set("cmc_3", "green", "enchantment", "life_gain", "landfall")),
          DeckEntry(
            1,
            "Retreat to Coralhelm",
            Set("cmc_3", "blue", "enchantment", "scry", "landfall")),
          DeckEntry(
            1,
            "Seed the Land",
            Set("cmc_4", "green", "enchantment", "token_generator", "landfall")),
          DeckEntry(
            1,
            "Centaur Glade",
            Set("cmc_5", "green", "enchantment", "token_generator", "mana_sink")),
          DeckEntry(
            1,
            "Venser's Journal",
            Set("cmc_5", "colorless", "artifact", "life_gain")),
          DeckEntry(
            1,
            "Future Sight",
            Set("cmc_5", "blue", "enchantment")),
          DeckEntry(
            1,
            "Mystic Confluence",
            Set("cmc_5", "blue", "instant", "draw", "counter", "bounce")),
          DeckEntry(
            1,
            "Zendikar's Roil",
            Set("cmc_5", "green", "enchantment", "landfall", "token_generator")),
          DeckEntry(
            1,
            "Seasons Past",
            Set("cmc_6", "green", "sorcery", "graveyard_recursion")),
          DeckEntry(
            1,
            "Multani, Yavimaya's Avatar",
            Set("cmc_6", "green", "creature")),
          DeckEntry(
            1,
            "Rampaging Baloths",
            Set("cmc_6", "green", "creature", "token_generator")),
          DeckEntry(
            1,
            "Protean Hulk",
            Set("cmc_7", "green", "creature")),
          DeckEntry(
            1,
            "Howl of the Night Pack",
            Set("cmc_7", "green", "sorcery", "token_generator")),
          DeckEntry(
            1,
            "Praetor's Counsel",
            Set("cmc_8", "green", "sorcery", "graveyard_recursion")),
          DeckEntry(
            1,
            "Myriad Landscape",
            Set("land", "mana_src_green", "mana_src_blue", "land_to_battlefield")),
          DeckEntry(
            1,
            "Terramorphic Expanse",
            Set("land", "mana_src_green", "mana_src_blue")),
          DeckEntry(
            1,
            "Evolving Wilds",
            Set("land", "mana_src_green", "mana_src_blue")),
          DeckEntry(
            1,
            "Bant Panorama",
            Set("land", "mana_src_green", "mana_src_blue")),
          DeckEntry(
            1,
            "Esper Panorama",
            Set("land", "mana_src_blue")),
          DeckEntry(
            1,
            "Grixis Panorama",
            Set("land", "mana_src_blue")),
          DeckEntry(
            1,
            "Jund Panorama",
            Set("land", "mana_src_green")),
          DeckEntry(
            1,
            "Naya Panorama",
            Set("land", "mana_src_green")),
          DeckEntry(
            1,
            "Reliquary Tower",
            Set("land")),
          DeckEntry(
            1,
            "Rogue’s Passage",
            Set("land", "political")),
          DeckEntry(
            1,
            "Command Tower",
            Set("land", "mana_src_green", "mana_src_blue")),
          DeckEntry(
            1,
            "Simic Growth Chamber",
            Set("land", "mana_src_green", "mana_src_blue")),
          DeckEntry(
            1,
            "Blighted Woodland",
            Set("land", "mana_src_green", "mana_src_blue", "land_to_battlefield")),
          DeckEntry(
            1,
            "Yavimaya Coast",
            Set("land", "mana_src_green", "mana_src_blue")),
          DeckEntry(
            1,
            "Flooded Grove",
            Set("land", "mana_src_green", "mana_src_blue")),
          DeckEntry(
            1,
            "Hinterland Harbor",
            Set("land", "mana_src_green", "mana_src_blue")),
          DeckEntry(
            1,
            "Memorial to Genius",
            Set("land", "mana_src_blue")),
          DeckEntry(14,
            "Forest",
            Set("land", "mana_src_green")),
          DeckEntry(
            8,
            "Island",
            Set("land", "mana_src_blue"))
        )
        (DeckListPort.read(s"$RESOURCES_DIRECTORY/tatyova_valid.txt")
            shouldEqual Success(expected))
      }
    }

    "given an invalid deck list" should
    {
      "return a Failure" in
      {
        val result = DeckListPort.read(s"$RESOURCES_DIRECTORY/tatyova_invalid.txt")
        result.isFailure shouldBe true
        (result
            .asInstanceOf[Failure[Seq[DeckEntry]]]
            .exception
            .getMessage
            .contains("(line 12) ")
            shouldBe true)
      }
    }
  }
}
