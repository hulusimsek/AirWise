package com.hulusimsek.claudetest.domain.usecase

import com.hulusimsek.claudetest.domain.model.ForecastWeather
import com.hulusimsek.claudetest.domain.model.Weather
import com.hulusimsek.claudetest.domain.repository.WeatherRepository
import javax.inject.Inject

class GetForecastWeatherUseCase @Inject constructor(
    private val repository: WeatherRepository
)
{
    suspend operator fun invoke(location: String): Result<ForecastWeather> {
        return repository.getForecastWeather(location)
    }
}