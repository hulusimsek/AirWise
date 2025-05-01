package com.hulusimsek.claudetest.presentation.permission

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.IntentSender
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.Priority
import javax.inject.Inject

/**
 * Konum izinlerini ve ayarlarını yönetmek için yardımcı sınıf
 */
class LocationPermissionHandler @Inject constructor() {

    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>
    private lateinit var locationSettingsLauncher: ActivityResultLauncher<IntentSenderRequest>

    /**
     * Activity oluşturulduğunda çağrılmalıdır
     */
    fun register(
        activity: ComponentActivity,
        onPermissionResult: (Boolean) -> Unit,
        onLocationSettingsResult: (Boolean) -> Unit
    ) {
        Log.e("izinler", "LocationPermissionHandler - register başladı")
        permissionLauncher = activity.registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            Log.e("izinler", "LocationPermissionHandler - register - permissionLauncher")
            val allGranted = permissions.entries.all { it.value }
            onPermissionResult(allGranted)
            Log.e("izinler", "LocationPermissionHandler - register - permissionLauncher 2")
        }
        Log.e("izinler", "LocationPermissionHandler - permissionLauncher:  " + permissionLauncher.toString())

        locationSettingsLauncher = activity.registerForActivityResult(
            ActivityResultContracts.StartIntentSenderForResult()
        ) { result ->
            Log.e("izinler", "LocationPermissionHandler - register - locationSettingsLauncher")
            onLocationSettingsResult(result.resultCode == Activity.RESULT_OK)
            Log.e("izinler", "LocationPermissionHandler - register - locationSettingsLauncher 2")
        }
        Log.e("izinler", "LocationPermissionHandler - locationSettingsLauncher:  " + locationSettingsLauncher.toString())

    }

    /**
     * Konum izinlerini kontrol eder
     */
    fun checkLocationPermissions(
        context: Context,
        onPermissionGranted: () -> Unit,
        onPermissionDenied: () -> Unit
    ) {
        Log.e("izinler", "LocationPermissionHandler - checkLocationPermissions")
        when {
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                onPermissionGranted()
            }
            else -> {
                try {
                    permissionLauncher.launch(
                        arrayOf(
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        )
                    )
                } catch (e: Exception) {
                    onPermissionDenied()
                }
            }
        }
    }

    /**
     * Konum ayarlarını kontrol eder
     */
    fun checkLocationSettings(
        context: Context,
        onSettingsOk: () -> Unit,
        onSettingsNotOk: () -> Unit
    ) {
        Log.e("izinler", "LocationPermissionHandler - checkLocationSettings")
        val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 10000)
            .setWaitForAccurateLocation(false)
            .build()

        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)

        val client = LocationServices.getSettingsClient(context)
        val task = client.checkLocationSettings(builder.build())

        task.addOnSuccessListener {
            onSettingsOk()
        }

        task.addOnFailureListener { exception ->
            if (exception is ResolvableApiException) {
                try {
                    val intentSenderRequest = IntentSenderRequest.Builder(exception.resolution).build()
                    locationSettingsLauncher.launch(intentSenderRequest)
                } catch (sendEx: IntentSender.SendIntentException) {
                    onSettingsNotOk()
                }
            } else {
                onSettingsNotOk()
            }
        }
    }
}
