package de.sciss.scenery.ops

import cleargl.{GLMatrix => JGLMatrix, GLVector => JGLVector}
import graphics.scenery.{Camera => KtCamera}

final class CameraOps(private val peer: KtCamera) extends AnyVal {
  import peer._

  def active: Boolean = getActive
  def active_=(value: Boolean): Unit = setActive(value)

  def forward: JGLVector = getForward
  def forward(value: JGLVector): Unit = setForward(value)

  def up: JGLVector = getUp
  def up(value: JGLVector): Unit = setUp(value)

  def view: JGLMatrix = getView
  def view_=(value: JGLMatrix): Unit = setView(value)

  def perspectiveCamera(fov: Float, width: Float, height: Float, nearPlaneLocation: Float = 0.1f,
                        farPlaneLocation: Float = 1000.0f): Unit =
    peer.perspectiveCamera(fov, width, height, nearPlaneLocation, farPlaneLocation)
}