package com.hulusimsek.claudetest.data.repository

import android.util.Log
import com.hulusimsek.claudetest.BuildConfig
import com.hulusimsek.claudetest.R
import com.hulusimsek.claudetest.data.mapper.toForecastWeather
import com.hulusimsek.claudetest.data.model.WeatherResponseDto
import com.hulusimsek.claudetest.data.remote.ApiClient
import com.hulusimsek.claudetest.domain.model.Weather
import com.hulusimsek.claudetest.domain.repository.WeatherRepository
import com.hulusimsek.claudetest.data.mapper.toWeather
import com.hulusimsek.claudetest.data.remote.WeatherApi
import com.hulusimsek.claudetest.domain.common.LanguageProvider
import com.hulusimsek.claudetest.domain.common.ResourceProvider
import com.hulusimsek.claudetest.domain.model.ForecastWeather
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val api: WeatherApi,
    private val languageProvider: LanguageProvider,
    private val resourceProvider: ResourceProvider
) : WeatherRepository {
    private val apiKey = BuildConfig.WEATHER_API_KEY

    override suspend fun getWeather(location: String): Result<Weather> {
        return try {
            withContext(Dispatchers.IO) {
                val deviceLang = languageProvider.getDeviceLanguage()
                val supported = languageProvider.getSupportedLanguages()
                Log.e("desteklenenDiller", supported.toString())
                val apiLang = if (deviceLang in supported) deviceLang else "en"
                val response = api.getCurrentWeather(
                    location = location,
                    language = apiLang,
                    apiKey = apiKey
                )

                if (response.error != null) {
                        // Hata durumunu Result.failure ile döndürüyoruz
                        Result.failure(
                            Exception("${resourceProvider.getString(R.string.http_error_message)}: ${response.error.code} - ${response.error.message}")
                        )
                } else {
                    // Başarı durumunda veriyi döndürüyoruz
                    Result.success(response.toWeather())
                }
            }
        } catch (e: HttpException) {
            // HTTP hatası (4xx ya da 5xx)
            val errorBody = e.response()?.errorBody()?.string() // API hata mesajı (JSON vs)

            Result.failure(Exception("${resourceProvider.getString(R.string.http_error_message)}: ${e.code()} - ${errorBody}"))
        } catch (e: IOException) {
            // Ağ hatası (internet bağlantısı vs.)
            Result.failure(Exception(resourceProvider.getString(R.string.network_error_message)))
        } catch (e: Exception) {
            // Diğer genel hatalar
            Result.failure(Exception("${resourceProvider.getString(R.string.error_message)}: ${e.localizedMessage}"))
        }
    }

    override suspend fun getForecastWeather(location: String): Result<ForecastWeather> {
        return try {
            withContext(Dispatchers.IO) {
                val deviceLang = languageProvider.getDeviceLanguage()
                val supported = languageProvider.getSupportedLanguages()
                Log.e("desteklenenDiller", supported.toString())
                val apiLang = if (deviceLang in supported) deviceLang else "en"
                val response = api.getForecastWeather(
                    location = location,
                    language = apiLang,
                    day = "5",
                    apiKey = apiKey
                )

                if (response.error != null) {
                    // Hata durumunu Result.failure ile döndürüyoruz
                    Result.failure(
                        Exception("${resourceProvider.getString(R.string.http_error_message)}: ${response.error.code} - ${response.error.message}")
                    )
                } else {
                    // Başarı durumunda veriyi döndürüyoruz
                    Result.success(response.toForecastWeather())
                }
            }
        } catch (e: HttpException) {
            // HTTP hatası (4xx ya da 5xx)
            val errorBody = e.response()?.errorBody()?.string() // API hata mesajı (JSON vs)

            Result.failure(Exception("${resourceProvider.getString(R.string.http_error_message)}: ${e.code()} - ${errorBody}"))
        } catch (e: IOException) {
            // Ağ hatası (internet bağlantısı vs.)
            Result.failure(Exception(resourceProvider.getString(R.string.network_error_message)))
        } catch (e: Exception) {
            // Diğer genel hatalar
            Result.failure(Exception("${resourceProvider.getString(R.string.error_message)}: ${e.localizedMessage}"))
        }
    }
}
