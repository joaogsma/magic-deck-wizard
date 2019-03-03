package org.joaogsma.controllers

import org.joaogsma.models.Card
import org.joaogsma.ports.file.CachePort

import scala.collection.mutable
import scala.collection.immutable

object CacheController {
  private lazy val cache: mutable.Map[String, Card] = loadCache()
  private val cacheFilename: String = ".cache"

  def getOrReadCache(): mutable.Map[String, Card] = cache

  def writeCache(): Boolean = CachePort.write(immutable.HashMap.empty ++ cache, cacheFilename)

  private def loadCache(): mutable.Map[String, Card] = {
    CachePort.read(cacheFilename)
        .map(mutable.HashMap.empty ++ _)
        .getOrElse(mutable.HashMap.empty[String, Card])
  }
}
