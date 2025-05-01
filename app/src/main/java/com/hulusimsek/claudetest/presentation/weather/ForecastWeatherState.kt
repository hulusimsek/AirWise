package com.hulusimsek.claudetest.presentation.weather

import com.hulusimsek.claudetest.domain.model.ForecastWeather

data class ForecastWeatherState(
    val isLoading: Boolean = false,
    val forecastWeather: ForecastWeather? = null,
    val tip: String = "",
    val formattedDate: String = "",
    val error: String? = null
)