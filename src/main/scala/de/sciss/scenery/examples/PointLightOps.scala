package de.sciss.scenery.examples

import cleargl.{GLVector => JGLVector}
import graphics.scenery.{PointLight => KtPointLight}

final class PointLightOps(private val peer: KtPointLight) extends AnyVal {
  import peer._
  
  def emissionColor: JGLVector = getEmissionColor
  def emissionColor_=(value: JGLVector): Unit = setEmissionColor(value)

  def intensity: Float = getIntensity
  def intensity_=(value: Float): Unit = setIntensity(value)

  def linear: Float = getLinear
  def linear_=(value: Float): Unit = setLinear(value)

  def quadratic: Float = getQuadratic
  def quadratic_=(value: Float): Unit = setQuadratic(value)
}