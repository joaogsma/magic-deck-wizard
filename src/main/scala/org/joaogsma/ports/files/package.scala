package org.joaogsma.ports

import scala.io.BufferedSource

package object files
{
  def usingFile[A](bufferedSource: BufferedSource, f: BufferedSource => A): A =
  {
    try
      f(bufferedSource)
    finally
      bufferedSource.close()
  }
}
