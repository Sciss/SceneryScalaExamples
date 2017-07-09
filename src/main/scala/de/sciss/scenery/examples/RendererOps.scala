package de.sciss.scenery.examples

import graphics.scenery.backends.{SceneryWindow, Renderer => KtRenderer}

final class RendererOps(private val peer: KtRenderer) extends AnyVal {
  import peer._

  def window: SceneryWindow = getWindow
}
