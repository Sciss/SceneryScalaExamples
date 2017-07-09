package de.sciss.scenery.examples
package basic

import graphics.scenery.SceneryElement
import graphics.scenery.utils.Numerics

object ArcballExampleApp extends App {
  (new ArcballExample).main()
}
/**
  * This example demonstrates how to use the TargetArcBallBehaviour and how
  * to modify the default behaviour/key map of scenery, and also manually
  * trigger behaviours. See also [SceneryDefaultApplication.setupCameraModeSwitching].
  *
  * @author Ulrik Günther <hello@ulrik.is>
  * @author Hanns Holger Rutz <contact@sciss.de>
  */
class ArcballExample extends SceneryScalaApp("ArcballExample") {
  override def init(): Unit = {
    renderer = createRenderer(hub, applicationName, scene, 1024, 1024)
    hub.add(SceneryElement.RENDERER, renderer)

    val cam = DetachedHeadCamera()
    cam.perspectiveCamera(70.0f, windowWidth, windowHeight)

    cam.position  = GLVector(0.0f, 0.0f, 2.5f)
    cam.view      = GLMatrix().setCamera(cam.position, cam.position + cam.forward, cam.up)
    cam.active    = true

    scene.addChild(cam)

    val box = Box(GLVector(1.0f, 1.0f, 1.0f))
    box.position = GLVector(0.0f, 0.0f, 0.0f)

    // the Kotlin code doesn't create material,
    // but the box returns `null`, I don't know
    // what it does - perhaps it silently swallows
    // calls to `null`?
    val boxMat = Material()
    boxMat.ambient  = GLVector(1.0f, 0.0f, 0.0f)
    boxMat.diffuse  = GLVector(0.0f, 1.0f, 0.0f)
    boxMat.specular = GLVector(1.0f, 1.0f, 1.0f)
    boxMat.textures.put("diffuse", classOf[TexturedCubeExample].getResource("textures/helix.png").getFile)
    box.material = boxMat

    scene.addChild(box)

    val lights = Seq.fill(3) {
      val light = PointLight()
      light.position      = Numerics.randomVectorFromRange(3, -5.0f, 5.0f)
      light.emissionColor = GLVector(1.0f, 0.0f, 1.0f)
      light.intensity     = Numerics.randomFromRange(50.0f, 150.0f)
      light
    }

    lights.foreach(scene.addChild)

    thread {
      while (true) {
        box.rotation.rotateByAngleY(0.01f)
        box.needsUpdate = true

        Thread.sleep(20)
      }
    }
  }

  override def inputSetup(): Unit = {
    // note: it seems `setupCameraModeSwitching` is not available in scenery 0.1.0

//    setupCameraModeSwitching(keybinding = "C")
//
//    // switch to arcball mode by manually triggering the behaviour
//    inputHandlerOption.foreach(h => (h.getBehaviour("toggle_control_mode").asInstanceOf[ClickBehaviour]).click(0, 0))
  }
}