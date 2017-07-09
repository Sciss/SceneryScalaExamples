package de.sciss.scenery.ops

import graphics.scenery.{Line => KtLine}

final class LineOps(private val peer: KtLine) extends AnyVal {
  import peer._

  def edgeWidth: Float = getEdgeWidth
  def edgeWidth_=(value: Float): Unit = setEdgeWidth(value)
}