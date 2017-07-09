package de.sciss.scenery.ops

import cleargl.{GLVector => JGLVector}

final class GLVectorOps(private val peer: JGLVector) extends AnyVal {
  import peer._

  def + (that: JGLVector): JGLVector = plus (that)
  def - (that: JGLVector): JGLVector = minus(that)

  def * (that: Float): JGLVector = times(that)
}