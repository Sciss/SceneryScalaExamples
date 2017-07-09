package de.sciss.scenery.ops

import java.nio.FloatBuffer

import graphics.scenery.{Sphere => KtSphere}

final class SphereOps(private val peer: KtSphere) extends AnyVal {
  import peer._

  def vertices: FloatBuffer = getVertices
  def vertices_=(value: FloatBuffer): Unit = setVertices(value)

  def normals: FloatBuffer = getNormals
  def normals_=(value: FloatBuffer): Unit = setNormals(value)
}