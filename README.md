# MTG Deck Wizard
MTG Deck Wizard is a simple command line tool for extracting metrics from magic the gathering deck lists.

## Quick Start (TLDR version)
```
# In both cases, the installation directory is build/install/mtg-deck-wizard

# Unix
$ ./gradlew installShadowDist
$ build/install/mtg-deck-wizard-shadow/bin/mtg-deck-wizard <filename>

# Windows
$ ./gradlew.bat installShadowDist
$ build/install/mtg-deck-wizard-shadow/bin/mtg-deck-wizard.bat <filename>
```

## Decklists
Deck lists are specified in `.deck` files containing one card specification per line. The 
application requires the following information of each card:
- Number of copies in the deck
- Name 
- Mana cost
- Colors
- Card types
- CMC
- Tags (optional)

To create a deck list, a user can either specify all those for each card, or alternatively only the 
number of copies, name and tags (if any). In the latter case, the application will fetch the missing 
information from Scryfall.

### Syntax
The two variant syntaxes are detailed below. Comments and whitespaces are ignored when reading a 
deck list.
```
[<number of copies>] <card name> @<tag 1> @<tag 2> @<tag 3>
[<number of copies>] <card name> {manacost: "<mana cost>", colors: "<colors>", types: [<list of types>], cmc: <cmc>} @<tag>
```

The mana cost can be a combination of:
- `{X}`: Mana cost of X
- `{n}`: Fixed generic mana cost. N can be any positive integer, such as `{1}` and `{2}`
- `{W}`: Single white mana
- `{U}`: Single blue mana
- `{B}`: Single black mana
- `{R}`: Single red mana
- `{G}`: Single white mana
- `{W}`: Single white mana
- `{<color 1>/<color 2>}`: Hybrid mana
- `{<color>/P}`: Phyrexian mana

The colors are a combination of `W`, `U`, `B`, `R`, `G`.

The types are a list of elements from among `Creature`, `Instant`, `Sorcery`, `Artifact`, 
`Enchantment`, `Planeswalker` and `Land`. 

#### Examples
Some examples are detailed below. For entire example deck lists, check the `examples` folder.
```
// This is a comment

// Colorless card
[1] Horn of Greed @draw @symmetrical
// Same card with full syntax
[1] Horn of Greed {manacost: "{3}", colors: "", types: [Artifact], cmc: 3.00} @draw @symmetrical

// Non-colorless card
[1] Khalni Heart Expedition @ramp @land_to_battlefield
// Same wcard with full syntax
[1] Khalni Heart Expedition {manacost: "{1}{G}", colors: "G", types: [Enchantment], cmc: 2.00} @ramp @land_to_battlefield
```


## Running the application
```
$ build/install/mtg-deck-wizard-shadow/bin/mtg-deck-wizard examples/tatyova.deck 
 Searching Scryfall for missing information on card "Horn of Greed"...done
 Searching Scryfall for missing information on card "Intellectual Offering"...done
 Searching Scryfall for missing information on card "Minds Aglow"...done
 Searching Scryfall for missing information on card "Future Sight"...done
 Searching Scryfall for missing information on card "Rishkar's Expertise"...done
 Searching Scryfall for missing information on card "Skyshroud Ranger"...done
 Searching Scryfall for missing information on card "Sakura-Tribe Scout"...done
 Searching Scryfall for missing information on card "Budoka Gardener"...done
 Searching Scryfall for missing information on card "Llanowar Scout"...done
 Searching Scryfall for missing information on card "Walking Atlas"...done
 Searching Scryfall for missing information on card "Khalni Heart Expedition"...done
 Searching Scryfall for missing information on card "Grow from the Ashes"...done
 Searching Scryfall for missing information on card "Harrow"...done
 Searching Scryfall for missing information on card "Wayward Swordtooth"...done
 Searching Scryfall for missing information on card "Pir's Whim"...done
 Searching Scryfall for missing information on card "Silverglade Elemental"...done
 Searching Scryfall for missing information on card "Oblivion Sower"...done
 Searching Scryfall for missing information on card "Ulvenwald Hydra"...done
 Searching Scryfall for missing information on card "Curse of the Swine"...done
 Searching Scryfall for missing information on card "Ezuri's Predation"...done
 Searching Scryfall for missing information on card "Pongify"...done
 Searching Scryfall for missing information on card "Scavenging Ooze"...done
 Searching Scryfall for missing information on card "Lignify"...done
 Searching Scryfall for missing information on card "Broken Bond"...done
 Searching Scryfall for missing information on card "Beast Within"...done
 Searching Scryfall for missing information on card "Krosan Grip"...done
 Searching Scryfall for missing information on card "Acidic Slime"...done
 Searching Scryfall for missing information on card "Swan Song"...done
 Searching Scryfall for missing information on card "Counterspell"...done
 Searching Scryfall for missing information on card "Deprive"...done
 Searching Scryfall for missing information on card "Dissolve"...done
 Searching Scryfall for missing information on card "Insidious Will"...done
 Searching Scryfall for missing information on card "Swiftfoot Boots"...done
 Searching Scryfall for missing information on card "Lightning Greaves"...done
 Searching Scryfall for missing information on card "Neurok Stealthsuit"...done
 Searching Scryfall for missing information on card "Thaumatic Compass // Spires of Orazca"...done
 Searching Scryfall for missing information on card "Elfhame Sanctuary"...done
 Searching Scryfall for missing information on card "Strionic Resonator"...done
 Searching Scryfall for missing information on card "Rites of Flourishing"...done
 Searching Scryfall for missing information on card "Courser of Kruphix"...done
 Searching Scryfall for missing information on card "Mana Breach"...done
 Searching Scryfall for missing information on card "Abundance"...done
 Searching Scryfall for missing information on card "Sylvan Offering"...done
 Searching Scryfall for missing information on card "Heroic Intervention"...done
 Searching Scryfall for missing information on card "Coiling Oracle"...done
 Searching Scryfall for missing information on card "Blackblade Reforged"...done
 Searching Scryfall for missing information on card "Kefnet the Mindful"...done
 Searching Scryfall for missing information on card "Retreat to Kazandu"...done
 Searching Scryfall for missing information on card "Retreat to Coralhelm"...done
 Searching Scryfall for missing information on card "Seed the Land"...done
 Searching Scryfall for missing information on card "Centaur Glade"...done
 Searching Scryfall for missing information on card "Mystic Confluence"...done
 Searching Scryfall for missing information on card "Zendikar's Roil"...done
 Searching Scryfall for missing information on card "Seasons Past"...done
 Searching Scryfall for missing information on card "Multani, Yavimaya's Avatar"...done
 Searching Scryfall for missing information on card "Rampaging Baloths"...done
 Searching Scryfall for missing information on card "Spelltwine"...done
 Searching Scryfall for missing information on card "Protean Hulk"...done
 Searching Scryfall for missing information on card "Howl of the Night Pack"...done
 Searching Scryfall for missing information on card "Praetor's Counsel"...done
 Searching Scryfall for missing information on card "Myriad Landscape"...done
 Searching Scryfall for missing information on card "Terramorphic Expanse"...done
 Searching Scryfall for missing information on card "Evolving Wilds"...done
 Searching Scryfall for missing information on card "Bant Panorama"...done
 Searching Scryfall for missing information on card "Esper Panorama"...done
 Searching Scryfall for missing information on card "Grixis Panorama"...done
 Searching Scryfall for missing information on card "Jund Panorama"...done
 Searching Scryfall for missing information on card "Naya Panorama"...done
 Searching Scryfall for missing information on card "Reliquary Tower"...done
 Searching Scryfall for missing information on card "Rogue’s Passage"...done
 Searching Scryfall for missing information on card "Command Tower"...done
 Searching Scryfall for missing information on card "Simic Growth Chamber"...done
 Searching Scryfall for missing information on card "Yavimaya Coast"...done
 Searching Scryfall for missing information on card "Flooded Grove"...done
 Searching Scryfall for missing information on card "Hinterland Harbor"...done
 Searching Scryfall for missing information on card "Forest"...done
 Searching Scryfall for missing information on card "Island"...done
Save filled deck list? [y/n]
n
Total number of cards: 99
Tags:
  - artifact_removal =====> count = 03
  - bounce ===============> count = 01
  - commander_protection => count = 03
  - counter ==============> count = 06
  - creature_removal =====> count = 03
  - creature_wrath =======> count = 02
  - draw =================> count = 08
  - enchantment_removal ==> count = 03
  - extra_land_drop ======> count = 08
  - graveyard_hate =======> count = 01
  - graveyard_recursion ==> count = 02
  - land_to_battlefield ==> count = 08
  - land_to_hand =========> count = 01
  - landfall =============> count = 05
  - life_gain ============> count = 01
  - ramp =================> count = 15
  - scry =================> count = 01
  - symmetrical ==========> count = 06
  - token_generator ======> count = 07
Mana curve:
  - 0.0: 39
  - 1.0: 6
  - 2.0: 19
  - 3.0: 13
  - 4.0: 4
  - 5.0: 7
  - 6.0: 7
  - 7.0: 2
  - 8.0: 2
Card Types:
  - Enchantment: 13
  - Artifact: 8
  - Creature: 17
  - Instant: 12
  - Sorcery: 12
  - Land: 40
Colors:
  - Colorless: 48
  - Blue: 16
  - Green: 36
Mana Symbols:
  - Blue: 24
  - Green: 52
```

## Installation
### Distribution
To install the application, run:
```
# Unix
$ ./gradlew installShadowDist

# Windows
$ ./gradlew.bat installShadowDist
```
This should install a distribution on the `build/install/mtg-deck-wizard-shadow` directory. To run 
the installed distribution, first go to its directory, and then run:
```
# Unix
$ bin/mtg-deck-wizard <filename>

# Windows
$ bin/mtg-deck-wizard.bat <filename>
```
where `<filename>` is a path for a deck list, such as `../../../examples/tatyova.txt`

### Runnable JAR (Alternative Installation)
To build a runnable jar file, run:
```
# Unix
$ ./gradlew shadowJar

# Windows
$ ./gradlew.bat shadowJar
```

Alternatively, since the `lib/mtg-deck-wizard-<version>.jar` file is a runnable jar, you can also
run the application with the command:
```
$ java -jar lib/mtg-deck-wizard-<version>.jar <filename>
```

## Releasing a new version
To release a new version, run:
```
# Unix
$ ./gradlew release -Prelease.scope=<scope> -Prelease.stage=<stage>

# Windows
$ ./gradlew.bat release -Prelease.scope=<scope> -Prelease.stage=<stage>
```
where `<scope>` can be `patch`, `minor` or `major`, and `<stage>` can be `dev`, `milestone` or 
`final`.
