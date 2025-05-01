package com.hulusimsek.claudetest.domain.model

data class Weather(
    val location: Location,
    val current: Current
)

data class ForecastWeather(
    val location: Location,
    val current: Current,
    val forecast: Forecast
)

data class Location(
    val name: String,
    val region: String,
    val country: String,
    val lat: Double,
    val lon: Double,
    val tzId: String,
    val localtimeEpoch: Long,
    val localtime: String
)

data class Current(
    val lastUpdatedEpoch: Long,
    val lastUpdated: String,
    val tempC: Double,
    val tempF: Double,
    val isDay: Int,
    val condition: Condition,
    val windMph: Double,
    val windKph: Double,
    val windDegree: Int,
    val windDir: WindDirection,
    val pressureMb: Double,
    val pressureIn: Double,
    val precipMm: Double,
    val precipIn: Double,
    val humidity: Int,
    val cloud: Int,
    val feelslikeC: Double,
    val feelslikeF: Double,
    val visKm: Double,
    val visMiles: Double,
    val uv: Double,
    val gustMph: Double,
    val gustKph: Double
)

data class Condition(
    val text: String,
    val icon: String,
    val code: Int
)

//-----

data class Forecast(
    val forecastDays: List<ForecastDay>
)

data class ForecastDay(
    val date: String,
    val dateEpoch: Long,
    val day: Day,
    val astro: Astro,
    val hours: List<Hour>
)

data class Day(
    val maxTempC: Double,
    val maxTempF: Double,
    val minTempC: Double,
    val minTempF: Double,
    val avgTempC: Double,
    val avgTempF: Double,
    val maxWindMph: Double,
    val maxWindKph: Double,
    val totalPrecipMm: Double,
    val totalPrecipIn: Double,
    val totalSnowCm: Double,
    val avgVisibilityKm: Double,
    val avgVisibilityMiles: Double,
    val avgHumidity: Int,
    val willItRain: Boolean,
    val chanceOfRain: Int,
    val willItSnow: Boolean,
    val chanceOfSnow: Int,
    val condition: Condition,
    val uvIndex: Double
)

data class Astro(
    val sunrise: String,
    val sunset: String,
    val moonrise: String,
    val moonset: String,
    val moonPhase: String,
    val moonIllumination: Int,
    val isMoonUp: Boolean,
    val isSunUp: Boolean
)

data class Hour(
    val timeEpoch: Long,
    val time: String,
    val tempC: Double,
    val tempF: Double,
    val isDay: Boolean,
    val condition: Condition,
    val windMph: Double,
    val windKph: Double,
    val windDegree: Int,
    val windDirection: String,
    val pressureMb: Double,
    val pressureIn: Double,
    val precipMm: Double,
    val precipIn: Double,
    val snowCm: Double,
    val humidity: Int,
    val cloud: Int,
    val feelsLikeC: Double,
    val feelsLikeF: Double,
    val windChillC: Double,
    val windChillF: Double,
    val heatIndexC: Double,
    val heatIndexF: Double,
    val dewPointC: Double,
    val dewPointF: Double,
    val willItRain: Boolean,
    val chanceOfRain: Int,
    val willItSnow: Boolean,
    val chanceOfSnow: Int,
    val visibilityKm: Double,
    val visibilityMiles: Double,
    val gustMph: Double,
    val gustKph: Double,
    val uvIndex: Double
)

