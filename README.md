# AirWise

## Weather Forecast App ☀️🌧️

A modern and clean weather forecast Android application built with **Kotlin** and **Jetpack Compose**, following **MVVM** and **Clean Architecture** principles. The app fetches real-time weather data using the [WeatherAPI](https://www.weatherapi.com/) and provides current weather information, forecasts, and personalized recommendations based on the conditions.

## 🌟 Features

- 📍 **Location-based weather**
  - Requests location permission on first launch.
  - Fetches current weather based on the user's coordinates.
  - Defaults to a preset location if permission is denied.

- 🌤️ **Current Weather Details**
  - Temperature
  - Weather condition
  - Humidity
  - Wind speed
  - Atmospheric pressure
  - UV index
  - Dynamic background images based on weather condition
  - Suggestions based on the weather (e.g. "Carry an umbrella")

- 📅 **3-Day Forecast**
  - Displays forecast for today and the next two days.
  - Hourly breakdown for each day.
  - Shows high and low temperatures.

## 🧰 Tech Stack

- **Language & UI**
  - Kotlin
  - Jetpack Compose

- **Architecture**
  - MVVM
  - Clean Architecture

- **Dependency Injection**
  - Dagger-Hilt

- **Networking**
  - Retrofit
  - Gson

- **Asynchronous Programming**
  - Kotlin Coroutines

- **Image Loading**
  - Coil

- **Permissions**
  - Accompanist Permissions

- **Location Services**
  - Google Play Services Location

## 📦 Dependencies

```kotlin
// Retrofit
implementation("com.squareup.retrofit2:retrofit:2.9.0")
implementation("com.squareup.retrofit2:converter-gson:2.9.0")

// Jetpack Compose
implementation("androidx.compose.ui:ui:1.5.4")
implementation("androidx.compose.material:material:1.5.4")
implementation("androidx.compose.ui:ui-tooling-preview:1.5.4")
implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")

// Coil for image loading
implementation("io.coil-kt:coil-compose:2.5.0")

// Dagger-Hilt
implementation("com.google.dagger:hilt-android:2.49")
kapt("com.google.dagger:hilt-android-compiler:2.49")
kapt("androidx.hilt:hilt-compiler:1.2.0")
implementation("androidx.hilt:hilt-navigation-compose:1.2.0")
implementation("androidx.hilt:hilt-work:1.2.0")

// Kotlin Coroutines
implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

// Location
implementation("com.google.android.gms:play-services-location:21.0.1")

// Permissions
implementation("com.google.accompanist:accompanist-permissions:0.25.0")
```

## 🔑 API Key Setup

To fetch weather data, you need an API key from [WeatherAPI](https://www.weatherapi.com/).

1. Get your API key from WeatherAPI.
2. Open your project's `local.properties` file.
3. Add the following line:

```properties
WEATHER_API_KEY=your_api_key_here
```

4. Make sure your build system (e.g., `build.gradle`) is configured to read this key.

> ⚠️ Do **not** commit your `local.properties` file to version control.

## 🔌 API

Weather data is provided by [WeatherAPI](https://www.weatherapi.com/). You need an API key to use the service.

## 🛠️ Project Structure

```
- data/
  - remote/
  - repository/
- domain/
  - model/
  - usecase/
- presentation/
  - screens/
  - components/
  - viewmodel/
- di/
- utils/
```

## 🚀 Getting Started

1. Clone the repository:
   ```bash
   git clone https://github.com/hulusimsek/AirWise.git
   ```

2. Add your API key from [WeatherAPI](https://www.weatherapi.com/) in the appropriate place.

3. Run the project on an Android device or emulator.

## 📸 Screenshots
<img src="https://github.com/user-attachments/assets/71ef8840-e739-4034-8f4a-aae373155915" alt="1" width="300"/>
<img src="https://github.com/user-attachments/assets/0804cc9e-ea89-44a5-a853-34859591a778" alt="2" width="300"/>


