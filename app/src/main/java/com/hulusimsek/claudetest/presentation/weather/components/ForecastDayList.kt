package com.hulusimsek.claudetest.presentation.weather.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import com.hulusimsek.claudetest.R
import com.hulusimsek.claudetest.domain.model.ForecastDay
import com.hulusimsek.claudetest.presentation.common.extensions.getFloatDimen

@Composable
fun ForecastDayList(
    forecastDays: List<ForecastDay>,
    formatDate: (String) -> String,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val backgroundAlpha = context.getFloatDimen(R.dimen.alpha_grid_card_background)
    val cornerRadius = dimensionResource(id = R.dimen.corner_radius_grid_card)
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = Color.White.copy(alpha = backgroundAlpha),
                shape = RoundedCornerShape(cornerRadius)
            )
    ) {
        items(items = forecastDays) { day ->
            ForecastDayCard(day, formatDate)
        }
    }
}