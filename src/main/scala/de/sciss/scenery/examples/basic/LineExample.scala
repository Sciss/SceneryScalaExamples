package de.sciss.scenery.examples
package basic

import de.sciss.scenery.Ops._
import graphics.scenery.SceneryElement

object LineExampleApp extends App {
  (new LineExample).main()
}

/**
  * Simple example to demonstrate the drawing of 3D lines.
  *
  * This example will draw a nicely illuminated bundle of lines using
  * the [Line] class. The line's width will oscillate while 3 lights
  * circle around the scene.
  *
  * @author Ulrik Günther <hello@ulrik.is>
  * @author Hanns Holger Rutz <contact@sciss.de>
  */
class LineExample extends SceneryScalaApp("LineExample") {
  protected var lineAnimating = true

  override def init(): Unit = {
    renderer = createRenderer(hub, applicationName, scene, windowWidth, windowHeight)
    hub.add(SceneryElement.RENDERER, renderer)

    val hull            = Box(GLVector(50.0f, 50.0f, 50.0f))
    val hullMat         = Material()
    hullMat.diffuse     = GLVector(0.2f, 0.2f, 0.2f)
    hullMat.doubleSided = true
    hull.material       = hullMat
    scene.addChild(hull)

    val lineMat         = Material()
    lineMat.ambient     = GLVector(1.0f, 0.0f, 0.0f)
    lineMat.diffuse     = GLVector(0.0f, 1.0f, 0.0f)
    lineMat.specular    = GLVector(1.0f, 1.0f, 1.0f)

    val line            = Line()
    line.addPoint(GLVector(-1.0f, -1.0f, -1.0f))
    line.addPoint(GLVector(2.0f, 0.0f, 2.0f))
    line.material       = lineMat
    line.position       = GLVector(0.0f, 0.0f, 0.0f)

    scene.addChild(line)

    val colors = Seq(
      GLVector(1.0f, 0.0f, 0.0f),
      GLVector(0.0f, 1.0f, 0.0f),
      GLVector(0.0f, 0.0f, 1.0f)
    )

    val lights = colors.map { color =>
      val l           = PointLight()
      l.intensity     = 200.0f
      l.emissionColor = color

      scene.addChild(l)
      l
    }

    val cam       = DetachedHeadCamera()
    cam.position  = GLVector(0.0f, 0.0f, 15.0f)
    cam.perspectiveCamera(50.0f, windowWidth, windowHeight)
    cam.active    = true

    scene.addChild(cam)

    thread {
      var t = 0
      while (true) {
        import math._
        if (lineAnimating) {
          line.addPoint(GLVector(
            10.0f * random().toFloat - 5.0f,
            10.0f * random().toFloat - 5.0f,
            10.0f * random().toFloat - 5.0f
          ))
          line.edgeWidth = 0.03f * math.sin(t * Pi / 50).toFloat + 0.004f
        }

        Thread.sleep(100)

        lights.iterator.zipWithIndex.foreach { case (pointLight, i) =>
          pointLight.position = GLVector(
              0.0f,
             15.0f * sin(2 * i * Pi / 3.0f + t * Pi / 50).toFloat,
            -15.0f * cos(2 * i * Pi / 3.0f + t * Pi / 50).toFloat
          )
        }

        t += 1
      }
    }
  }

//  override def inputSetup(): Unit = {
//    setupCameraModeSwitching(keybinding = "C")
//  }
}