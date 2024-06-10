package aandrosov.gnews.ui.utils

import android.content.Context
import android.util.TypedValue

fun Context.dpToPx(value: Float): Float {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        value,
        resources.displayMetrics
    )
}