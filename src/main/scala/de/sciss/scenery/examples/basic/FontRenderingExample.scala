package de.sciss.scenery.examples
package basic

import de.sciss.scenery.Ops._
import graphics.scenery.SceneryElement

object FontRenderingExampleApp extends App {
  (new FontRenderingExample).main()
}

class FontRenderingExample extends SceneryScalaApp("FontRenderingExample") {
  override def init(): Unit = {
    renderer = createRenderer(hub, applicationName, scene, windowWidth, windowHeight)
    hub.add(SceneryElement.RENDERER, renderer)

    (0 until 5).foreach { i =>
      val light = PointLight()
      light.position      = GLVector(5.0f + i * 2.0f, 5.0f, 5.0f)
      light.emissionColor = GLVector(1.0f, 1.0f, 1.0f)
      light.intensity     = 1000.0f
      light.quadratic     = 0.001f
      light.linear        = 0.0f
      scene.addChild(light)
    }

    val hullBox = Box(GLVector(900.0f, 900.0f, 900.0f))
    hullBox.position = GLVector(0.1f, 0.1f, 0.1f)
    val hullMat = Material()
    hullMat.ambient     = GLVector(1.0f, 1.0f, 1.0f)
    hullMat.diffuse     = GLVector(1.0f, 1.0f, 1.0f)
    hullMat.specular    = GLVector(1.0f, 1.0f, 1.0f)
    hullMat.doubleSided = true
    hullBox.material    = hullMat

    scene.addChild(hullBox)

    val cam = DetachedHeadCamera()
    cam.position = GLVector(5.0f, 0.0f, 15.0f)
    cam.perspectiveCamera(70.0f, windowWidth * 1.0f, windowHeight * 1.0f, 1.0f, 1000.0f)
    cam.active = true

    scene.addChild(cam)

    val board = FontBoard()
    board.text = ""
    board.position = GLVector(0.0f, 0.0f, 0.0f)

    scene.addChild(board)

    thread {
      while (!running) {
        Thread.sleep(200)
      }

      Seq(
        "hello world!",
        "this is scenery.",
        "demonstrating sdf font rendering."
      ).foreach { txt =>
        Thread.sleep(5000)
        board.text = txt
      }
    }
  }
}