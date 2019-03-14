package org.joaogsma.ports.file

import java.io.BufferedWriter
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.FileWriter
import java.io.InputStream
import java.io.OutputStream

import scala.io.BufferedSource
import scala.io.Source

trait FilePort {
  def usingFile[A](filename: String, f: BufferedSource => A): A = {
    val bs: BufferedSource = Source.fromFile(filename)
    try f(bs)
    finally bs.close()
  }

  def usingFile[A](filename: String, f: InputStream => A)(implicit d: DummyImplicit): A = {
    val fis: FileInputStream = new FileInputStream(filename)
    try f(fis)
    finally fis.close()
  }

  def usingFile(filename: String, f: BufferedWriter => Boolean): Boolean = {
    val bw = new BufferedWriter(new FileWriter(new File(filename)))
    try f(bw)
    finally bw.close()
  }

  def usingFile(
      filename: String,
      f: OutputStream => Boolean)(
      implicit d: DummyImplicit): Boolean = {
    val fos: FileOutputStream = new FileOutputStream(filename)
    try f(fos)
    finally fos.close()
  }
}
