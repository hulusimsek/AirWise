package com.hulusimsek.claudetest.presentation.weather

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.hulusimsek.claudetest.R
import com.hulusimsek.claudetest.presentation.common.extensions.getFloatDimen
import com.hulusimsek.claudetest.presentation.common.getBackgroundImage
import com.hulusimsek.claudetest.presentation.weather.components.ForecastDayList
import com.hulusimsek.claudetest.presentation.weather.components.SearchBar
import com.hulusimsek.claudetest.presentation.weather.components.WeatherGlassCard
import com.hulusimsek.claudetest.presentation.weather.components.WeatherGridDetails
import com.hulusimsek.claudetest.presentation.weather.components.WeatherTipBox

@SuppressLint("ServiceCast")
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun WeatherScreen(
    viewModel: WeatherViewModel
) {
    val state by viewModel.forecastState.collectAsState()
    val userCity by viewModel.userCity.collectAsState()
    var searchQuery by remember {
        mutableStateOf(userCity ?: "")
    }
    val selectedDay by viewModel.selectedForecastDay.collectAsState()
    val context = LocalContext.current

    // userCity değiştiğinde searchQuery'yi güncelle
//    LaunchedEffect(userCity) {
//        userCity?.let {
//            searchQuery = it
//        }
//    }

    val padding = dimensionResource(id = R.dimen.padding_large)
    val backgroundAlpha = context.getFloatDimen(R.dimen.alpha_home_screen_background)

    val backgroundRes = remember(state.forecastWeather) {
        val condition = state.forecastWeather?.current?.condition?.text ?: ""
        getBackgroundImage(context, condition)
    }

    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    Box(modifier = Modifier
        .fillMaxSize()
        .pointerInput(Unit) {
            // Her tıklamada focus'u temizle
            detectTapGestures(onTap = {
                focusManager.clearFocus()
                keyboardController?.hide()
            })
        }) {
        Image(
            painter = painterResource(id = backgroundRes),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Blur + semi-transparent overlay
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = backgroundAlpha))
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            verticalArrangement = Arrangement.spacedBy(padding)
        ) {
            // 🔍 SearchBar
            SearchBar(
                query = searchQuery,
                onQueryChange = { searchQuery = it },
                onSearch = { viewModel.getForecastWeather(it) }
            )

            state.forecastWeather?.let { weather ->
                WeatherGlassCard(weather, state.formattedDate)
                WeatherGridDetails(weather)
                WeatherTipBox(state.tip)
                state.forecastWeather?.forecast?.forecastDays.let { days ->
                    ForecastDayList(forecastDays = days ?: listOf(), {viewModel.formatDate(it)})
                    //WeeklyForecastRow(forecastDays = days ?: listOf(), onDaySelected = { viewModel.selectForecastDay(it) })
                }
            }

            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    color = MaterialTheme.colors.primary
                )
            }

            state.error?.let {
                Text(
                    text = it,
                    color = Color.Red,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}


