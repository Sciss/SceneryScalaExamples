package de.sciss.scenery.examples
package advanced

import java.util

import graphics.scenery.SceneryElement
import de.sciss.scenery.Ops._
import graphics.scenery.backends.ShaderPreference
import graphics.scenery.utils.Numerics

import scala.collection.JavaConverters._

object MultiBoxInstancedExampleApp extends App {
  (new MultiBoxInstancedExample).main()
}

/**
  * Demo animating multiple boxes with instancing.
  *
  * @author Ulrik Günther <hello@ulrik.is>
  * @author Hanns Holger Rutz <contact@sciss.de>
  */
class MultiBoxInstancedExample extends SceneryScalaApp("MultiBoxInstancedExample") {
  override def init(): Unit = {
    renderer = createRenderer(hub, applicationName, scene, windowWidth, windowHeight)
    hub.add(SceneryElement.RENDERER, renderer)

    val cam = DetachedHeadCamera()
    cam.position = GLVector(10.0f, 10.0f, 10.0f)
    cam.perspectiveCamera(60.0f, 1.0f * windowWidth, 1.0f * windowHeight, 1.0f, 1000.0f)
    cam.active = true
    scene.addChild(cam)

    val boundaryWidth  = 15.0
    val boundaryHeight = 15.0

    val container = Mesh()

    val b = Box(GLVector(0.2f, 0.2f, 0.2f))
    b.material = Material()
    b.material.diffuse  = GLVector(1.0f, 1.0f, 1.0f)
    b.material.ambient  = GLVector(1.0f, 1.0f, 1.0f)
    b.material.specular = GLVector(1.0f, 1.0f, 1.0f)
    b.name = "boxmaster"

    b.instanceMaster = true
    b.instancedProperties.put("ModelViewMatrix" , () => b.modelView )
    b.instancedProperties.put("ModelMatrix"     , () => b.model     )
    b.instancedProperties.put("MVP"             , () => b.mvp       )
    b.metadata.put(
      "ShaderPreference",
      new ShaderPreference(
        Seq("DefaultDeferredInstanced.vert", "DefaultDeferred.frag").asJava,
        new util.HashMap(),
        Seq("DeferredShadingRenderer").asJava
      )
    )
    scene.addChild(b)

    Seq.tabulate((boundaryWidth * boundaryHeight * boundaryHeight).toInt) { it =>
      val p = Node(s"Parent of $it")

      val inst = Mesh()
      inst.name       = s"Box_$it"
      inst.instanceOf = b
      inst.material   = b.material

      inst.instancedProperties.put("ModelViewMatrix", () => inst.modelView)
      inst.instancedProperties.put("ModelMatrix"    , () => inst.model    )
      inst.instancedProperties.put("MVP"            , () => inst.mvp      )

      val k: Double =  it %  boundaryWidth
      val j: Double = (it /  boundaryWidth) % boundaryHeight
      val i: Double =  it / (boundaryWidth  * boundaryHeight)

      import math._

      p.position          = GLVector(floor(i).toFloat * 3.0f, floor(j).toFloat * 3.0f, floor(k).toFloat * 3.0f)
      p.needsUpdate       = true
      p.needsUpdateWorld  = true
      p.addChild(inst)

      container.addChild(p)
      inst
    }

    scene.addChild(container)

    val lights = Seq.fill(20) {
      val it = PointLight()
      it.position       = Numerics.randomVectorFromRange(3, -600.0f, 600.0f)
      it.emissionColor  = Numerics.randomVectorFromRange(3, 0.1f, 0.9f)
      it.intensity      = Numerics.randomFromRange(5.0f, 150.0f)
      it.linear         = 0.1f
      it.quadratic      = 0.8f

      scene.addChild(it)
      it
    }

    val hullBox           = Box(GLVector(100.0f, 100.0f, 100.0f))
    hullBox.position      = GLVector(0.0f, 0.0f, 0.0f)
    val hullBoxM          = Material()
    hullBoxM.ambient      = GLVector(0.6f, 0.6f, 0.6f)
    hullBoxM.diffuse      = GLVector(0.4f, 0.4f, 0.4f)
    hullBoxM.specular     = GLVector(0.0f, 0.0f, 0.0f)
    hullBoxM.doubleSided  = true
    hullBox.material      = hullBoxM

    scene.addChild(hullBox)

    var ticks: Int = 0

    thread {
      while (true) {
        import math._

        lights.iterator.zipWithIndex.foreach { case (light, i) =>
          val phi   = Pi * 2.0f * ticks / 1500.0f
          val e     = exp(i / 10.0)

          light.position = GLVector(
            (i * 10.0f * sin(phi) + e).toFloat,
             i *  5.0f - 100.0f,
            (i * 10.0f * cos(phi) + e).toFloat
          )
        }

        ticks += 1

        container.rotation.rotateByEuler(0.001f, 0.001f, 0.0f)
        container.needsUpdate = true

        Thread.sleep(10)
      }
    }
  }
}