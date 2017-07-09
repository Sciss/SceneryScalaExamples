package de.sciss.scenery.ops

import java.util
import java.util.concurrent.CopyOnWriteArrayList

import cleargl.{GLMatrix, GLVector => JGLVector}
import com.jogamp.opengl.math.Quaternion
import graphics.scenery.{NodeMetadata, Material => KtMaterial, Node => KtNode}
import kotlin.jvm.functions

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

  def initialized: Boolean = getInitialized

  def visible: Boolean = getVisible
  def visible_=(value: Boolean): Unit = setVisible(value)

  def instanceOf: KtNode = getInstanceOf
  def instanceOf_=(value: KtNode): Unit = setInstanceOf(value)

  def instanceMaster: Boolean = getInstanceMaster
  def instanceMaster_=(value: Boolean): Unit = setInstanceMaster(value)

  def instancedProperties: util.LinkedHashMap[String, functions.Function0[AnyRef]] = getInstancedProperties

  def parentOption: Option[KtNode] = Option(getParent)

  def children: CopyOnWriteArrayList[KtNode] = getChildren

  def metadata: util.HashMap[String, NodeMetadata] = getMetadata

  def model     : GLMatrix = getModel
  def modelView : GLMatrix = getModelView
  def mvp       : GLMatrix = getMvp
}