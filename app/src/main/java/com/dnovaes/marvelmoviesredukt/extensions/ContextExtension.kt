package com.dnovaes.marvelmoviesredukt.extensions

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import com.dnovaes.marvelmoviesredukt.ui.activities.base.ReactiveActivity

fun Context.dp(resourceReference: Int) = resources.getDimensionPixelSize(resourceReference)

fun Context.sp(resourceReference: Int) = resources.getDimension(resourceReference)

fun Context.color(resourceReference: Int) = ContextCompat.getColor(this, resourceReference)

fun Context.open(activity: ReactiveActivity, intent: Intent) {
    startActivity(intent)
    activity.finish()
}
