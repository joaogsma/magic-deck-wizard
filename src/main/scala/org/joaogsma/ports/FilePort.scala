package org.joaogsma.ports

import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter

import scala.io.BufferedSource
import scala.io.Source

trait FilePort {
  def usingFile[A](filename: String, f: BufferedSource => A): A = {
    val bs: BufferedSource = Source.fromFile(filename)
    try f(bs)
    finally bs.close()
  }

  def usingFile(filename: String, f: BufferedWriter => Unit): Unit = {
    val bw = new BufferedWriter(new FileWriter(new File(filename)))
    try f(bw)
    finally bw.close()
  }
}
