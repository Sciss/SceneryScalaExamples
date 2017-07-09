package de.sciss.scenery.examples
package basic

import de.sciss.scenery.Ops._
import graphics.scenery.SceneryElement
import graphics.scenery.utils.Numerics

object SponzaExampleApp extends App {
  (new SponzaExample).main()
}

/**
  * Demo loading the Sponza Model, demonstrating multiple moving lights
  * and transparent objects.
  *
  * @author Ulrik Günther <hello@ulrik.is>
  * @author Hanns Holger Rutz <contact@sciss.de>
  */
class SponzaExample extends SceneryScalaApp("SponzaExample", windowWidth = 1280, windowHeight = 720) {
//  private var hmd: TrackedStereoGlasses = null

  override def init(): Unit = {
    renderer = createRenderer(hub, applicationName, scene, windowWidth, windowHeight)
    hub.add(SceneryElement.RENDERER, renderer)

    val cam       = DetachedHeadCamera() // (hmd)
    cam.position  = GLVector(0.0f, 1.0f, 0.0f)
    cam.perspectiveCamera(50.0f, windowWidth, windowHeight)
    cam.active    = true
    scene.addChild(cam)

    val transBox          = Box(GLVector(2.0f, 2.0f, 2.0f))
    transBox.position     = GLVector(1.5f, 1.0f, -4.0f)
    val transMat          = Material()
    transMat.transparent  = true
    transMat.diffuse      = GLVector(0.0f, 0.0f, 1.0f)
    transBox.material     = transMat
    transBox.name         = "transparent box"
    scene.addChild(transBox)

    val lights = Seq.fill(16) {
      val it              = Box(GLVector(0.1f, 0.1f, 0.1f))
      it.position         = Numerics.randomVectorFromRange(3, -600.0f, 600.0f)
      val lightMat        = Material()
      lightMat.diffuse    = Numerics.randomVectorFromRange(3, 0.0f, 1.0f)
      it.material         = lightMat

      val light           = PointLight()
      light.emissionColor = it.material.diffuse
      light.intensity     = Numerics.randomFromRange(1.0f, 50f)
      light.linear        = 0.0f
      light.quadratic     = 2.2f

      it.addChild(light)

      scene.addChild(it)
      it
    }

    val mesh = Mesh()
    mesh.readFromOBJ(demoFilesPath + "/sponza.obj", true)
    mesh.position = GLVector(-200.0f, 5.0f, 200.0f)
    mesh.rotation.rotateByAngleY(math.Pi.toFloat / 2.0f)
    mesh.scale    = GLVector(0.01f, 0.01f, 0.01f)
    mesh.name     = "Sponza Mesh"

    scene.addChild(mesh)

    thread {
      var ticks = 0L
      while (true) {
        import math._
        lights.iterator.zipWithIndex.foreach { case (light, i) =>
          val phi = (Pi * 2.0f * ticks / 500.0f) % (Pi * 2.0f)

          light.position = GLVector(
            5.0f * cos(phi + (i * 0.5f)).toFloat,
            0.1f + i * 0.2f,
            -20.0f + 2.0f * i)

          light.children.get(0).position = light.position
        }

        ticks += 1
        Thread.sleep(15)
      }
    }
  }

//  override def inputSetup(): Unit = {
//    setupCameraModeSwitching(keybinding = "C")
//  }
}