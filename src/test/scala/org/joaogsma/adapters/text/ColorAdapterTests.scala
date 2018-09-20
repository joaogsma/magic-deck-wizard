package org.joaogsma.adapters.text

import org.joaogsma.models.Color
import org.scalatest.Matchers
import org.scalatest.WordSpec

import scala.util.Random
import scala.util.Success
import scala.util.Try

class ColorAdapterTests extends WordSpec with Matchers
{
  "The parseToSequence function" when
  {
    "applied to a sequence with any combination of color strings" should
    {
      "return a sequence with the correct color combination" in
      {
        val colors = List(Color.White, Color.Blue, Color.Black, Color.Red, Color.Green)

        val inputCombinations: Seq[String] = (1 to 5)
            .flatMap("WUBRG".combinations)
            .map('\"' + _ + '\"')
        val expectedCombinations: Seq[Try[Seq[Color]]] = (1 to 5)
            .flatMap(colors.combinations)
            .map(Success.apply)

        val resultCombinations = inputCombinations.map(ColorAdapter.parseToSequence)
        resultCombinations should contain theSameElementsInOrderAs expectedCombinations
      }
    }

    "applied to an empty string" should
    {
      "return a failure" in
      {
        ColorAdapter.parseToSequence("").isFailure shouldBe true
      }
    }

    "applied to an empty sequence" should
    {
      "return an empty sequence" in
      {
        ColorAdapter.parseToSequence("\"\"") shouldBe Success(Seq.empty)
      }
    }

    "applied to a sequence with repeated color strings" should
    {
      "return a failure" in
      {
        val colors = List(Color.White, Color.Blue, Color.Black, Color.Red, Color.Green)

        val inputCombinations: Seq[String] = (1 to 5)
            .flatMap("WUBRG".combinations)
            .map(combination => combination ++ randomSubset(combination))
            .map('\"' + _ + '\"')

        val resultCombinations = inputCombinations.map(ColorAdapter.parseToSequence)
        resultCombinations.forall(_.isFailure) shouldBe true
      }
    }
  }

  private def randomSubset[A](elements: Iterable[A]): Iterable[A] =
  {
    val subsetSize: Int = 1 + Random.nextInt(elements.size)
    Random.shuffle(elements).take(subsetSize)
  }
}
