package com.hailong.floralens

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import com.hailong.floralens.ui.MainScreen
import com.hailong.floralens.ui.screen.SplashScreen
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(TAG, "Floralens 应用启动")

        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppContent()
                }
            }
        }

        Log.d(TAG, "Compose 界面初始化完成")
    }
}

@Composable
fun AppContent() {
    // 控制是否显示启动屏
    var showSplash by remember { mutableStateOf(true) }

    // 启动屏透明度动画
    val splashAlpha = remember { Animatable(1f) }
    // 主界面透明度动画
    val mainScreenAlpha = remember { Animatable(0f) }

    // 背景色（与系统栏一致）
    val backgroundColor = Color.White

    // 标记主界面是否已准备好（避免白屏）
    var isMainScreenReady by remember { mutableStateOf(false) }

    // 预加载主界面（可在此添加初始化逻辑）
    LaunchedEffect(Unit) {
        isMainScreenReady = true
    }

    // 当启动屏结束时，执行淡出/淡入动画
    // 当启动屏结束时，执行淡出/淡入动画
    LaunchedEffect(showSplash) {
        if (!showSplash) {
            launch { splashAlpha.animateTo(0f, tween(durationMillis = 400)) }
            launch { mainScreenAlpha.animateTo(1f, tween(durationMillis = 400)) }
        }
    }

    Box(modifier = Modifier.fillMaxSize().background(backgroundColor)) {
        // 主界面：提前组合但初始透明
        if (isMainScreenReady) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(mainScreenAlpha.value)
                    .graphicsLayer() // 启用硬件加速
            ) {
                MainScreen()
            }
        }

        // 启动屏：覆盖在上方，随动画隐藏
        Box(
            modifier = Modifier
                .fillMaxSize()
                .alpha(splashAlpha.value)
        ) {
            if (showSplash) {
                SplashScreen(onAnimationComplete = { showSplash = false })
            }
        }
    }
}