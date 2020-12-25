package org.joaogsma.ports.ui

import scala.io.StdIn

object ConsolePort {
  def queryUser(
      message: String,
      validAnswers: Set[String],
      maxAttempts: Int = 5): Option[String] = {
    println(message)

    var attempts = 0
    var answer: String = StdIn.readLine()
    while (!validAnswers.contains(answer) && attempts < maxAttempts) {
      println(s"Invalid answer: $answer")
      answer = StdIn.readLine()
      attempts += 1
    }

    attempts match {
      case `maxAttempts` => Option.empty
      case _ => Option(answer)
    }
  }
}
