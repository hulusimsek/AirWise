package com.hulusimsek.claudetest.domain.repository

import com.hulusimsek.claudetest.domain.model.ForecastWeather
import com.hulusimsek.claudetest.domain.model.Weather

interface WeatherRepository {
    suspend fun getWeather(location: String): Result<Weather>
    suspend fun getForecastWeather(location: String): Result<ForecastWeather>
}
