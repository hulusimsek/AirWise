package com.hulusimsek.claudetest.presentation.weather

import com.hulusimsek.claudetest.domain.model.Weather

data class WeatherState(
    val isLoading: Boolean = false,
    val weather: Weather? = null,
    val tip: String = "",
    val formattedDate: String = "",
    val error: String? = null
)
