package org.joaogsma.adapters

import org.joaogsma.ports.file.FilePort

package object scryfall {

  object FilePortImpl extends FilePort

  val RESOURCES_DIRECTORY = "src/test/resources/org/joaogsma/adapters/scryfall"
}
