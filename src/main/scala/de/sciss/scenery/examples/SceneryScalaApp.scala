package de.sciss.scenery.examples

import graphics.scenery.backends.Renderer
import graphics.scenery.repl.{REPL => KtREPL}
import graphics.scenery.{Hub, Scene, SceneryDefaultApplication}

abstract class SceneryScalaApp(val applicationName: String, val windowWidth: Int = 1024,
                               val windowHeight: Int = 1024, val wantREPL: Boolean = true)
  extends SceneryDefaultApplication(applicationName, windowWidth, windowHeight, wantREPL) {

  def renderer: Renderer = getRenderer
  def renderer_=(value: Renderer): Unit = setRenderer(value)

  def hub: Hub = getHub

//  def applicationName: String = getApplicationName

  def scene: Scene = getScene

  def repl: KtREPL = getRepl
  def repl_=(value: KtREPL): Unit = setRepl(value)

  def createRenderer(hub: Hub, appName: String, scene: Scene, winWidth: Int, winHeight: Int): Renderer =
    ScalaAccessors.createRenderer(hub, appName, scene, winWidth, winHeight)

  def thread(thunk: => Unit): Unit =
    new Thread {
      override def run(): Unit = thunk
      start()
    }
}