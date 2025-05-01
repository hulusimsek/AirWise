package com.hulusimsek.claudetest.presentation.weather.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.hulusimsek.claudetest.R
import com.hulusimsek.claudetest.domain.model.Hour

@Composable
fun HourlyForecastRow(hours: List<Hour>) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(id = R.dimen.padding_grid_detail)),
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
    ) {
        items(hours) { hour ->
            Column(
                modifier = Modifier
                    .width(dimensionResource(id = R.dimen.forecast_row_width))
                    .background(Color(0x44212121), RoundedCornerShape(dimensionResource(id = R.dimen.corner_radius_medium)))
                    .padding(dimensionResource(id = R.dimen.padding_small)),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = hour.time.substring(11, 16), // "15:00"
                    color = Color.White,
                    fontSize = dimensionResource(id = R.dimen.font_size_forecast).value.sp
                )
                AsyncImage(
                    model = "https:${hour.condition.icon}",
                    contentDescription = null,
                    modifier = Modifier.size(dimensionResource(id = R.dimen.forecast_icon_size))
                )
                Text(
                    text = "${hour.tempC.toInt()}°",
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}