package com.hailong.floralens.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hailong.floralens.R

@Composable
fun MainScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "🌿 Floralens",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        // 占位图（确保 res/drawable/ic_flower.xml 或 ic_launcher 存在）
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground), // 或换成你自己的图
            contentDescription = "Flower Icon",
            modifier = Modifier.padding(top = 48.dp)
        )

        Text(
            text = "识别身边的植物，发现自然之美",
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(top = 24.dp)
        )

        Button(
            onClick = { /* TODO: 启动相机或选择图片 */ },
            modifier = Modifier.padding(top = 48.dp)
        ) {
            Text("开始识别")
        }
    }
}