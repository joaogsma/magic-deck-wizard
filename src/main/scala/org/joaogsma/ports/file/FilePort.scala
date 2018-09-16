package org.joaogsma.ports.file

import scala.io.BufferedSource
import scala.io.Source

trait FilePort
{
  def usingFile[A](filename: String, f: BufferedSource => A): A =
  {
    val bs: BufferedSource = Source.fromFile(filename)
    try
      f(bs)
    finally
      bs.close()
  }
}
