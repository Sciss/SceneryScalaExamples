package de.sciss.scenery.examples

import cleargl.{GLVector => JGLVector}
import com.jogamp.opengl.math.Quaternion
import graphics.scenery.{Material => KtMaterial, Node => KtNode}

final class NodeOps(private val peer: KtNode) extends AnyVal {
  import peer._

  def material: KtMaterial = getMaterial
  def material_=(value: KtMaterial): Unit = setMaterial(value)

  def position: JGLVector = getPosition
  def position_=(value: JGLVector): Unit = setPosition(value)

  def needsUpdate: Boolean = getNeedsUpdate
  def needsUpdate_=(value: Boolean): Unit = setNeedsUpdate(value)

  def rotation: Quaternion = getRotation
  def rotation_=(value: Quaternion): Unit = setRotation(value)
}