package com.hulusimsek.claudetest

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.Modifier
import com.hulusimsek.claudetest.presentation.weather.WeatherScreen
import dagger.hilt.android.AndroidEntryPoint
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import com.hulusimsek.claudetest.presentation.permission.LocationPermissionHandler
import com.hulusimsek.claudetest.presentation.weather.WeatherViewModel
import com.hulusimsek.claudetest.ui.theme.ClaudeTestTheme
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var locationPermissionHandler: LocationPermissionHandler

    private val weatherViewModel: WeatherViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Konum izinleri ve ayarları için register işlemi
        Log.e("izinler", "mainActivity - onCreate - creatlendi")
        locationPermissionHandler.register(
            activity = this,
            onPermissionResult = { isGranted ->
                if (isGranted) {
                    Log.e("izinler", "mainActivity - onCreate - izin verildi")
                    locationPermissionHandler.checkLocationSettings(
                        context = this,
                        onSettingsOk = {
                            Log.e("izinler", "mainActivity - onCreate - izin verildi - ayarlar ok")
                            weatherViewModel.fetchUserCity()
                        },
                        onSettingsNotOk = {
                            // Ayarlar açılmadığında varsayılan şehri kullan
                            Log.e("izinler", "mainActivity - onCreate - izin verildi - ayarlar ok değil")
                            weatherViewModel.getForecastWeather("Istanbul")
                        }
                    )
                } else {
                    // İzinler verilmediğinde varsayılan şehri kullan
                    Log.e("izinler", "mainActivity - onCreate - izin verilmedi")
                    weatherViewModel.getForecastWeather("Istanbul")
                }
            },
            onLocationSettingsResult = { isEnabled ->
                if (isEnabled) {
                    Log.e("izinler", "mainActivity - onCreate - ayarlar 2 ok")
                    weatherViewModel.fetchUserCity()
                } else {
                    // Konum ayarları etkinleştirilmediğinde varsayılan şehri kullan
                    Log.e("izinler", "mainActivity - onCreate - ayarlar 2 ok değil")
                    weatherViewModel.getForecastWeather("Istanbul")
                }
            }
        )

        setContent {
            ClaudeTestTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    WeatherScreen(weatherViewModel)
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()

        // İzin kontrolleri
        locationPermissionHandler.checkLocationPermissions(
            context = this,
            onPermissionGranted = {
                Log.e("izinler", "mainActivity - onStart - izin verildi")
                locationPermissionHandler.checkLocationSettings(
                    context = this,
                    onSettingsOk = {
                        Log.e("izinler", "mainActivity - onStart - izin verildi - ayarlar ok")
                        weatherViewModel.fetchUserCity()
                    },
                    onSettingsNotOk = {
                        // Ayarlar açılmadığında varsayılan şehri kullan
                        Log.e("izinler", "mainActivity - onStart - izin verildi - ayarlar ok değil")
                        weatherViewModel.getForecastWeather("Istanbul")
                    }
                )
            },
            onPermissionDenied = {
                // İzinler verilmediğinde varsayılan şehri kullan
                Log.e("izinler", "mainActivity - onStart - izin verilmedi")
                weatherViewModel.getForecastWeather("Istanbul")
            }
        )
    }
}
