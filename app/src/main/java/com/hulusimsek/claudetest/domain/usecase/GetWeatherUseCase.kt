package com.hulusimsek.claudetest.domain.usecase

import com.hulusimsek.claudetest.domain.model.Weather
import com.hulusimsek.claudetest.domain.repository.WeatherRepository
import com.hulusimsek.claudetest.data.repository.WeatherRepositoryImpl
import javax.inject.Inject

class GetWeatherUseCase @Inject constructor(
    private val repository: WeatherRepository
)
 {
    suspend operator fun invoke(location: String): Result<Weather> {
        return repository.getWeather(location)
    }
}
