package com.hulusimsek.claudetest.domain.usecase

import android.util.Log
import com.hulusimsek.claudetest.domain.repository.LocationRepository
import javax.inject.Inject

class GetUserCityUseCase @Inject constructor(
    private val locationRepository: LocationRepository
) {
    suspend operator fun invoke(): List<String?>? {
        val location = locationRepository.getCurrentLocation()
        if(location == null){
            Log.e("konumtest", "location null geldi")
            return null
        }

        Log.d("konumtest", "Konum alındı: ${location.latitude}, ${location.longitude}")
        val cityName = locationRepository.getCityName(location)
        Log.d("konumtest", "Şehir adı: $cityName")

        val coordinates = "${location.latitude},${location.longitude}"
        return listOf(coordinates, cityName)
    }
}