package de.sciss.scenery.examples
package advanced

import java.nio.FloatBuffer

import de.sciss.scenery.Ops._
import graphics.scenery.SceneryElement

import scala.collection.mutable

object VertexUpdateExampleApp extends App {
  (new VertexUpdateExample).main()
}

/**
  * @author Ulrik Günther <hello@ulrik.is>
  * @author Hanns Holger Rutz <contact@sciss.de>
  */
class VertexUpdateExample extends SceneryScalaApp("VertexUpdateExample") {
  override def init(): Unit = {
    renderer = createRenderer(hub, applicationName, scene, 512, 512)
    hub.add(SceneryElement.RENDERER, renderer)

    val cam = DetachedHeadCamera()
    cam.position  = GLVector(0.0f, 0.0f, 5.0f)
    cam.perspectiveCamera(70.0f, 1.0f * windowWidth, 1.0f * windowHeight, 1.0f, 1000.0f)
    cam.active    = true
    scene.addChild(cam)

    val sphere            = Sphere(2.0f, 50)
    val sphereMat         = Material()
    sphereMat.ambient     = GLVector(1.0f, 1.0f, 1.0f)
    sphereMat.diffuse     = GLVector(1.0f, 1.0f, 1.0f)
    sphereMat.specular    = GLVector(1.0f, 1.0f, 1.0f)
    sphereMat.doubleSided = true
    sphere.position       = GLVector(0.0f, 0.0f, 0.0f)
    sphere.material       = sphereMat
    scene.addChild(sphere)

    for (i <- 0 to 2) {
      val light = PointLight()
      light.position = GLVector(2.0f * i, 2.0f * i, 2.0f * i)
      light.emissionColor = GLVector(1.0f, 1.0f, 1.0f)
      light.intensity = 150f * (i + 1)
      scene.addChild(light)
      light
    }

    var ticks = 0
    thread {
      import math._

      while (!scene.initialized) {
        Thread.sleep(200)
      }

      while (true) {
        sphere.rotation.rotateByAngleY(0.01f)
        sphere.needsUpdate = true
        ticks += 1

        val vBuf = mutable.Buffer.empty[Float]
        val nBuf = mutable.Buffer.empty[Float]

        val segments  = 50
        val radius    = 2.0f

        for (i <- 0 to segments) {
          val lat0: Float = (Pi * (-0.5f + (i - 1.0f) / segments)).toFloat
          val lat1: Float = (Pi * (-0.5f +  i         / segments)).toFloat

          val z0 = sin(lat0).toFloat
          val z1 = sin(lat1).toFloat

          val zr0 = cos(lat0).toFloat
          val zr1 = cos(lat1).toFloat

          for (j <- 1 to segments) {
            val lng = (2 * Pi * (j - 1) / segments).toFloat
            val x = cos(lng).toFloat
            val y = sin(lng).toFloat
            var r = radius

            if (j % 10 == 0) {
              r = radius + sin(ticks / 100.0).toFloat
            }
            vBuf += x * zr0 * r
            vBuf += y * zr0 * r
            vBuf += z0 * r

            vBuf += x * zr1 * r
            vBuf += y * zr1 * r
            vBuf += z1 * r

            nBuf += x
            nBuf += y
            nBuf += z0

            nBuf += x
            nBuf += y
            nBuf += z1
          }
        }

        sphere.vertices = FloatBuffer.wrap(vBuf.toArray)
        sphere.normals  = FloatBuffer.wrap(nBuf.toArray)
        sphere.recalculateNormals()

        sphere.dirty = true

        Thread.sleep(20)
      }
    }
  }
}