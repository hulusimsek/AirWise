package com.hulusimsek.claudetest.domain.usecase

import com.hulusimsek.claudetest.R
import com.hulusimsek.claudetest.domain.common.ResourceProvider
import javax.inject.Inject


class GetWeatherTipUseCase @Inject constructor(
    private val resourceProvider: ResourceProvider
) {
    operator fun invoke(condition: String): String {
        val lower = condition.lowercase()

        val rainKeyword = resourceProvider.getString(R.string.keyword_rain)
        val sunKeyword = resourceProvider.getString(R.string.keyword_sun)
        val snowKeyword = resourceProvider.getString(R.string.keyword_snow)
        val cloudKeyword = resourceProvider.getString(R.string.keyword_cloud)

        return when {
            lower.contains(rainKeyword.lowercase()) -> resourceProvider.getString(R.string.weather_rainy_tip)
            lower.contains(sunKeyword.lowercase()) -> resourceProvider.getString(R.string.weather_sunny_tip)
            lower.contains(snowKeyword.lowercase()) -> resourceProvider.getString(R.string.weather_snowy_tip)
            lower.contains(cloudKeyword.lowercase()) -> resourceProvider.getString(R.string.weather_cloudy_tip)
            else -> resourceProvider.getString(R.string.weather_normal_tip)
        }
    }
}