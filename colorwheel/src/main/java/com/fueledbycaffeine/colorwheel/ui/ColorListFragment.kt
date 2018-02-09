package com.fueledbycaffeine.colorwheel.ui

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.annotation.ColorInt
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.graphics.drawable.DrawableCompat
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fueledbycaffeine.colorwheel.ColorUtil
import com.fueledbycaffeine.colorwheel.R
import com.fueledbycaffeine.colorwheel.ext.android.*
import kotlinx.android.synthetic.main.fragment_color_list.*


class ColorListFragment: Fragment() {
  companion object {
    private const val KEY_COLOR_NAME = "colorName"
    private const val KEY_COLOR_VALUE = "color"
  }

  @ColorInt private var selectedColor = Color.WHITE

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.fragment_color_list, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    makeNavBarsFancy()

    val displayMetrics = resources.displayMetrics
    // Round down column count
    var columns = (displayMetrics.widthPixels / resources.getDimension(R.dimen.min_chip_width)).toInt()
    // Round down to nearest even number
    columns -= (columns % 2)
    // Ensure at least one column
    columns = Math.max(1, columns)
    recyclerView.layoutManager = GridLayoutManager(context!!, columns)
    recyclerView.adapter = ColorChipRecyclerAdapter { title, colorRes ->
      toolbar.title = title
      selectedColor = ContextCompat.getColor(activity!!, colorRes)
      barLayout.setExpanded(true, true)
      this.updateNavigationTheme(view, selectedColor)
    }

    // Restore state from rotation or configuration change
    selectedColor = if (savedInstanceState != null) {
      toolbar.title = savedInstanceState.getString(KEY_COLOR_NAME)
      savedInstanceState.getInt(KEY_COLOR_VALUE)
    } else {
      // Default color
      toolbar.title = "Amber 700"
      ContextCompat.getColor(activity!!, R.color.md_amber_700)
    }
    this.updateNavigationTheme(view, selectedColor)
  }

  override fun onSaveInstanceState(outState: Bundle) {
    super.onSaveInstanceState(outState)
    // Prepare for rotation or configuration change
    outState.putString(KEY_COLOR_NAME, toolbar.title.toString())
    outState.putInt(KEY_COLOR_VALUE, selectedColor)
  }

  private fun updateNavigationTheme(view: View, @ColorInt color: Int) {
    // Pick a text color that will be most visible on the selected color.
    val titleTextColor = ColorUtil.textColorForBackground(color)
    toolbar.setTitleTextColor(titleTextColor)
    toolbar.setSubtitleTextColor(titleTextColor)
    toolbar.setBackgroundColor(color)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      toolbar.navigationIcon?.setTint(titleTextColor)
    } else {
      // You poor soul...
      toolbar.navigationIcon?.let { DrawableCompat.setTint(it, titleTextColor) }
    }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      val statusBarColor = ColorUtil.darken(color, byAmount = 0.20f)
      activity?.window?.statusBarColor = statusBarColor

      // Ensure status bar icons will still be legible with the new color
      when (titleTextColor) {
        Color.BLACK -> view.useLightStatusBarStyle()
        else -> view.useDarkStatusBarStyle()
      }
    }
  }

  private fun makeNavBarsFancy() {
    // It is not possible to tell which window your app occupies in multiwindow
    // mode when fitsSystemWindows is false. Apps can't draw under the navbar
    // in multiwindow mode anyways.
    val fitSystemWindows = if (activity?.isInMultiWindow == true) {
      true
    } else {
      resources.getBoolean(R.bool.fullscreen_style_fit_system_windows)
    }
    // Override the activity's theme when in multiwindow.
    coordinator.fitsSystemWindows = fitSystemWindows

    if (!fitSystemWindows) {
      // Inset bottom of content if drawing under the translucent navbar, but
      // only if the navbar is a software bar and is on the bottom of the
      // screen.
      if (resources.showsSoftwareNavBar && resources.isNavBarAtBottom) {
        recyclerView.updatePaddingRelative(
          bottom = recyclerView.paddingBottom + resources.navBarHeight
        )
      }

      // Inset the toolbar when it is drawn under the status bar.
      barLayout.updatePaddingRelative(
        top = barLayout.paddingTop + resources.statusBarHeight
      )
    }
  }
}
