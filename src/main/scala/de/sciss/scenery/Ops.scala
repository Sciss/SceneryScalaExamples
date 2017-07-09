package de.sciss.scenery

import cleargl.{GLMatrix, GLVector => JGLVector}
import com.jogamp.opengl.math.Quaternion
import de.sciss.scenery.ops.{CameraOps, FontBoardOps, GLVectorOps, LineOps, MaterialOps, MeshOps, NodeOps, PointLightOps, RendererOps, SceneryWindowOps}
import graphics.scenery.backends.{Renderer, SceneryWindow}
import graphics.scenery.repl.REPL
import graphics.scenery.{Box, Camera, DetachedHeadCamera, FontBoard, Line, Material, Mesh, Node => KtNode, PointLight}

import scala.language.implicitConversions

object Ops {
  def Box       (sizes: JGLVector     ): Box                = new Box       (sizes)
  def DetachedHeadCamera()             : DetachedHeadCamera = new DetachedHeadCamera
  def FontBoard ()                     : FontBoard          = new FontBoard
  def GLMatrix  ()                     : GLMatrix           = new GLMatrix
  def Line      ()                     : Line               = new Line
  def Material  ()                     : Material           = new Material
  def Mesh      ()                     : Mesh               = new Mesh
  def Node      (name: String)         : Node               = new Node(name)
  def PointLight()                     : PointLight         = new PointLight
  def Quaternion()                     : Quaternion         = new Quaternion
//  def Volume    ()                     : Volume             = new Volume

  type Node = KtNode

  object GLVector {
    def apply(elems: Float*): JGLVector = new JGLVector(elems: _*)

    def zeroes(dimension: Int): JGLVector = JGLVector.getNullVector(dimension)
    def ones  (dimension: Int): JGLVector = JGLVector.getOneVector (dimension)
  }

  def REPL(accessibleObjects: AnyRef*): REPL = new REPL(accessibleObjects: _*)

  implicit def wrap(c: Camera       ): CameraOps            = new CameraOps       (c)
  implicit def wrap(b: FontBoard    ): FontBoardOps         = new FontBoardOps    (b)
  implicit def wrap(v: JGLVector    ): GLVectorOps          = new GLVectorOps     (v)
  implicit def wrap(l: Line         ): LineOps              = new LineOps         (l)
  implicit def wrap(m: Material     ): MaterialOps          = new MaterialOps     (m)
  implicit def wrap(m: Mesh         ): MeshOps              = new MeshOps         (m)
  implicit def wrap(n: Node         ): NodeOps              = new NodeOps         (n)
  implicit def wrap(l: PointLight   ): PointLightOps        = new PointLightOps   (l)
  implicit def wrap(r: Renderer     ): RendererOps          = new RendererOps     (r)
  implicit def wrap(w: SceneryWindow): SceneryWindowOps     = new SceneryWindowOps(w)
}
