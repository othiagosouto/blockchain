package dev.thiagosouto.blockchain.features.commons.ext

import android.content.Context
import android.graphics.Color
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt

@ColorInt
fun Context.getThemeColorFrom(@AttrRes attrs: Int): Int {
    val a = this.obtainStyledAttributes(null, intArrayOf(attrs))
    try {
        return a.getColor(0, Color.MAGENTA)
    } finally {
        a.recycle()
    }
}
