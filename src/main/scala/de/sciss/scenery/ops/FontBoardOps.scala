package de.sciss.scenery.ops

import graphics.scenery.{FontBoard => KtFontBoard}

final class FontBoardOps(private val peer: KtFontBoard) extends AnyVal {
  import peer._

  def text: String = getText
  def text_=(value: String): Unit = setText(value)
}