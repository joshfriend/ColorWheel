package com.fueledbycaffeine.colorwheel

import android.graphics.Color
import android.support.annotation.ColorInt
import android.support.annotation.FloatRange


object ColorUtil {
  /**
   * Darkens a [color] by [byAmount] (percentage)
   */
  @ColorInt fun darken(@ColorInt color: Int, byAmount: Float): Int {
    val hsv = FloatArray(3)
    Color.colorToHSV(color, hsv)
    hsv[2] *= (1 - byAmount)
    return Color.HSVToColor(hsv)
  }

  /**
   * Computes the perceived luminance of a [color].
   */
  @FloatRange(from = 0.0, to = 1.0)
  fun relativeLuminanceOf(@ColorInt color: Int): Double {
    // https://www.w3.org/TR/WCAG20/#relativeluminancedef
    return 1 - (
        0.299 * Color.red(color)
      + 0.587 * Color.green(color)
      + 0.114 * Color.blue(color)
    ) / 255
  }

  @ColorInt fun textColorForBackground(@ColorInt color: Int): Int {
    return if (relativeLuminanceOf(color) < 0.5) {
      Color.BLACK
    } else {
      Color.WHITE
    }
  }
}
