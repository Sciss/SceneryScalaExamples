package de.sciss.scenery

import cleargl.{GLMatrix, GLVector}
import de.sciss.scenery.ops.{CameraOps, FontBoardOps, GLVectorOps, LineOps, MaterialOps, MeshOps, NodeOps, PointLightOps, RendererOps, SceneryWindowOps}
import graphics.scenery.backends.{Renderer, SceneryWindow}
import graphics.scenery.repl.REPL
import graphics.scenery.{Box, Camera, DetachedHeadCamera, FontBoard, Line, Material, Mesh, Node, PointLight}

import scala.language.implicitConversions

object Ops {
  def Box       (sizes: GLVector      ): Box                = new Box       (sizes)
  def DetachedHeadCamera()             : DetachedHeadCamera = new DetachedHeadCamera
  def FontBoard ()                     : FontBoard          = new FontBoard
  def GLMatrix  ()                     : GLMatrix           = new GLMatrix
  def GLVector  (elems: Float*        ): GLVector           = new GLVector   (elems: _*)
  def Line      ()                     : Line               = new Line
  def Material  ()                     : Material           = new Material
  def Mesh      ()                     : Mesh               = new Mesh
  def PointLight()                     : PointLight         = new PointLight

  def REPL(accessibleObjects: AnyRef*): REPL = new REPL(accessibleObjects: _*)

  implicit def wrap(c: Camera       ): CameraOps            = new CameraOps       (c)
  implicit def wrap(b: FontBoard    ): FontBoardOps         = new FontBoardOps    (b)
  implicit def wrap(v: GLVector     ): GLVectorOps          = new GLVectorOps     (v)
  implicit def wrap(l: Line         ): LineOps              = new LineOps         (l)
  implicit def wrap(m: Material     ): MaterialOps          = new MaterialOps     (m)
  implicit def wrap(m: Mesh         ): MeshOps              = new MeshOps         (m)
  implicit def wrap(n: Node         ): NodeOps              = new NodeOps         (n)
  implicit def wrap(l: PointLight   ): PointLightOps        = new PointLightOps   (l)
  implicit def wrap(r: Renderer     ): RendererOps          = new RendererOps     (r)
  implicit def wrap(w: SceneryWindow): SceneryWindowOps     = new SceneryWindowOps(w)
}
