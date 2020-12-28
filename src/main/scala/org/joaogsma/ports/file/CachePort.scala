package org.joaogsma.ports.file

import java.io.InputStream
import java.io.OutputStream

import org.joaogsma.adapters.proto.CacheAdapter
import org.joaogsma.entities.models.Card
import org.joaogsma.entities.models.proto.CacheProtos

import scala.util.Try

object CachePort extends FilePort {
  def read(filename: String): Try[Seq[(String, Card)]] = {
    val tryReadSeq: InputStream => Try[Seq[(String, Card)]] =
        ois => Try(CacheAdapter.fromProto(CacheProtos.Cache.parseFrom(ois))).flatten
    Try(usingFile(filename, tryReadSeq)).flatten
  }

  def write(cache: IterableOnce[(String, Card)], filename: String): Boolean = {
    val tryWrite: OutputStream => Boolean =
        oos => Try(CacheAdapter.toProto(cache).writeTo(oos)).isSuccess
    Try(usingFile(filename, tryWrite)).getOrElse(false)
  }
}
