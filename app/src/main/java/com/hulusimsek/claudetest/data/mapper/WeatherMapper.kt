package com.hulusimsek.claudetest.data.mapper

import com.hulusimsek.claudetest.data.model.AstroDto
import com.hulusimsek.claudetest.data.model.ConditionDto
import com.hulusimsek.claudetest.data.model.CurrentDto
import com.hulusimsek.claudetest.data.model.DayDto
import com.hulusimsek.claudetest.data.model.ForecastDayDto
import com.hulusimsek.claudetest.data.model.ForecastDto
import com.hulusimsek.claudetest.data.model.ForecastResponseDto
import com.hulusimsek.claudetest.data.model.HourDto
import com.hulusimsek.claudetest.data.model.LocationDto
import com.hulusimsek.claudetest.data.model.WeatherResponseDto
import com.hulusimsek.claudetest.domain.model.Astro
import com.hulusimsek.claudetest.domain.model.Condition
import com.hulusimsek.claudetest.domain.model.Current
import com.hulusimsek.claudetest.domain.model.Day
import com.hulusimsek.claudetest.domain.model.Forecast
import com.hulusimsek.claudetest.domain.model.ForecastDay
import com.hulusimsek.claudetest.domain.model.ForecastWeather
import com.hulusimsek.claudetest.domain.model.Hour
import com.hulusimsek.claudetest.domain.model.Location
import com.hulusimsek.claudetest.domain.model.Weather
import com.hulusimsek.claudetest.domain.model.WindDirection

fun WeatherResponseDto.toWeather(): Weather {
    return Weather(
        location = location!!.toLocation(),
        current = current!!.toCurrent()
    )
}

fun ForecastResponseDto.toForecastWeather(): ForecastWeather {
    return ForecastWeather(
        location = location!!.toLocation(),
        forecast = forecast.toForecast(),
        current = current!!.toCurrent()
    )
}

fun LocationDto.toLocation(): Location {
    return Location(
        name = name,
        region = region,
        country = country,
        lat = lat,
        lon = lon,
        tzId = tzId,
        localtimeEpoch = localtimeEpoch,
        localtime = localtime
    )
}

fun CurrentDto.toCurrent(): Current {
    return Current(
        lastUpdatedEpoch = lastUpdatedEpoch,
        lastUpdated = lastUpdated,
        tempC = tempC,
        tempF = tempF,
        isDay = isDay,
        condition = condition.toCondition(),
        windMph = windMph,
        windKph = windKph,
        windDegree = windDegree,
        windDir = WindDirection.fromString(windDir) ?: WindDirection.X,
        pressureMb = pressureMb,
        pressureIn = pressureIn,
        precipMm = precipMm,
        precipIn = precipIn,
        humidity = humidity,
        cloud = cloud,
        feelslikeC = feelslikeC,
        feelslikeF = feelslikeF,
        visKm = visKm,
        visMiles = visMiles,
        uv = uv,
        gustMph = gustMph,
        gustKph = gustKph
    )
}

fun ConditionDto.toCondition(): Condition {
    return Condition(
        text = text,
        icon = icon,
        code = code
    )
}


//----

fun ForecastDto.toForecast(): Forecast {
    return Forecast(
        forecastDays = forecastDays.map { it.toForecastDay() }
    )
}

fun ForecastDayDto.toForecastDay(): ForecastDay {
    return ForecastDay(
        date = date,
        dateEpoch = dateEpoch,
        day = day.toDay(),
        astro = astro.toAstro(),
        hours = hours.map { it.toHour() }
    )
}

fun DayDto.toDay(): Day {
    return Day(
        maxTempC = maxTempC,
        maxTempF = maxTempF,
        minTempC = minTempC,
        minTempF = minTempF,
        avgTempC = avgTempC,
        avgTempF = avgTempF,
        maxWindMph = maxWindMph,
        maxWindKph = maxWindKph,
        totalPrecipMm = totalPrecipMm,
        totalPrecipIn = totalPrecipIn,
        totalSnowCm = totalSnowCm,
        avgVisibilityKm = avgVisKm,
        avgVisibilityMiles = avgVisMiles,
        avgHumidity = avgHumidity,
        willItRain = dailyWillItRain == 1,
        chanceOfRain = dailyChanceOfRain,
        willItSnow = dailyWillItSnow == 1,
        chanceOfSnow = dailyChanceOfSnow,
        condition = condition.toCondition(),
        uvIndex = uv
    )
}

fun AstroDto.toAstro(): Astro {
    return Astro(
        sunrise = sunrise,
        sunset = sunset,
        moonrise = moonrise,
        moonset = moonset,
        moonPhase = moonPhase,
        moonIllumination = moonIllumination,
        isMoonUp = isMoonUp == 1,
        isSunUp = isSunUp == 1
    )
}

fun HourDto.toHour(): Hour {
    return Hour(
        timeEpoch = timeEpoch,
        time = time,
        tempC = tempC,
        tempF = tempF,
        isDay = isDay == 1,
        condition = condition.toCondition(),
        windMph = windMph,
        windKph = windKph,
        windDegree = windDegree,
        windDirection = windDir,
        pressureMb = pressureMb,
        pressureIn = pressureIn,
        precipMm = precipMm,
        precipIn = precipIn,
        snowCm = snowCm,
        humidity = humidity,
        cloud = cloud,
        feelsLikeC = feelslikeC,
        feelsLikeF = feelslikeF,
        windChillC = windchillC,
        windChillF = windchillF,
        heatIndexC = heatindexC,
        heatIndexF = heatindexF,
        dewPointC = dewpointC,
        dewPointF = dewpointF,
        willItRain = willItRain == 1,
        chanceOfRain = chanceOfRain,
        willItSnow = willItSnow == 1,
        chanceOfSnow = chanceOfSnow,
        visibilityKm = visKm,
        visibilityMiles = visMiles,
        gustMph = gustMph,
        gustKph = gustKph,
        uvIndex = uv
    )
}

