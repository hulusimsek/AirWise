package com.hulusimsek.claudetest.presentation.weather

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hulusimsek.claudetest.R
import com.hulusimsek.claudetest.domain.common.ResourceProvider
import com.hulusimsek.claudetest.domain.model.ForecastDay
import com.hulusimsek.claudetest.domain.usecase.FormatDateUseCase
import com.hulusimsek.claudetest.domain.usecase.GetForecastWeatherUseCase
import com.hulusimsek.claudetest.domain.usecase.GetUserCityUseCase
import com.hulusimsek.claudetest.domain.usecase.GetWeatherTipUseCase
import com.hulusimsek.claudetest.domain.usecase.GetWeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val getWeatherUseCase: GetWeatherUseCase,
    private val getForecastWeatherUseCase: GetForecastWeatherUseCase,
    private val getWeatherTipUseCase: GetWeatherTipUseCase,
    private val resourceProvider: ResourceProvider,
    private val getUserCityUseCase: GetUserCityUseCase,
    private val formatDateUseCase: FormatDateUseCase

) : ViewModel() {

    //private val _state = MutableStateFlow(WeatherState())
    //val state: StateFlow<WeatherState> = _state.asStateFlow()

    private val _forecastState = MutableStateFlow(ForecastWeatherState())
    val forecastState: StateFlow<ForecastWeatherState> = _forecastState.asStateFlow()

    private val _userCity = MutableStateFlow<String?>(null)
    val userCity: StateFlow<String?> = _userCity

    private val _selectedForecastDay = MutableStateFlow<ForecastDay?>(null)
    val selectedForecastDay: StateFlow<ForecastDay?> = _selectedForecastDay.asStateFlow()


    fun fetchUserCity() {
        viewModelScope.launch {
            try {
                _forecastState.update { it.copy(isLoading = true) }

                val result = getUserCityUseCase()
                Log.d("konumtest", "UseCase sonucu: $result")

                val cityName = result?.getOrNull(1)

                if (cityName.isNullOrBlank()) {
                    Log.e("konumtest", "Şehir adı alınamadı, varsayılan şehir kullanılıyor")
                    _userCity.value = "sivas" // varsayılan şehir
                } else {
                    _userCity.value = cityName
                }

                val coordinates = result?.getOrNull(0)
                if(coordinates.isNullOrBlank()) {
                    getForecastWeather(_userCity.value!!)
                }
                else {
                    getForecastWeather(coordinates)
                }


            } catch (e: Exception) {
                Log.e("konumtest", "Hata: ${e.message}", e)
                _forecastState.update { it.copy(
                    isLoading = false,
                    error = e.message ?: resourceProvider.getString(R.string.error_message)
                ) }

                // Hata durumunda da varsayılan şehri kullan
                _userCity.value = "Istanbul"
                getForecastWeather(_userCity.value!!)
            }
        }
    }




//    fun getWeather(location: String) {
//        viewModelScope.launch {
//            _state.update { it.copy(isLoading = true, error = null) }
//
//            getWeatherUseCase(location)
//                .fold(
//                    onSuccess = { weather ->
//                        val tip = getWeatherTipUseCase(weather.current.condition.text)
//                        val formattedDate = formatDateUseCase(weather.location.localtime)
//                        _state.update { it.copy(
//                            isLoading = false,
//                            weather = weather,
//                            formattedDate = formattedDate,
//                            tip = tip
//                        ) }
//                    },
//                    onFailure = { exception ->
//                        _state.update { it.copy(
//                            isLoading = false,
//                            error = exception.message ?: resourceProvider.getString(R.string.error_message)
//                        ) }
//                    }
//                )
//        }
//    }

    fun getForecastWeather(location: String) {
        viewModelScope.launch {
            _forecastState.update { it.copy(isLoading = true, error = null) }

            getForecastWeatherUseCase(location)
                .fold(
                    onSuccess = { weather ->
                        val tip = getWeatherTipUseCase(weather.current.condition.text)
                        val formattedDate = formatDateUseCase(weather.location.localtime)
                        _forecastState.update { it.copy(
                            isLoading = false,
                            forecastWeather = weather,
                            formattedDate = formattedDate,
                            tip = tip
                        ) }
                    },
                    onFailure = { exception ->
                        _forecastState.update { it.copy(
                            isLoading = false,
                            error = exception.message ?: resourceProvider.getString(R.string.error_message)
                        ) }
                    }
                )
        }
    }
    fun selectForecastDay(day: ForecastDay) {
        _selectedForecastDay.value = day
    }
    fun formatDate(date: String): String {
        return formatDateUseCase(date)
    }
}
