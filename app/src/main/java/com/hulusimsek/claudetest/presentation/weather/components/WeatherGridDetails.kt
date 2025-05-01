package com.hulusimsek.claudetest.presentation.weather.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupPositionProvider
import androidx.compose.ui.window.PopupProperties
import com.hulusimsek.claudetest.R
import com.hulusimsek.claudetest.domain.model.ForecastWeather
import com.hulusimsek.claudetest.presentation.common.extensions.getFloatDimen


@Composable
fun WeatherGridDetails(weather: ForecastWeather) {
    val context = LocalContext.current
    val cornerRadius = dimensionResource(id = R.dimen.corner_radius_grid_card)
    val padding = dimensionResource(id = R.dimen.padding_grid_detail)
    val backgroundAlpha = context.getFloatDimen(R.dimen.alpha_grid_card_background)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color.White.copy(alpha = backgroundAlpha),
                shape = RoundedCornerShape(cornerRadius)
            ),
    ) {
        Column(modifier = Modifier.padding(padding)) {
            DetailGridItemWithInfo(
                stringResource(id = R.string.moisture),
                "${weather.current.humidity}%",
                stringResource(id = R.string.info_humidity)
            )
            DetailGridItemWithInfo(
                stringResource(id = R.string.wind),
                "${weather.current.windKph} km/s ${weather.current.windDir.symbol}", stringResource(id = R.string.info_wind)
            )

            DetailGridItemWithInfo(
                stringResource(id = R.string.pressure),
                "${weather.current.pressureMb} mb",
                stringResource(id = R.string.info_pressure)
            )
            DetailGridItemWithInfo(stringResource(id = R.string.uv),
                "${weather.current.uv}",
                stringResource(id = R.string.info_uv))

        }
    }
}


@Composable
fun DetailGridItemWithInfo(
    label: String,
    value: String,
    info: String
) {
    var showTooltip by remember { mutableStateOf(false) }
    val verticalPadding = dimensionResource(id = R.dimen.padding_grid_item_vertical)
    val horizontalPadding = dimensionResource(id = R.dimen.padding_grid_item_horizontal)

    // Store icon's position for popup positioning
    var iconBounds by remember { mutableStateOf<Rect?>(null) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = verticalPadding),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            color = Color.White,
        )

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = value,
                color = Color.White,
                modifier = Modifier.padding(horizontal = horizontalPadding)
            )

            // Info icon with position tracking
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = "Info icon",
                modifier = Modifier
                    .size(16.dp)
                    .onGloballyPositioned { coordinates ->
                        // Store the icon's position in the window
                        iconBounds = coordinates.boundsInRoot()
                    }
                    .clickable {
                        showTooltip = !showTooltip
                    },
                tint = Color.Gray
            )
        }
    }

    // Show tooltip as a popup positioned relative to the icon
    if (showTooltip && iconBounds != null) {
        InfoTooltip(
            text = info,
            iconBounds = iconBounds!!,
            onDismiss = { showTooltip = false }
        )
    }
}

@Composable
fun InfoTooltip(
    text: String,
    iconBounds: Rect,
    onDismiss: () -> Unit
) {
    val density = LocalDensity.current
    val tooltipWidth = 200.dp

    // Create custom position provider based on the icon's position
    val popupPositionProvider = remember(iconBounds) {
        object : PopupPositionProvider {
            override fun calculatePosition(
                anchorBounds: IntRect,
                windowSize: IntSize,
                layoutDirection: androidx.compose.ui.unit.LayoutDirection,
                popupContentSize: IntSize
            ): IntOffset {
                // Convert icon bounds to Int
                val iconX = iconBounds.left.toInt()
                val iconY = iconBounds.top.toInt()

                // Calculate tooltip position (centered under the icon)
                val x = iconX - popupContentSize.width + 80 // Align with the icon
                // Position above the icon instead of below
                val y = iconY - popupContentSize.height + 80 // 10px above the icon

                return IntOffset(x, y)
            }
        }
    }

    Popup(
        popupPositionProvider = popupPositionProvider,
        properties = PopupProperties(
            focusable = true,
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        ),
        onDismissRequest = onDismiss
    ) {
        // Tooltip content
        Column {
            // Card containing tooltip text
            Card(
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                colors = CardDefaults.cardColors(containerColor = Color.DarkGray),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.width(tooltipWidth)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(
                        text = text,
                        color = Color.White,
                        fontSize = 12.sp
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Text(
                            text = stringResource(id = R.string.ok),
                            color = Color.Cyan,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .clickable { onDismiss() }
                                .padding(4.dp)
                        )
                    }
                }
            }

            // Triangle pointing to the info icon (now positioned below the card)
            Canvas(
                modifier = Modifier
                    .size(12.dp)
                    .align(Alignment.End)
                    .offset(x = (-12).dp, y = 0.dp)
            ) {
                val path = Path().apply {
                    moveTo(0f, 0f)
                    lineTo(size.width / 2, size.height)
                    lineTo(size.width, 0f)
                    close()
                }
                drawPath(path, color = Color.DarkGray)
            }
        }
    }
}
