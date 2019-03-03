package org.joaogsma.ports.file

import java.io.BufferedWriter
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.FileWriter
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

import scala.io.BufferedSource
import scala.io.Source

trait FilePort {
  def usingFile[A](filename: String, f: BufferedSource => A): A = {
    val bs: BufferedSource = Source.fromFile(filename)
    try f(bs)
    finally bs.close()
  }

  def usingFile[A](filename: String, f: ObjectInputStream => A)(implicit d: DummyImplicit): A = {
    val ois: ObjectInputStream = new ObjectInputStream(new FileInputStream(filename))
    try f(ois)
    finally ois.close()
  }

  def usingFile(filename: String, f: BufferedWriter => Boolean): Boolean = {
    val bw = new BufferedWriter(new FileWriter(new File(filename)))
    try f(bw)
    finally bw.close()
  }

  def usingFile(
      filename: String,
      f: ObjectOutputStream => Boolean)(
      implicit d: DummyImplicit): Boolean = {
    val oos: ObjectOutputStream = new ObjectOutputStream(new FileOutputStream(filename))
    try f(oos)
    finally oos.close()
  }
}
