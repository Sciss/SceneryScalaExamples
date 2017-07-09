package de.sciss.scenery.examples
package cluster

import java.io.File

import de.sciss.scenery.Ops._
import graphics.scenery.controls.InputHandler
import graphics.scenery.{Node, SceneryElement}
import org.scijava.ui.behaviour.ClickBehaviour

import scala.collection.mutable

object ClusterExampleApp extends App {
  (new ClusterExample).main()
}

/**
  * @author Ulrik Günther <hello@ulrik.is>
  * @author Hanns Holger Rutz <contact@sciss.de>
  */
class ClusterExample extends SceneryScalaApp("Clustered Volume Rendering example") {
  //  var hmd: TrackedStereoGlasses? = null
  val publishedNodes: mutable.Buffer[Node] = mutable.Buffer.empty

  override def init(): Unit = {
    logger.warn("*** WARNING - EXPERIMENTAL ***")
    logger.warn("This is an experimental example, which might need additional configuration on your computer")
    logger.warn("or might not work at all. You have been warned!")

//    hmd = TrackedStereoGlasses("DTrack@10.1.2.201", screenConfig = "CAVEExample.yml")
//    hub.add(SceneryElement.HMDInput, hmd !!)

    renderer = createRenderer(hub, applicationName, scene, 2560, 1600)
    hub.add(SceneryElement.RENDERER, renderer)

    val cam = DetachedHeadCamera() // (hmd)
    cam.position = GLVector(.0f, -0.4f, 2.0f)
    cam.perspectiveCamera(50.0f, 1.0f * windowWidth, 1.0f * windowHeight)
    cam.active = true
    scene.addChild(cam)

    val box               = Box(GLVector(2.0f, 2.0f, 2.0f))
    val boxMat            = Material()
    boxMat.diffuse        = GLVector(1.0f, 0.0f, 0.0f)
    box.material          = boxMat

    val shell             = Box(GLVector(120.0f, 120.0f, 120.0f) /* , insideNormals = true */)
    val shellMat          = Material()
    shellMat.doubleSided  = true
    shellMat.diffuse      = GLVector(0.0f, 0.0f, 0.0f)
    shellMat.specular     = GLVector.zeroes(3)
    shellMat.ambient      = GLVector.zeroes(3)
    shell.material        = shellMat
    scene.addChild(shell)

    // not available in scenery 0.1.0

//    val volume = Volume()
//
//    volume.visible = true
//    scene.addChild(volume)

    for (i <- 0 until 3) {
      val light           = PointLight()
      light.position      = GLVector(4.0f * i, 4.0f * i, 4.0f)
      light.emissionColor = GLVector(1.0f, 1.0f, 1.0f)
      light.intensity     = 200.2f * (i + 1)
      light.linear        = 1.8f
      light.quadratic     = 0.7f
      scene.addChild(light)
    }

    val folder = new File("M:/CAVE_DATA/histones-isonet/stacks/default/")

    val files = Option(folder.listFiles()).fold[List[File]](Nil)(_.toList)
    val volumes = files.collect {
      case it if it.isFile && it.getName.endsWith("raw") => it.getAbsolutePath
    } .sorted

    volumes.foreach { it =>
      logger.info(s"Volume: $it")
    }

//    var currentVolume = 0
//
//    def nextVolume(): String = {
//      val v = volumes(currentVolume % volumes.size)
//      currentVolume += 1
//      v
//    }

    publishedNodes += cam
    publishedNodes += box
//    publishedNodes += volume

//    val publisher   = hub.get < NodePublisher > (SceneryElement.NodePublisher)
//    val subscriber  = hub.get < NodeSubscriber > (SceneryElement.NodeSubscriber)
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

//    val min_delay = 600
//
//    if (publisher != null) {
//      thread {
//        while (!scene.initialized) {
//          Thread.sleep(1000)
//        }
//
//
//        while (true) {
//          val start = System.currentTimeMillis()
//
//          logger.info("Reading next volume...")
////          volume.currentVolume = nextVolume()
//
//          val time_to_read = System.currentTimeMillis() - start
//
//          logger.info("took ${time_to_read} ms")
//          Thread.sleep(Math.max(0, min_delay - time_to_read))
//
//        }
//      }
//    }
  }

  override def inputSetup(): Unit = {
//    setupCameraModeSwitching(keybinding = "C")

    val inputHandler = hub.get(SceneryElement.INPUT).asInstanceOf[InputHandler]

    val cycleObjects = new ClickBehaviour {
      def click(x: Int, y: Int): Unit = {
        val currentIndex = publishedNodes.indexWhere(_.visible)

        publishedNodes.foreach(_.visible = false)
        val n = publishedNodes((currentIndex + 1) % (publishedNodes.size - 1))
//        n.run {
          n.visible = true
          logger.info(s"Now visible: $n")
//        }
      }
    }

    inputHandler.addBehaviour("cycle_objects", cycleObjects)
    inputHandler.addKeyBinding("cycle_objects", "N")
  }
}