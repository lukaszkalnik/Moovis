package com.lukaszkalnik.moovis.util

import android.content.Context

/**
 * Converts a value in dp to relevant number of pixels.
 */
fun Int.dpToPix(context: Context): Int = (this * context.resources.displayMetrics.density).toInt()