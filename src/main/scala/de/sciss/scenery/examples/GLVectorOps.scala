package de.sciss.scenery.examples

import cleargl.{GLVector => JGLVector}

final class GLVectorOps(private val peer: JGLVector) extends AnyVal {
  import peer._

  def + (that: JGLVector): JGLVector = plus(that)
}