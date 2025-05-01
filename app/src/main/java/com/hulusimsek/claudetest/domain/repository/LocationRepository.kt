package com.hulusimsek.claudetest.domain.repository

import android.location.Location

interface LocationRepository {
    suspend fun getCurrentLocation(): Location?
    suspend fun getCityName(location: Location): String?
}