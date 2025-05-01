package com.hulusimsek.claudetest.di

import android.app.Application
import android.content.Context
import com.hulusimsek.claudetest.data.common.DateFormatterImpl
import com.hulusimsek.claudetest.data.common.DefaultResourceProvider
import com.hulusimsek.claudetest.data.common.LanguageProviderImpl
import com.hulusimsek.claudetest.data.remote.ApiClient
import com.hulusimsek.claudetest.data.remote.WeatherApi
import com.hulusimsek.claudetest.data.repository.LocationRepositoryImpl
import com.hulusimsek.claudetest.data.repository.WeatherRepositoryImpl
import com.hulusimsek.claudetest.domain.common.DateFormatter
import com.hulusimsek.claudetest.domain.common.LanguageProvider
import com.hulusimsek.claudetest.domain.common.ResourceProvider
import com.hulusimsek.claudetest.domain.repository.LocationRepository
import com.hulusimsek.claudetest.domain.repository.WeatherRepository
import com.hulusimsek.claudetest.domain.usecase.FormatDateUseCase
import com.hulusimsek.claudetest.domain.usecase.GetForecastWeatherUseCase
import com.hulusimsek.claudetest.domain.usecase.GetWeatherTipUseCase
import com.hulusimsek.claudetest.domain.usecase.GetWeatherUseCase
import com.hulusimsek.claudetest.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    // API
    @Singleton
    @Provides
    fun provideWeatherApi(): WeatherApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(WeatherApi::class.java)
    }

    // Repository
    @Singleton
    @Provides
    fun provideWeatherRepository(
        api: WeatherApi,
        languageProvider: LanguageProvider,
        resourceProvider: ResourceProvider
    ) = WeatherRepositoryImpl(api, languageProvider, resourceProvider) as WeatherRepository

    @Provides
    @Singleton
    fun provideResourceProvider(
        @ApplicationContext context: Context
    ): ResourceProvider = DefaultResourceProvider(context)

    // UseCase
    @Singleton
    @Provides
    fun provideGetWeatherUseCase(
        repository: WeatherRepository
    ): GetWeatherUseCase {
        return GetWeatherUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideGetForecastWeatherUseCase(
        repository: WeatherRepository
    ): GetForecastWeatherUseCase {
        return GetForecastWeatherUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideGetWeatherTipUseCase(
        resourceProvider: ResourceProvider
    ): GetWeatherTipUseCase {
        return GetWeatherTipUseCase(resourceProvider)
    }

    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }

    @Provides
    @Singleton
    fun provideLanguageProvider(@ApplicationContext context: Context): LanguageProvider {
        return LanguageProviderImpl(context)
    }

    @Provides
    @Singleton
    fun provideLocationRepository(
        @ApplicationContext context: Context
    ): LocationRepository = LocationRepositoryImpl(context)

    @Provides
    @Singleton
    fun provideDateFormatter(
        languageProvider: LanguageProvider
    ): DateFormatter = DateFormatterImpl(languageProvider)

    @Singleton
    @Provides
    fun provideFormatDateUseCase(
        dateFormatter: DateFormatter
    ): FormatDateUseCase {
        return FormatDateUseCase(dateFormatter)
    }
}
