package org.joaogsma.adapters.scryfall

import org.joaogsma.models.Color
import org.scalatest.Matchers
import org.scalatest.WordSpec

import scala.util.Random

class ColorAdapterTests extends WordSpec with Matchers
{
  "The parseSequence function" when
  {
    "applied to a sequence with any combination of color strings" should
    {
      "return a sequence with the correct color combination" in
      {
        val colors = List(Color.White, Color.Blue, Color.Black, Color.Red, Color.Green)

        val inputCombinations: Seq[Seq[String]] = (1 to 5)
            .flatMap("WUBRG".combinations)
            .map(_.map(_.toString))
        val expectedCombinations: Seq[Seq[Color]] = (1 to 5).flatMap(colors.combinations)

        val resultCombinations = inputCombinations.map(ColorAdapter.parseSequence)
        resultCombinations should contain theSameElementsAs expectedCombinations
      }
    }

    "applied to an empty sequence" should
    {
      "return an empty sequence" in
      {
        ColorAdapter.parseSequence(Seq.empty) shouldBe empty
      }
    }

    "applied to a sequence with repeated color strings" should
    {
      "remove the repeated colors" in
      {
        val colors = List(Color.White, Color.Blue, Color.Black, Color.Red, Color.Green)

        val inputCombinations: Seq[Seq[String]] = (1 to 5)
            .flatMap("WUBRG".combinations)
            .map(_.map(_.toString))
            .map(combination => combination ++ randomSubset(combination))
        val expectedCombinations: Seq[Seq[Color]] = (1 to 5).flatMap(colors.combinations)

        val resultCombinations = inputCombinations.map(ColorAdapter.parseSequence)
        resultCombinations should contain theSameElementsAs expectedCombinations
      }
    }
  }

  private def randomSubset[A](elements: Iterable[A]): Iterable[A] =
  {
    val subsetSize: Int = 1 + Random.nextInt(elements.size)
    Random.shuffle(elements).take(subsetSize)
  }
}
