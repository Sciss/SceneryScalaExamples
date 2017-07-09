package de.sciss.scenery.examples
package advanced

import java.util

import de.sciss.scenery.Ops._
import graphics.scenery.backends.ShaderPreference
import graphics.scenery.utils.Numerics
import graphics.scenery.SceneryElement

import scala.collection.JavaConverters._

object BloodCellsExampleApp extends App {
  (new BloodCellsExample).main()
}

/**
  * @author Ulrik Günther <hello@ulrik.is>
  * @author Hanns Holger Rutz <contact@sciss.de>
  */
class BloodCellsExample extends SceneryScalaApp("BloodCellsExample", windowWidth = 1280, windowHeight = 720) {
  //  private var ovr: OpenVRHMD = null

  override def init(): Unit = {
    //  ovr = OpenVRHMD(seated = false, useCompositor = true)
    //  hub.add(SceneryElement.HMDInput, ovr!!)

    renderer = createRenderer(hub, applicationName, scene, windowWidth, windowHeight)
    hub.add(SceneryElement.RENDERER, renderer)

    val cam = DetachedHeadCamera()
    cam.position = GLVector(0.0f, 20.0f, -20.0f)
    cam.perspectiveCamera(50.0f, 1.0f * windowWidth, 1.0f * windowHeight, 10.0f, 5000.0f)
    cam.rotation = Quaternion().setFromEuler(-1.5f, -0.5f, 0.0f)
    cam.active = true

    scene.addChild(cam)

    val boxes   = Seq.fill(10)(Box(GLVector(0.5f, 0.5f, 0.5f)))
    val lights  = Seq.fill(10)(PointLight())

    (boxes, lights).zipped.foreach { case (box, light) =>
      box.material = Material()
      box.addChild(light)
      box.visible = false
      scene.addChild(box)
    }

    lights.foreach { it =>
      it.position = GLVector(Numerics.randomFromRange(00.0f, 600.0f),
        Numerics.randomFromRange(00.0f, 600.0f),
        Numerics.randomFromRange(00.0f, 600.0f))
      it.emissionColor = GLVector(1.0f, 1.0f, 1.0f)

      for {
        p <- it.parentOption
        m <- p.materialOption
      } {
        m.diffuse = it.emissionColor
      }

      it.intensity  = 100.0f
      it.linear     = 0f
      it.quadratic  = 0.001f

      scene.addChild(it)
    }

    val hullMaterial = Material()
    hullMaterial.ambient      = GLVector(0.0f, 0.0f, 0.0f)
    hullMaterial.diffuse      = GLVector(1.0f, 1.0f, 1.0f)
    hullMaterial.specular     = GLVector(0.0f, 0.0f, 0.0f)
    hullMaterial.doubleSided  = true

    val hull = Box(GLVector(5000.0f, 5000.0f, 5000.0f))
    hull.material = hullMaterial

    scene.addChild(hull)

    val e_material = Material()
    e_material.ambient      = GLVector(0.1f, 0.0f, 0.0f)
    e_material.diffuse      = GLVector(0.4f, 0.0f, 0.02f)
    e_material.specular     = GLVector(0.05f, 0f, 0f)
    e_material.doubleSided  = false

    val e_mesh = Mesh()
    e_mesh.readFromOBJ(demoFilesPath + "/erythrocyte_simplified.obj", true)
    e_mesh.material        = e_material
    e_mesh.name            = "Erythrocyte_Master"
    e_mesh.instanceMaster  = true
    e_mesh.instancedProperties.put("ModelViewMatrix", () => e_mesh.modelView)
    e_mesh.instancedProperties.put("ModelMatrix"    , () => e_mesh.model    )
    e_mesh.instancedProperties.put("MVP"            , () => e_mesh.mvp      )
    scene.addChild(e_mesh)

    e_mesh.metadata.put(
      "ShaderPreference",
      new ShaderPreference(
        Seq("DefaultDeferredInstanced.vert", "DefaultDeferred.frag").asJava,
        new util.HashMap,
        Seq("DeferredShadingRenderer").asJava
      )
    )

    val l_material = Material()
    l_material.ambient      = GLVector(0.1f, 0.0f, 0.0f)
    l_material.diffuse      = GLVector(0.8f, 0.7f, 0.7f)
    l_material.specular     = GLVector(0.05f, 0f, 0f)
    l_material.doubleSided  = false

    val leucocyte = Mesh()
    leucocyte.readFromOBJ(demoFilesPath + "/leukocyte_simplified.obj", true)
    leucocyte.material = l_material
    leucocyte.name = "leucocyte_Master"
    leucocyte.instanceMaster = true
    leucocyte.instancedProperties.put("ModelViewMatrix" , () => leucocyte.modelView )
    leucocyte.instancedProperties.put("ModelMatrix"     , () => leucocyte.model     )
    leucocyte.instancedProperties.put("MVP"             , () => leucocyte.mvp       )
    scene.addChild(leucocyte)

    leucocyte.metadata.put(
      "ShaderPreference",
      new ShaderPreference(
        Seq("DefaultDeferredInstanced.vert", "DefaultDeferred.frag").asJava,
        new util.HashMap(),
        Seq("DeferredShadingRenderer").asJava
      )
    )

    val posRange = 1200.0f
    val container = Node("Cell container")

    val leucocytes = Seq.tabulate(100) { i =>
      val v = Mesh()
      v.name = s"leucocyte_$i"
      v.instanceOf = leucocyte
      v.instancedProperties.put("ModelViewMatrix" , () => v.modelView )
      v.instancedProperties.put("ModelMatrix"     , () => v.model     )
      v.instancedProperties.put("MVP"             , () => v.mvp       )

      val p = Node(s"parent of $i")
      val scale = Numerics.randomFromRange(30.0f, 40.0f)

      v.material = l_material
      v.scale = GLVector(scale, scale, scale)
      v.children.asScala.foreach {
        ch => ch.material = l_material
      }
      v.rotation.setFromEuler(
        Numerics.randomFromRange(0.01f, 0.9f),
        Numerics.randomFromRange(0.01f, 0.9f),
        Numerics.randomFromRange(0.01f, 0.9f)
      )

      p.position = Numerics.randomVectorFromRange(3, 0.0f, posRange)
      p.addChild(v)

      container.addChild(p)
      v
    }

    val erythrocytes = Seq.tabulate(2000) { i =>
      val v = Mesh()
      v.name = s"erythrocyte_$i"
      v.instanceOf = e_mesh
      v.instancedProperties.put("ModelViewMatrix" , () => v.modelView )
      v.instancedProperties.put("ModelMatrix"     , () => v.model     )
      v.instancedProperties.put("MVP"             , () => v.mvp       )

      val p = Node(s"parent of $i")
      val scale = Numerics.randomFromRange(5f, 12f)

      v.material  = e_material
      v.scale     = GLVector(scale, scale, scale)
      v.children.asScala.foreach {
        ch => ch.material = e_material
      }
      v.rotation.setFromEuler(
        Numerics.randomFromRange(0.01f, 0.9f),
        Numerics.randomFromRange(0.01f, 0.9f),
        Numerics.randomFromRange(0.01f, 0.9f)
      )

      p.position = Numerics.randomVectorFromRange(3, 0.0f, posRange)
      p.addChild(v)

      container.addChild(p)
      v
    }

    scene.addChild(container)

    def hoverAndTumble (obj: Node, magnitude: Float, phi: Float, index: Int): Unit = {
      import math._
      val axis = GLVector(sin(0.01 * index).toFloat, -cos(0.01 * index).toFloat, index * 0.01f).normalize
      obj.rotation.rotateByAngleNormalAxis(magnitude, axis.x(), axis.y(), axis.z())
      obj.rotation.rotateByAngleY(-1.0f * magnitude)
    }

    var ticks: Int = 0
    updateFunction = { () =>
      import math._
      val step = 0.05f
      val phi = Pi * 2.0f * ticks / 2000.0f

      boxes.iterator.zipWithIndex.foreach { case (box, i) =>
        val expi = exp(i)
        box.position = GLVector(
          (expi * 10 * sin(phi) + expi).toFloat,
          step * ticks,
          (expi * 10 * cos(phi) + expi).toFloat
        )

        box.children.get(0).position = box.position
      }

      erythrocytes.iterator.zipWithIndex.foreach { case (erythrocyte, i) =>
        hoverAndTumble(erythrocyte, 0.003f, phi.toFloat, i)
      }
      leucocytes.iterator.zipWithIndex.foreach { case (leukocyte, i) =>
        hoverAndTumble(leukocyte, 0.001f, phi.toFloat / 100.0f, i)
      }

      container.position = container.position - GLVector(0.1f, 0.1f, 0.1f)

      container.updateWorld(true, false)
      ticks += 1
    }
  }
}