package de.sciss.scenery

import graphics.scenery.{Mesh => KtMesh, Box => KtBox, Camera => KtCamera, DetachedHeadCamera => KtDetachedHeadCamera, Material => KtMaterial, Node => KtNode, PointLight => KtPointLight}
import graphics.scenery.backends.{Renderer => KtRenderer}
import cleargl.{GLMatrix => JGLMatrix, GLVector => JGLVector}
import graphics.scenery.backends.{SceneryWindow => KtSceneryWindow}
import graphics.scenery.repl.{REPL => KtREPL}

import scala.language.implicitConversions

package object examples {
  def Material  ()                     : KtMaterial           = new KtMaterial
  def GLVector  (elems: Float*        ): JGLVector            = new JGLVector   (elems: _*)
  def GLMatrix  ()                     : JGLMatrix            = new JGLMatrix
  def Box       (sizes: JGLVector     ): KtBox                = new KtBox       (sizes)
  def PointLight()                     : KtPointLight         = new KtPointLight
  def DetachedHeadCamera()             : KtDetachedHeadCamera = new KtDetachedHeadCamera
  def Mesh      ()                     : KtMesh               = new KtMesh

  def REPL(accessibleObjects: AnyRef*): KtREPL = new KtREPL(accessibleObjects: _*)

  implicit def wrap(m: KtMaterial     ): MaterialOps          = new MaterialOps     (m)
  implicit def wrap(n: KtNode         ): NodeOps              = new NodeOps         (n)
  implicit def wrap(r: KtRenderer     ): RendererOps          = new RendererOps     (r)
  implicit def wrap(l: KtPointLight   ): PointLightOps        = new PointLightOps   (l)
  implicit def wrap(w: KtSceneryWindow): SceneryWindowOps     = new SceneryWindowOps(w)
  implicit def wrap(c: KtCamera       ): CameraOps            = new CameraOps       (c)
  implicit def wrap(v: JGLVector      ): GLVectorOps          = new GLVectorOps     (v)
  implicit def wrap(m: KtMesh         ): MeshOps              = new MeshOps         (m)
}