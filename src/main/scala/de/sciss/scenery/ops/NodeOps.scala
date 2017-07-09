package de.sciss.scenery.ops

import java.util.concurrent.CopyOnWriteArrayList

import cleargl.{GLVector => JGLVector}
import com.jogamp.opengl.math.Quaternion
import graphics.scenery.{Material => KtMaterial, Node => KtNode}

final class NodeOps(private val peer: KtNode) extends AnyVal {
  import peer._

  def material: KtMaterial = getMaterial
  def material_=(value: KtMaterial): Unit = setMaterial(value)

  def materialOption: Option[KtMaterial] = Option(getMaterial)

  def position: JGLVector = getPosition
  def position_=(value: JGLVector): Unit = setPosition(value)

  def scale: JGLVector  = getScale
  def scale_=(value: JGLVector): Unit = setScale(value)

  def needsUpdate: Boolean = getNeedsUpdate
  def needsUpdate_=(value: Boolean): Unit = setNeedsUpdate(value)

  def rotation: Quaternion = getRotation
  def rotation_=(value: Quaternion): Unit = setRotation(value)

  def parentOption: Option[KtNode] = Option(getParent)

  def children: CopyOnWriteArrayList[KtNode] = getChildren
}