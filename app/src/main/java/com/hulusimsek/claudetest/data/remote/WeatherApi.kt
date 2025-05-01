package com.hulusimsek.claudetest.data.remote

import com.hulusimsek.claudetest.data.model.ForecastResponseDto
import com.hulusimsek.claudetest.data.model.WeatherResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("v1/current.json")
    suspend fun getCurrentWeather(
        @Query("q") location: String,
        @Query("lang") language: String,
        @Query("key") apiKey: String
    ): WeatherResponseDto

    @GET("v1/forecast.json")
    suspend fun getForecastWeather(
        @Query("q") location: String,
        @Query("lang") language: String,
        @Query("days") day: String,
        @Query("key") apiKey: String
    ): ForecastResponseDto
}
