package com.hulusimsek.claudetest.presentation.weather.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.hulusimsek.claudetest.R
import com.hulusimsek.claudetest.domain.model.ForecastWeather
import com.hulusimsek.claudetest.domain.model.Weather
import com.hulusimsek.claudetest.presentation.common.extensions.getFloatDimen


@Composable
fun WeatherGlassCard(weather: ForecastWeather, formattedDate: String) {
    val context = LocalContext.current

    val cornerRadius = dimensionResource(id = R.dimen.corner_radius_large)
    val padding = dimensionResource(id = R.dimen.padding_large)
    val elevationZero = dimensionResource(id = R.dimen.elevation_zero)
    val spacerHeight = dimensionResource(id = R.dimen.spacer_height_small)
    val iconSize = dimensionResource(id = R.dimen.weather_icon_size)

    val fontSizeLocationAndDegree =
        dimensionResource(id = R.dimen.font_size_location_and_degree).value.sp
    val fontSizeCondition = dimensionResource(id = R.dimen.font_size_condition).value.sp
    val fontSizeLocalTime = dimensionResource(id = R.dimen.font_size_localtime).value.sp

    val backgroundAlpha = context.getFloatDimen(R.dimen.alpha_glass_background)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color.Transparent,
                shape = RoundedCornerShape(cornerRadius)
            )
    ) {
        Row (horizontalArrangement = Arrangement.Start, verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier
                    .padding(padding)
                    .height(120.dp)

            ) {
                AsyncImage(
                    model = "https:${weather.current.condition.icon}",
                    contentDescription = null,
                    modifier = Modifier.size(iconSize)
                )
            }
            Column(
                modifier = Modifier
                ) {


                Text(
                    text = weather.location.name,
                    fontSize = fontSizeLocationAndDegree,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(spacerHeight))

                Text(
                    text = "${weather.current.tempC}${stringResource(id = R.string.degree_icon)}",
                    fontSize = fontSizeLocationAndDegree,
                    fontWeight = FontWeight.Medium,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(spacerHeight))
                Text(
                    text = weather.current.condition.text,
                    fontSize = fontSizeCondition,
                    fontWeight = FontWeight.Medium,
                    color = Color.White
                )
                Text(
                    text = formattedDate,
                    fontSize = fontSizeLocalTime,
                    color = Color.LightGray
                )
            }
        }


    }
}