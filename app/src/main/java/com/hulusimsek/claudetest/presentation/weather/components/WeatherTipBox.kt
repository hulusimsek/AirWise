package com.hulusimsek.claudetest.presentation.weather.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hulusimsek.claudetest.R
import com.hulusimsek.claudetest.presentation.common.extensions.getFloatDimen

@Composable
fun WeatherTipBox(tip: String) {
    val context = LocalContext.current
    val cornerRadius = dimensionResource(id = R.dimen.corner_radius_small)
    val padding = dimensionResource(id = R.dimen.corner_radius_medium)
    val fontSize = dimensionResource(id = R.dimen.font_size_tip_box).value.sp
    val backgroundAlpha = context.getFloatDimen(R.dimen.alpha_tip_box_background)

    Text(
        text = tip,
        color = Color.White,
        fontSize = fontSize,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White.copy(alpha = backgroundAlpha), shape = RoundedCornerShape(cornerRadius))
            .padding(padding)
    )
}