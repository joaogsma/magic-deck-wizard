package org.joaogsma.adapters.scryfall

import org.joaogsma.models.Type
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class TypeAdapterTests extends AnyWordSpec with Matchers {
  "The parseToSequence function" when {
    "given a creature" should {
      "parse correctly" in {
        (TypeAdapter.parseToSequence("Creature — Zombie Wizard")
            should contain theSameElementsInOrderAs Seq(Type.Creature))
        (TypeAdapter.parseToSequence("Legendary Creature — Goblin Wizard")
            should contain theSameElementsInOrderAs Seq(Type.Creature))
      }
    }

    "given an enchantment" should {
      "parse correctly" in {
        (TypeAdapter.parseToSequence("Enchantment")
            should contain theSameElementsInOrderAs Seq(Type.Enchantment))
        (TypeAdapter.parseToSequence("Legendary Enchantment")
            should contain theSameElementsInOrderAs Seq(Type.Enchantment))
      }
    }

    "given an artifact" should {
      "parse correctly" in {
        (TypeAdapter.parseToSequence("Artifact")
            should contain theSameElementsInOrderAs Seq(Type.Artifact))
        (TypeAdapter.parseToSequence("Legendary Artifact")
            should contain theSameElementsInOrderAs Seq(Type.Artifact))
      }
    }

    "given an enchantment or artifact creature" should {
      "parse correctly" in {
        (TypeAdapter.parseToSequence("Legendary Enchantment Creature — God")
            should contain theSameElementsInOrderAs Seq(Type.Enchantment, Type.Creature))
        (TypeAdapter.parseToSequence("Artifact Creature — Golem")
            should contain theSameElementsInOrderAs Seq(Type.Artifact, Type.Creature))
      }
    }

    "given an enchantment artifact" should {
      "parse correctly" in {
        (TypeAdapter.parseToSequence("Legendary Enchantment Artifact")
            should contain theSameElementsInOrderAs Seq(Type.Enchantment, Type.Artifact))
      }
    }

    "given a land" should {
      "parse correctly" in {
        (TypeAdapter.parseToSequence("Basic Land - Island")
            should contain theSameElementsInOrderAs Seq(Type.Land))
        (TypeAdapter.parseToSequence("Land")
            should contain theSameElementsInOrderAs Seq(Type.Land))
        (TypeAdapter.parseToSequence("Legendary Land")
            should contain theSameElementsInOrderAs Seq(Type.Land))
      }
    }

    "given a planeswalker" should {
      "parse correctly" in {
        (TypeAdapter.parseToSequence("Legendary Planeswalker — Liliana")
            should contain theSameElementsInOrderAs Seq(Type.Planeswalker))
      }
    }

    "given a instant" should {
      "parse correctly" in {
        (TypeAdapter.parseToSequence("Instant")
            should contain theSameElementsInOrderAs Seq(Type.Instant))
      }
    }

    "given a sorcery" should {
      "parse correctly" in {
        (TypeAdapter.parseToSequence("Sorcery")
            should contain theSameElementsInOrderAs Seq(Type.Sorcery))
        (TypeAdapter.parseToSequence("Legendary Sorcery")
            should contain theSameElementsInOrderAs Seq(Type.Sorcery))
      }
    }
  }
}
