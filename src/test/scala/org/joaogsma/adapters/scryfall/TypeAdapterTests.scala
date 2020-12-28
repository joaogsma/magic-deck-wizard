package org.joaogsma.adapters.scryfall

import org.joaogsma.entities.models.Type
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class TypeAdapterTests extends AnyWordSpec with Matchers {
  "The parseToSequence function" when {
    "given a creature" should {
      "parse correctly" in {
        (TypeAdapter.parseToSet("Creature — Zombie Wizard")
            shouldEqual Set(Type.Creature))
        (TypeAdapter.parseToSet("Legendary Creature — Goblin Wizard")
            shouldEqual Set(Type.Creature))
      }
    }

    "given an enchantment" should {
      "parse correctly" in {
        (TypeAdapter.parseToSet("Enchantment")
            shouldEqual Set(Type.Enchantment))
        (TypeAdapter.parseToSet("Legendary Enchantment")
            shouldEqual Set(Type.Enchantment))
      }
    }

    "given an artifact" should {
      "parse correctly" in {
        (TypeAdapter.parseToSet("Artifact")
            shouldEqual Set(Type.Artifact))
        (TypeAdapter.parseToSet("Legendary Artifact")
            shouldEqual Set(Type.Artifact))
      }
    }

    "given an enchantment or artifact creature" should {
      "parse correctly" in {
        (TypeAdapter.parseToSet("Legendary Enchantment Creature — God")
            shouldEqual Set(Type.Enchantment, Type.Creature))
        (TypeAdapter.parseToSet("Artifact Creature — Golem")
            shouldEqual Set(Type.Artifact, Type.Creature))
      }
    }

    "given an enchantment artifact" should {
      "parse correctly" in {
        (TypeAdapter.parseToSet("Legendary Enchantment Artifact")
            shouldEqual Set(Type.Enchantment, Type.Artifact))
      }
    }

    "given a land" should {
      "parse correctly" in {
        (TypeAdapter.parseToSet("Basic Land - Island")
            shouldEqual Set(Type.Land))
        (TypeAdapter.parseToSet("Land")
            shouldEqual Set(Type.Land))
        (TypeAdapter.parseToSet("Legendary Land")
            shouldEqual Set(Type.Land))
      }
    }

    "given a planeswalker" should {
      "parse correctly" in {
        (TypeAdapter.parseToSet("Legendary Planeswalker — Liliana")
            shouldEqual Set(Type.Planeswalker))
      }
    }

    "given a instant" should {
      "parse correctly" in {
        (TypeAdapter.parseToSet("Instant")
            shouldEqual Set(Type.Instant))
      }
    }

    "given a sorcery" should {
      "parse correctly" in {
        (TypeAdapter.parseToSet("Sorcery")
            shouldEqual Set(Type.Sorcery))
        (TypeAdapter.parseToSet("Legendary Sorcery")
            shouldEqual Set(Type.Sorcery))
      }
    }
  }
}
