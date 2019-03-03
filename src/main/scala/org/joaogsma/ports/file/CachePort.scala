package org.joaogsma.ports.file

import java.io.ObjectInputStream
import java.io.ObjectOutputStream

import org.joaogsma.models.Card

import scala.collection.immutable.HashMap
import scala.util.Try

object CachePort extends FilePort {
  def read(filename: String): Try[Map[String, Card]] = {
    val tryReadMap: ObjectInputStream => Try[Map[String, Card]] =
        ois => Try(ois.readObject().asInstanceOf[Map[String, Card]])
    Try(usingFile(filename, tryReadMap)).flatten
  }

  def write(cache: Map[String, Card], filename: String): Boolean = {
    val hashMapCache: HashMap[String, Card] = cache match {
      case hashMap: HashMap[String, Card] => hashMap
      case _ => (HashMap.empty ++ cache).asInstanceOf[HashMap[String, Card]]
    }

    val tryWrite = (oos: ObjectOutputStream) => Try(oos.writeObject(hashMapCache)).isSuccess
    Try(usingFile(filename, tryWrite)).getOrElse(false)
  }
}
