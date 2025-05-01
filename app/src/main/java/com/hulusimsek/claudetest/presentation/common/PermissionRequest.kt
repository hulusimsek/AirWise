package com.hulusimsek.claudetest.presentation.common

import android.Manifest
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState
import android.provider.Settings
import androidx.compose.foundation.layout.*
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PermissionRequest(permission: String, onPermissionGranted: () -> Unit) {
    val permissionState = rememberPermissionState(permission)
    val context = LocalContext.current

    when (permissionState.status) {
        PermissionStatus.Granted -> {
            // İzin verildiyse işlemi başlatıyoruz
            onPermissionGranted()
        }
        is PermissionStatus.Denied -> {
            // İzin reddedildiyse kullanıcıya açıklama yapıyoruz
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Konum izni gerekiyor. Lütfen izni verin.")
                Spacer(modifier = Modifier.height(16.dp))

                // Eğer kullanıcı daha önce izni reddetmişse ayarlar sayfasına yönlendiriyoruz
                if (!(permissionState.status as PermissionStatus.Denied).shouldShowRationale) {
                    Text(text = "İzni bir dahaki sefere sormak istemiyorsanız ayarlara gitmelisiniz.")
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = {
                            // Kullanıcıyı ayarlar sayfasına yönlendiriyoruz
                            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                                data = Uri.parse("package:${context.packageName}")
                            }
                            context.startActivity(intent)
                        }
                    ) {
                        Text(text = "Ayarlar")
                    }
                } else {
                    // Kullanıcıya izin isteme butonu sunuyoruz
                    Button(onClick = { permissionState.launchPermissionRequest() }) {
                        Text(text = "İzin Ver")
                    }
                }
            }
        }
    }
}