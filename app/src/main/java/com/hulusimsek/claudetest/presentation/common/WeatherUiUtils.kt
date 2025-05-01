package com.hulusimsek.claudetest.presentation.common

import android.content.Context
import com.hulusimsek.claudetest.R

fun getBackgroundImage(context: Context, condition: String): Int {
    val conditionLower = condition.lowercase()

    val sunnyKeywords = listOf(
        context.getString(R.string.condition_sunny),
        context.getString(R.string.condition_clear)
    )

    val partlyCloudyKeywords = listOf(
        context.getString(R.string.condition_partly_cloudy)
    )

    val cloudyKeywords = listOf(
        context.getString(R.string.condition_cloudy),
        context.getString(R.string.condition_overcast)
    )

    val rainyKeywords = listOf(
        context.getString(R.string.condition_rainy),
    )

    val snowyKeywords = listOf(
        context.getString(R.string.condition_snowy),
    )

    val foggyKeywords = listOf(
        context.getString(R.string.condition_foggy),
        context.getString(R.string.condition_mist),
    )

    return when {
        sunnyKeywords.any { conditionLower.contains(it.lowercase(), true) } -> R.drawable.sunny_background
        partlyCloudyKeywords.any { conditionLower.contains(it.lowercase(), true) } -> R.drawable.partly_cloudy_background
        cloudyKeywords.any { conditionLower.contains(it.lowercase(), true) } -> R.drawable.cloudy_background
        rainyKeywords.any { conditionLower.contains(it.lowercase(), true) } -> R.drawable.rainy_background
        snowyKeywords.any { conditionLower.contains(it.lowercase(), true) } -> R.drawable.snowy_background
        foggyKeywords.any { conditionLower.contains(it.lowercase(), true) } -> R.drawable.foggy_background
        else -> R.drawable.default_background
    }
}
