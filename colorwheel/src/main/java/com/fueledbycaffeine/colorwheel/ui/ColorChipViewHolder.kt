package com.fueledbycaffeine.colorwheel.ui

import android.graphics.Color
import android.support.annotation.ColorRes
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.View
import com.fueledbycaffeine.colorwheel.ColorUtil
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.color_chip.*
import org.jetbrains.annotations.Nls

class ColorChipViewHolder(override val containerView: View?): RecyclerView.ViewHolder(containerView), LayoutContainer {
  fun setup(@Nls title: String, @ColorRes colorRes: Int) {
    name.text = title
    val color = ContextCompat.getColor(itemView.context, colorRes)
    hexCode.text = String.format("#%06x", 0xFFFFFF and color)
    itemView.setBackgroundColor(color)

    val textColor = ColorUtil.textColorForBackground(color)
    name.setTextColor(textColor)
    hexCode.setTextColor(textColor)
  }
}
