package com.hulusimsek.claudetest.data.repository

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.LocationServices
import com.hulusimsek.claudetest.domain.repository.LocationRepository
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.Locale
import javax.inject.Inject
import kotlin.coroutines.resume

class LocationRepositoryImpl @Inject constructor(
    private val context: Context
) : LocationRepository {

    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    override suspend fun getCurrentLocation(): Location? = suspendCancellableCoroutine { cont ->
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ) {
            cont.resume(null)
            return@suspendCancellableCoroutine
        }

        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            cont.resume(location)
        }.addOnFailureListener {
            cont.resume(null)
        }
    }

    override suspend fun getCityName(location: Location): String? = suspendCancellableCoroutine { cont ->
        val geocoder = Geocoder(context, Locale.getDefault())

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            geocoder.getFromLocation(location.latitude, location.longitude, 1) { addresses ->
                // Daha fazla log ekleyin
                val size = addresses?.size ?: 0
                Log.d("konumtest", "Geocoder addresses size: $size")

                val locality = addresses.firstOrNull()?.locality
                val subAdminArea = addresses.firstOrNull()?.subAdminArea
                val adminArea = addresses.firstOrNull()?.adminArea

                Log.d("konumtest", "locality: $locality, subAdminArea: $subAdminArea, adminArea: $adminArea")

                // Önce locality, yoksa subAdminArea, o da yoksa adminArea kullan
                val cityName = locality ?: subAdminArea ?: adminArea
                cont.resume(cityName)
            }
        } else {
            try {
                @Suppress("DEPRECATION")
                val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
                val size = addresses?.size ?: 0
                Log.d("konumtest", "Geocoder addresses size: $size")

                val address = addresses?.firstOrNull()
                val locality = address?.locality
                val subAdminArea = address?.subAdminArea
                val adminArea = address?.adminArea

                Log.d("konumtest", "locality: $locality, subAdminArea: $subAdminArea, adminArea: $adminArea")

                // Önce locality, yoksa subAdminArea, o da yoksa adminArea kullan
                val cityName = locality ?: subAdminArea ?: adminArea
                cont.resume(cityName)
            } catch (e: Exception) {
                Log.e("konumtest", "Geocoder error: ${e.message}")
                cont.resume(null)
            }
        }
    }

}
