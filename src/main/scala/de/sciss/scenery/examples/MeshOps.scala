package de.sciss.scenery.examples

import graphics.scenery.{Mesh => KtMesh}

final class MeshOps(private val peer: KtMesh) extends AnyVal {
  import peer._

  def name: String = getName
  def name_=(value: String): Unit = setName(value)
}