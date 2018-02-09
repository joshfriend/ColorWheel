package com.fueledbycaffeine.colorwheel.ui

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.fueledbycaffeine.colorwheel.ColorPalette
import com.fueledbycaffeine.colorwheel.R


class ColorChipRecyclerAdapter(private val itemSelected: (String, Int) -> Unit): RecyclerView.Adapter<ColorChipViewHolder>() {
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorChipViewHolder {
    val inflater = LayoutInflater.from(parent.context)
    val view = inflater.inflate(R.layout.color_chip, parent, false)
    return ColorChipViewHolder(view)
  }

  override fun getItemCount() = ColorPalette.colors.size

  override fun onBindViewHolder(holder: ColorChipViewHolder, position: Int) {
    val (name, color) = ColorPalette.colors[holder.adapterPosition]
    holder.setup(name, color)
    holder.itemView.setOnClickListener { itemSelected(name, color) }
  }
}
