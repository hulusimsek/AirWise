package com.hulusimsek.claudetest.presentation.weather.components

// Presentation katmanı (UI)

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hulusimsek.claudetest.R
import com.hulusimsek.claudetest.presentation.common.extensions.getFloatDimen


@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit
) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }

    val context = LocalContext.current
    val backgroundAlpha = context.getFloatDimen(R.dimen.alpha_glass_background)


    // Ekranda boş bir alana tıklayınca focus'ı kaybettirir
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = dimensionResource(id = R.dimen.padding_grid_item_horizontal))

    ) {
        TextField(
            value = query,
            onValueChange = { onQueryChange(it) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = "Location Icon",
                    tint = Color.White
                )
            },
            placeholder = { Text(stringResource(id = R.string.city_name), color = Color.White) },
            modifier = Modifier
                .fillMaxWidth()
                .height(dimensionResource(id = R.dimen.searchbar_height))
                .background(
                    Color.White.copy(alpha = backgroundAlpha),
                    shape = RoundedCornerShape(dimensionResource(id = R.dimen.corner_radius_grid_card))
                )
                .focusRequester(focusRequester),
            textStyle = TextStyle(fontSize = dimensionResource(id = R.dimen.font_size_searchbar).value.sp),
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    keyboardController?.hide()
                    focusManager.clearFocus()
                    onSearch(query)
                }
            ),
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.White,
                backgroundColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )
    }
}