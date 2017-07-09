package de.sciss.scenery.examples
package cluster

import java.util

import de.sciss.scenery.Ops._
import graphics.scenery.{Node, SceneryElement}

object BileExampleApp extends App {
  (new BileExample).main()
}

/**
  * @author Ulrik Günther <hello@ulrik.is>
  * @author Hanns Holger Rutz <contact@sciss.de>
  */
class BileExample extends SceneryScalaApp("Bile Canaliculi example") {
//  var hmd: TrackedStereoGlasses ? = null
  val publishedNodes = new util.ArrayList[Node]()

  override def init(): Unit = {
    logger.warn("*** WARNING - EXPERIMENTAL ***")
    logger.warn("This is an experimental example, which might need additional configuration on your computer")
    logger.warn("or might not work at all. You have been warned!")

//    hmd = new TrackedStereoGlasses("DTrack@10.1.2.201", screenConfig = "CAVEExample.yml")
//    hub.add(SceneryElement.HMDINPUT, hmd)

    renderer = createRenderer(hub, applicationName, scene, 2560, 1600)
    hub.add(SceneryElement.RENDERER, renderer)

    val cam       = DetachedHeadCamera() // (hmd)
    cam.position  = GLVector(.0f, -0.4f, 5.0f)
    cam.perspectiveCamera(50.0f, 1.0f * windowWidth, 1.0f * windowHeight)
    cam.active    = true

    scene.addChild(cam)

    val shell             = Box(GLVector(120.0f, 120.0f, 120.0f) /* , insideNormals = true */)
    val shellMat          = Material()
    shellMat.doubleSided  = true
    shellMat.diffuse      = GLVector(0.0f, 0.0f, 0.0f)
    shellMat.specular     = GLVector.zeroes(3)
    shellMat.ambient      = GLVector.zeroes(3)
    shell.material        = shellMat
    scene.addChild(shell)

    val lights = Seq.fill(4)(PointLight())

    val sqrtTwo = math.sqrt(2).toFloat

    val tetrahedron = Seq(
      GLVector( 1.0f,  0.0f, -1.0f / sqrtTwo),
      GLVector(-1.0f,  0.0f, -1.0f / sqrtTwo),
      GLVector( 0.0f,  1.0f,  1.0f / sqrtTwo),
      GLVector( 0.0f, -1.0f,  1.0f / sqrtTwo)
    )

    (tetrahedron, lights).zipped.foreach { case (position, light) =>
      light.position      = position * 50.0f
      light.emissionColor = GLVector(1.0f, 0.5f, 0.3f) //Numerics.randomVectorFromRange(3, 0.2f, 0.8f)
      light.intensity     = 200.2f
      light.linear        = 0.0f
      light.quadratic     = 0.7f
      scene.addChild(light)
    }

    val bile = Mesh()
    val bileMat = Material()
//    bile.readFromSTL("M:/meshes/adult_mouse_bile_canaliculi_network_2.stl")
    bile.readFromSTL(classOf[BileExample].getResource("models/teapot.stl").getPath)
    bile.scale                = GLVector(0.1f, 0.1f, 0.1f)
    bile.position             = GLVector(-600.0f, -800.0f, -20.0f)
    bileMat.diffuse           = GLVector(0.8f, 0.5f, 0.5f)
    bileMat.specular          = GLVector(1.0f, 1.0f, 1.0f)
    bileMat.specularExponent  = 0.5f
    bile.material             = bileMat
    scene.addChild(bile)

    publishedNodes.add(cam)
    publishedNodes.add(bile)
    publishedNodes.add(shell)

    // this seems to require the next version of scenery:

//    val publisher   = hub.get /* < NodePublisher > */ (SceneryElement.NodePublisher)
//    val subscriber  = hub.get /* < NodeSubscriber > */ (SceneryElement.NodeSubscriber)
//
//    publishedNodes.forEachIndexed {
//      index
//      , node ->
//        publisher ?
//      .nodes ?
//      .put(13337 + index, node)
//
//      subscriber ?
//      .nodes ?
//      .put(13337 + index, node)
//    }

  }
}