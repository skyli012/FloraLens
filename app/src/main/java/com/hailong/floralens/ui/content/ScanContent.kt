// ui/content/ScanContent.kt
package com.hailong.floralens.ui.content

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.FileUpload
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hailong.floralens.R

// 定义图片中使用的颜色
private val PrimaryGreen = Color(0xFF059669) // 按钮和标题的绿色
private val BackgroundColor = Color(0xFFFAFAFA) // 接近图片的背景色

@Composable
fun ScanContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            // 使用更简洁的背景色
            .background(Color(0xFAFAF8FF))
            .padding(horizontal = 24.dp, vertical = 48.dp), // 增加垂直 padding 让内容居中看起来更自然
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        // 标题 "Identify Plant"
        Text(
            text = "Identify Plant",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black, // 图片中标题是黑色
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(12.dp))

        // 描述 "Position the plant in the center of the frame for the best results."
        Text(
            text = "Position the plant in the center of the frame for the best results.",
            fontSize = 15.sp,
            color = Color.DarkGray,
            textAlign = TextAlign.Center,
            lineHeight = 22.sp,
            modifier = Modifier.padding(horizontal = 24.dp)
        )

        Spacer(modifier = Modifier.height(48.dp))

        // "Take Photo" 按钮
        ActionCard(
            iconResId = R.drawable.ic_shoot, // 👈 改为 iconResId
            title = "Take Photo",
            subtitle = "Use your camera",
            containerColor = PrimaryGreen, // 绿色背景
            contentColor = Color.White, // 白色文字和图标
            onClick = { /* TODO: 处理拍照逻辑 */ }
        )

        Spacer(modifier = Modifier.height(24.dp))

        // "Upload Image" 按钮
        ActionCard(
            iconResId = R.drawable.ic_upload, // 👈 改为 iconResId
            title = "Upload Image",
            subtitle = "From your gallery",
            containerColor = Color.White, // 白色背景
            contentColor = Color.Black, // 黑色文字
            borderColor = Color(0xFFE0E0E0), // 轻微的边框颜色
            onClick = { /* TODO: 处理上传图片逻辑 */ }
        )
    }
}

/**
 * 封装了图片中的卡片式按钮
 */
@Composable
fun ActionCard(
    iconResId: Int, // 👈 改为 Int 类型
    title: String,
    subtitle: String,
    containerColor: Color,
    contentColor: Color,
    borderColor: Color = containerColor,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp), // 大圆角
        colors = CardDefaults.cardColors(
            containerColor = containerColor,
            contentColor = contentColor
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp), // 移除阴影
        modifier = Modifier
            .fillMaxWidth(0.9f) // 限制宽度
            .height(90.dp) // 固定高度
            .clip(RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
            .background(containerColor)
            .then(
                // 针对第二个按钮，添加边框效果 (仅在 containerColor 为白色时添加)
                if (containerColor == Color.White) {
                    Modifier.background(Color.White)
                } else {
                    Modifier
                }
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            // 左侧图标 - 使用 painterResource
            Icon(
                painter = painterResource(id = iconResId), // 👈 使用 painterResource
                contentDescription = title,
                tint = if (containerColor == PrimaryGreen) Color.White else PrimaryGreen, // 绿色图标 (或白色)
                modifier = Modifier.size(32.dp)
            )

            Spacer(modifier = Modifier.width(20.dp))

            // 右侧文字内容
            Column(
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = contentColor
                )
                Text(
                    text = subtitle,
                    fontSize = 14.sp,
                    color = if (containerColor == PrimaryGreen) Color(0xAAFFFFFF) else Color.Gray // 根据背景调整副标题颜色
                )
            }
        }
    }
}