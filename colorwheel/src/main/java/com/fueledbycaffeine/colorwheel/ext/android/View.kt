package com.fueledbycaffeine.colorwheel.ext.android

import android.os.Build
import android.support.annotation.Px
import android.support.annotation.RequiresApi
import android.view.View

@RequiresApi(Build.VERSION_CODES.M)
fun View.useLightStatusBarStyle() {
  this.systemUiVisibility = this.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
}

@RequiresApi(Build.VERSION_CODES.M)
fun View.useDarkStatusBarStyle() {
  this.systemUiVisibility = this.systemUiVisibility and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
}

fun View.updatePaddingRelative(
  @Px start: Int = paddingStart,
  @Px top: Int = paddingTop,
  @Px end: Int = paddingEnd,
  @Px bottom: Int = paddingBottom
) {
  setPaddingRelative(start, top, end, bottom)
}
