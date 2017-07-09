package de.sciss.scenery.examples

import graphics.scenery.backends.{SceneryWindow => KtSceneryWindow}

final class SceneryWindowOps(private val peer: KtSceneryWindow) extends AnyVal {
  import peer._

  def width: Int = getWidth
  def width_=(value: Int): Unit = setWidth(value)

  def height: Int = getHeight
  def height_=(value: Int): Unit = setHeight(value)
}