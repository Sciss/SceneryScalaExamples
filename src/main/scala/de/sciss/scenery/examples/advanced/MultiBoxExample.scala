package de.sciss.scenery.examples
package advanced

import de.sciss.scenery.Ops._
import graphics.scenery.SceneryElement
import graphics.scenery.utils.Numerics

object MultiBoxExampleApp extends App {
  (new MultiBoxExample).main()
}

/**
  * Demo animating multiple boxes without instancing.
  *
  * @author Ulrik Günther <hello@ulrik.is>
  * @author Hanns Holger Rutz <contact@sciss.de>
  */
class MultiBoxExample extends SceneryScalaApp("MultiBoxExample") {
  override def init(): Unit = {
    renderer = createRenderer(hub, applicationName, scene, windowWidth, windowHeight)
    hub.add(SceneryElement.RENDERER, renderer)

    val cam = DetachedHeadCamera()
    cam.position = GLVector(10.0f, 10.0f, 10.0f)
    cam.perspectiveCamera(60.0f, 1.0f * windowWidth, 1.0f * windowHeight, 1.0f, 1000.0f)
    cam.active = true
    scene.addChild(cam)

    val boundaryWidth  = 10.0
    val boundaryHeight = 10.0

    import math._

    val m = Mesh()
    for (s <- 0 until 1000) {
      val box = Box(GLVector(0.2f, 0.2f, 0.2f))
      val k: Double =  s %  boundaryWidth
      val j: Double = (s /  boundaryWidth) % boundaryHeight
      val i: Double =  s / (boundaryWidth  * boundaryHeight)

      box.position = GLVector(floor(i).toFloat * 3.0f, floor(j).toFloat * 3.0f, floor(k).toFloat * 3.0f)

      m.addChild(box)
    }

    scene.addChild(m)

    val lights = Seq.fill(10) {
      val it = PointLight()
      it.position       = Numerics.randomVectorFromRange(3, -600.0f, 600.0f)
      it.emissionColor  = GLVector(1.0f, 1.0f, 1.0f)
      it.intensity      = Numerics.randomFromRange(0.01f, 1000f)
      it.linear         = 0.1f
      it.quadratic      = 0.1f

      scene.addChild(it)
      it
    }

    val hullBox = Box(GLVector(100.0f, 100.0f, 100.0f) /* , insideNormals = true */)
    hullBox.position = GLVector(0.0f, 0.0f, 0.0f)
    val hullMat = Material()
    hullMat.ambient       = GLVector(0.6f, 0.6f, 0.6f)
    hullMat.diffuse       = GLVector(0.4f, 0.4f, 0.4f)
    hullMat.specular      = GLVector(0.0f, 0.0f, 0.0f)
    hullMat.doubleSided   = true

    scene.addChild(hullBox)

    var ticks: Int = 0

    thread {
      val step = 0.02f

      while (true) {
        lights.iterator.zipWithIndex.foreach { case (light, i) =>
          val phi = Pi * 2.0f * ticks / 1500.0f
          val expi = exp(i)

          light.position = GLVector(
            (expi * 10 * sin(phi) + expi).toFloat,
            step * ticks,
            (expi * 10 * cos(phi) + expi).toFloat
          )
        }

        ticks += 1

        m.rotation.rotateByEuler(0.001f, 0.001f, 0.0f)
        m.needsUpdate = true

        Thread.sleep(10)
      }
    }
  }
}