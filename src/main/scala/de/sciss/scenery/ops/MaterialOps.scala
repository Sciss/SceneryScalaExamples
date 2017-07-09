package de.sciss.scenery.ops

import java.util.concurrent.ConcurrentHashMap

import cleargl.{GLVector => JGLVector}
import graphics.scenery.{Material => KtMaterial}

final class MaterialOps(private val peer: KtMaterial) extends AnyVal {
  import peer._

  def ambient: JGLVector = getAmbient
  def ambient_=(value: JGLVector): Unit = setAmbient(value)

  def diffuse: JGLVector = getDiffuse
  def diffuse_=(value: JGLVector): Unit = setDiffuse(value)

  def specular: JGLVector = getSpecular
  def specular_=(value: JGLVector): Unit = setSpecular(value)

  def textures: ConcurrentHashMap[String, String] = getTextures

  def doubleSided: Boolean = getDoubleSided
  def doubleSided_=(value: Boolean): Unit = setDoubleSided(value)
}