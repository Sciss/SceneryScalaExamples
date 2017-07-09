package de.sciss.scenery.examples
package basic

import de.sciss.scenery.Ops._
import graphics.scenery.SceneryElement

object TexturedCubeExampleApp extends App {
  (new TexturedCubeExample).main()
}

/**
  * @author Ulrik Günther <hello@ulrik.is>
  * @author Hanns Holger Rutz <contact@sciss.de>
  */
class TexturedCubeExample extends SceneryScalaApp("scenery - TexturedCubeExample", 800, 600) {
  override def init(): Unit = {
    renderer = createRenderer(hub, applicationName, scene, 512, 512)
    hub.add(SceneryElement.RENDERER, renderer)

    val boxMat = Material()
    boxMat.ambient  = GLVector(1.0f, 0.0f, 0.0f)
    boxMat.diffuse  = GLVector(0.0f, 1.0f, 0.0f)
    boxMat.specular = GLVector(1.0f, 1.0f, 1.0f)
    boxMat.textures.put("diffuse", classOf[TexturedCubeExample].getResource("textures/helix.png").getFile)

    val box = Box(GLVector(1.0f, 1.0f, 1.0f) /*, false */)
    box.material = boxMat
    box.position = GLVector(0.0f, 0.0f, 0.0f)

    scene.addChild(box)

    for (i <- 0 until 2) {
      val light = PointLight()
      light.position      = GLVector(2.0f * i, 2.0f * i, 2.0f * i)
      light.emissionColor = GLVector(1.0f, 0.0f, 1.0f)
      light.intensity     = 100.2f * (i + 1)
      light.linear        = 0.0f
      light.quadratic     = 0.5f
      scene.addChild(light)
    }

    val cam = DetachedHeadCamera()
    cam.position = GLVector(0.0f, 0.0f, 5.0f)
    cam.perspectiveCamera(50.0f, renderer.window.width, renderer.window.height, 0.1f, 1000.0f)
    cam.active = true
    scene.addChild(cam)

    thread {
      while (true) {
        box.rotation.rotateByAngleY(0.01f)
        box.needsUpdate = true
        Thread.sleep(20)
      }
    }

    repl = REPL(scene, renderer)
    repl.start()
    repl.showConsoleWindow()
  }
}