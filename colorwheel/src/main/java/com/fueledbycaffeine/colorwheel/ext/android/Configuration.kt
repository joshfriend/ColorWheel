package com.fueledbycaffeine.colorwheel.ext.android

import android.content.res.Configuration
import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import android.content.res.Configuration.ORIENTATION_PORTRAIT

inline val Configuration.isPortrait: Boolean get() = this.orientation == ORIENTATION_PORTRAIT
inline val Configuration.isLandscape: Boolean get() = this.orientation == ORIENTATION_LANDSCAPE
