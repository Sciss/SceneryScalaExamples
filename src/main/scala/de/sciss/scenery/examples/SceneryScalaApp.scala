package de.sciss.scenery.examples

import java.io.File

import graphics.scenery.backends.Renderer
import graphics.scenery.controls.InputHandler
import graphics.scenery.repl.{REPL => KtREPL}
import graphics.scenery.{Hub, Scene, SceneryDefaultApplication}
import org.slf4j.Logger

abstract class SceneryScalaApp(val applicationName: String, val windowWidth: Int = 1024,
                               val windowHeight: Int = 1024, val wantREPL: Boolean = true)
  extends SceneryDefaultApplication(applicationName, windowWidth, windowHeight, wantREPL) {

  def renderer: Renderer = getRenderer
  def renderer_=(value: Renderer): Unit = setRenderer(value)

  def hub: Hub = getHub

  def scene: Scene = getScene

  def repl: KtREPL = getRepl
  def repl_=(value: KtREPL): Unit = setRepl(value)

  def running: Boolean = getRunning

  def logger: Logger = getLogger

  def createRenderer(hub: Hub, appName: String, scene: Scene, winWidth: Int, winHeight: Int): Renderer =
    ScalaAccessors.createRenderer(hub, appName, scene, winWidth, winHeight)

  def inputHandlerOption: Option[InputHandler] = Option(getInputHandler)

  def thread(thunk: => Unit): Unit =
    new Thread {
      override def run(): Unit = thunk
      start()
    }

  def demoFilesPath: String = {
    val f = new File("models")
    if (sys.env.contains("SCENERY_DEMO_FILES") || !f.exists()) getDemoFilesPath
    else f.getPath
  }
}