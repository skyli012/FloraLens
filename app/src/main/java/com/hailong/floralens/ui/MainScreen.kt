// ui/MainScreen.kt
package com.hailong.floralens.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.hailong.floralens.ui.components.FloraLensBottomNavBar
import com.hailong.floralens.ui.components.FloraLensTopBar
import com.hailong.floralens.ui.content.HomeContent
import com.hailong.floralens.ui.content.ProfileContent
import com.hailong.floralens.ui.content.ScanContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    // 使用 route 而不是 index（更清晰）
    var currentRoute by remember { mutableStateOf("home") }

    Scaffold(
        topBar = {
            FloraLensTopBar(
                onProfileClick = {
                    // 可跳转 Profile
                }
            )
        },
        bottomBar = {
            FloraLensBottomNavBar(
                currentRoute = currentRoute,
                onNavigateTo = { route -> currentRoute = route },
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // 根据 currentRoute 显示不同内容
            when (currentRoute) {
                "home" -> HomeContent()
                "scan" -> ScanContent()
                "profile" -> ProfileContent()
            }
        }
    }
}