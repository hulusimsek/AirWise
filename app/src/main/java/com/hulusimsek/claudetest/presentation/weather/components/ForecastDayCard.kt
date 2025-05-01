package com.hulusimsek.claudetest.presentation.weather.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.hulusimsek.claudetest.R
import com.hulusimsek.claudetest.domain.model.ForecastDay

@Composable
fun ForecastDayCard(
    day: ForecastDay,
    formatDate: (String) -> String
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .padding(horizontal = dimensionResource(id = R.dimen.padding_small))
            .fillMaxWidth()
            .background(color = Color.Transparent)
            .clickable { expanded = !expanded },
    ) {
        Column(modifier = Modifier
            .padding(dimensionResource(id = R.dimen.padding_medium))
            .background(color = Color.Transparent)) {
            Row(
                modifier = Modifier.background(color = Color.Transparent),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = formatDate("${day.date} 00:00"),
                    fontSize = dimensionResource(id = R.dimen.font_size_localtime).value.sp,
                    color = Color.White,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "${day.day.maxTempC.toInt()}${stringResource(id = R.string.degree_icon)} / ${day.day.minTempC.toInt()}${stringResource(id = R.string.degree_icon)}",
                    color = Color.White,
                )
                Spacer(Modifier.width(dimensionResource(id = R.dimen.elevation_standard)))
                AsyncImage(
                    model = "https:${day.day.condition.icon}",
                    contentDescription = null,
                    modifier = Modifier.size(dimensionResource(id = R.dimen.spacer_forecast))
                )
            }

            if (expanded) {
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_small)))
                HourlyForecastRow(hours = day.hours)
            }
        }
    }
}