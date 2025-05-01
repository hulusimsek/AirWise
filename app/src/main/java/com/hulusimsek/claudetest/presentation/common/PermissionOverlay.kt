package com.hulusimsek.claudetest.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun PermissionOverlay(
    onDismiss: () -> Unit,
    onRequestPermission: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.7f))
            .clickable(onClick = onDismiss),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .padding(16.dp)
                .widthIn(max = 400.dp)
                .clickable(onClick = {}),  // Kart tıklamalarını engelle
            elevation = 8.dp
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Konum İzni",
                    style = MaterialTheme.typography.h6
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Bulunduğunuz yerin hava durumunu otomatik göstermek için konum izni gerekiyor. İzin vermezseniz de uygulamayı kullanabilirsiniz.",
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("Daha Sonra")
                    }

                    Button(onClick = {
                        onRequestPermission()
                        onDismiss()
                    }) {
                        Text("İzin Ver")
                    }
                }
            }
        }
    }
}