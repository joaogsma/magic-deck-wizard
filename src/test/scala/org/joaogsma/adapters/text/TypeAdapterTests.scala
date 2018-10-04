package org.joaogsma.adapters.text

import org.joaogsma.models.Type
import org.scalatest.Matchers
import org.scalatest.WordSpec

import scala.util.Success

class TypeAdapterTests extends WordSpec with Matchers
{
  "The parseToSequence function" when
  {
    "given a creature" should
    {
      "parse correctly" in
      {
        TypeAdapter.parseToSequence("[Creature]") shouldEqual Success(Seq(Type.Creature))
      }
    }

    "given an enchantment" should
    {
      "parse correctly" in
      {
        TypeAdapter.parseToSequence("[Enchantment]") shouldEqual Success(Seq(Type.Enchantment))
      }
    }

    "given an artifact" should
     {
       "parse correctly" in
       {
         TypeAdapter.parseToSequence("[Artifact]") shouldEqual Success(Seq(Type.Artifact))
       }
     }

    "given an enchantment or artifact creature" should
    {
      "parse correctly" in
      {
        (TypeAdapter.parseToSequence("[Enchantment, Creature]")
            shouldEqual Success(Seq(Type.Enchantment, Type.Creature)))
        (TypeAdapter.parseToSequence("[Artifact, Creature]")
            shouldEqual Success(Seq(Type.Artifact, Type.Creature)))
      }
    }

    "given an enchantment artifact" should
    {
      "parse correctly" in
      {
        (TypeAdapter.parseToSequence("[Enchantment, Artifact]")
            shouldEqual Success(Seq(Type.Enchantment, Type.Artifact)))
      }
    }

    "given a land" should
    {
      "parse correctly" in
      {
        TypeAdapter.parseToSequence("[Land]") shouldEqual Success(Seq(Type.Land))
      }
    }

    "given a planeswalker" should
    {
      "parse correctly" in
      {
        (TypeAdapter.parseToSequence("[Planeswalker]")
            shouldEqual Success(Seq(Type.Planeswalker)))
      }
    }

    "given a instant" should
    {
      "parse correctly" in
      {
        TypeAdapter.parseToSequence("[Instant]") shouldEqual Success(Seq(Type.Instant))
      }
    }

    "given a sorcery" should
    {
      "parse correctly" in
      {
        TypeAdapter.parseToSequence("[Sorcery]") shouldEqual Success(Seq(Type.Sorcery))
      }
    }

    "given an invalid word" should
    {
      "return a Failure" in
      {
        TypeAdapter.parseToSequence("[Creature, foo]").isFailure shouldBe true
        TypeAdapter.parseToSequence("[bar, Enchantment]").isFailure shouldBe true
        TypeAdapter.parseToSequence("[Artifact, foo]").isFailure shouldBe true
        TypeAdapter.parseToSequence("[bar, Land]").isFailure shouldBe true
        TypeAdapter.parseToSequence("[Planeswalker, foo]").isFailure shouldBe true
        TypeAdapter.parseToSequence("[bar, Instant]").isFailure shouldBe true
        TypeAdapter.parseToSequence("[Sorcery, foo]").isFailure shouldBe true
      }
    }

    "given lower case words" should
    {
      "parse correctly" in
      {
        TypeAdapter.parseToSequence("[creature]") shouldEqual Success(Seq(Type.Creature))

        (TypeAdapter.parseToSequence("[enchantment]")
            shouldEqual Success(Seq(Type.Enchantment)))

        TypeAdapter.parseToSequence("[artifact]") shouldEqual Success(Seq(Type.Artifact))

        TypeAdapter.parseToSequence("[land]") shouldEqual Success(Seq(Type.Land))

        (TypeAdapter.parseToSequence("[planeswalker]")
            shouldEqual Success(Seq(Type.Planeswalker)))

        TypeAdapter.parseToSequence("[instant]") shouldEqual Success(Seq(Type.Instant))

        TypeAdapter.parseToSequence("[sorcery]") shouldEqual Success(Seq(Type.Sorcery))

        (TypeAdapter.parseToSequence("[enchantment, creature]")
            shouldEqual Success(Seq(Type.Enchantment, Type.Creature)))

        (TypeAdapter.parseToSequence("[artifact, creature]")
            shouldEqual Success(Seq(Type.Artifact, Type.Creature)))

        (TypeAdapter.parseToSequence("[enchantment, artifact]")
            shouldEqual Success(Seq(Type.Enchantment, Type.Artifact)))
      }
    }

    "given a sequence with arbitrary spacing" should
    {
      "parse correctly" in
      {
        (TypeAdapter.parseToSequence("[Enchantment,               Creature]")
            shouldEqual Success(Seq(Type.Enchantment, Type.Creature)))
        (TypeAdapter.parseToSequence("[       Artifact,     Creature       ]")
            shouldEqual Success(Seq(Type.Artifact, Type.Creature)))
      }
    }
  }

  "The toString function" when
  {
    "given only one type" should
    {
      "return the correct string" in
      {
        TypeAdapter.toString(List(Type.Enchantment)) shouldEqual "[Enchantment]"
        TypeAdapter.toString(List(Type.Artifact)) shouldEqual "[Artifact]"
        TypeAdapter.toString(List(Type.Creature)) shouldEqual "[Creature]"
        TypeAdapter.toString(List(Type.Instant)) shouldEqual "[Instant]"
        TypeAdapter.toString(List(Type.Sorcery)) shouldEqual "[Sorcery]"
        TypeAdapter.toString(List(Type.Planeswalker)) shouldEqual "[Planeswalker]"
        TypeAdapter.toString(List(Type.Land)) shouldEqual "[Land]"
      }
    }

    "given two types" should
    {
      "return the correct string" in
      {
        (TypeAdapter.toString(List(Type.Enchantment, Type.Creature))
            shouldEqual "[Enchantment, Creature]")
        (TypeAdapter.toString(List(Type.Artifact, Type.Creature))
            shouldEqual "[Artifact, Creature]")
        (TypeAdapter.toString(List(Type.Enchantment, Type.Artifact))
            shouldEqual "[Enchantment, Artifact]")
      }
    }
  }
}
