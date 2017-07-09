package de.sciss.scenery.examples
package basic

import graphics.scenery.SceneryElement
import graphics.scenery.utils.Numerics
import scala.collection.JavaConverters._

object BoxedProteinExampleApp extends App {
  (new BoxedProteinExample).main()
}

/**
  * Example to demonstrate model loading with multiple lights,
  * combined with arcball controls.
  *
  * @author Ulrik Günther <hello@ulrik.is>
  * @author Hanns Holger Rutz <contact@sciss.de>
  */
class BoxedProteinExample extends SceneryScalaApp("BoxedProteinExample", windowWidth = 1280, windowHeight = 720) {
  override def init(): Unit = {
    val lightCount = 8

    renderer = createRenderer(hub, applicationName, scene, windowWidth, windowHeight)
    hub.add(SceneryElement.RENDERER, renderer)

    val cam = DetachedHeadCamera()
    cam.position = GLVector(0.0f, 0.0f, 150.0f)
    cam.perspectiveCamera(50.0f, windowWidth, windowHeight)
    cam.active = true

    scene.addChild(cam)

    // I think this is a mistake by the original author -
    // ranges are inclusive in Kotlin, but pretty certainly we only want `lightCount` instances
    val boxes = Seq.fill(lightCount) {
      Box(GLVector(0.5f, 0.5f, 0.5f))
    }

    val lights = Seq.fill(lightCount) {
      PointLight()
    }

    (boxes, lights).zipped.foreach { case (box, light) =>
      box.material = Material()
      box.addChild(light)
      scene.addChild(box)
    }

    lights.foreach { it =>
      it.position       = Numerics.randomVectorFromRange(3, -600.0f, 600.0f)
      it.emissionColor  = Numerics.randomVectorFromRange(3, 0.0f, 1.0f)

      for {
        p <- it.parentOption
        m <- p.materialOption
      } {
        m.diffuse = it.emissionColor
      }

      it.intensity = Numerics.randomFromRange(0.01f, 100f)
      it.quadratic = 0.0f

      scene.addChild(it)
    }

    val hullBox = Box(GLVector(300.0f, 300.0f, 300.0f) /*, insideNormals = true */)

    hullBox.position    = GLVector(0.0f, 0.0f, 0.0f)
    val hullMat         = Material()
    hullMat.ambient     = GLVector(0.6f, 0.6f, 0.6f)
    hullMat.diffuse     = GLVector(0.4f, 0.4f, 0.4f)
    hullMat.specular    = GLVector(0.0f, 0.0f, 0.0f)
    hullMat.doubleSided = true
    hullBox.material    = hullMat

    scene.addChild(hullBox)

    val orcMaterial = Material()
    orcMaterial.ambient   = GLVector(0.8f, 0.8f, 0.8f)
    orcMaterial.diffuse   = GLVector(0.5f, 0.5f, 0.5f)
    orcMaterial.specular  = GLVector(0.1f, 0f, 0f)

    val orcMesh = Mesh()
    orcMesh.readFromOBJ(demoFilesPath + "/ORC6.obj", true)
    orcMesh.position  = GLVector(0.0f, 50.0f, -50.0f)
    orcMesh.material  = orcMaterial
    orcMesh.scale     = GLVector(1.0f, 1.0f, 1.0f)
    orcMesh.updateWorld(true, true)
    orcMesh.name      = "ORC6"

    orcMesh.children.asScala.foreach { it =>
      it.material = orcMaterial
    }

    scene.addChild(orcMesh)

    var ticks: Int = 0

    thread {
      val step = 0.02f

      while (true) {
        boxes.iterator.zipWithIndex.foreach { case (box, i) =>
          import math._

          val phi = Pi * 2.0f * ticks / 500.0f

          box.position = GLVector(
            (exp(i) * 20 * sin(phi) + exp(i)).toFloat,
            step * ticks,
            (exp(i) * 20 * cos(phi) + exp(i)).toFloat
          )

          box.children.get(0).position = box.position
        }

        ticks += 1

        Thread.sleep(10)
      }
    }
  }

  override def inputSetup(): Unit = {
    // note: it seems `setupCameraModeSwitching` is not available in scenery 0.1.0

    //    setupCameraModeSwitching(keybinding = "C")

    // switch to arcball mode by manually triggering the behaviour
    //    inputHandlerOption.foreach(h => (h.getBehaviour("toggle_control_mode").asInstanceOf[ClickBehaviour]).click(0, 0))
  }
}