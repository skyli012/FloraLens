// ui/content/HomeContent.kt
package com.hailong.floralens.ui.content

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hailong.floralens.R // 假设您有一个R文件来引用资源

// --- 颜色定义 ---
private val PrimaryGreen = Color(0xFF059669)
private val DarkGreen = Color(0xFF025B49)
private val LightGreenGradient = Color(0xFF50C878)
private val HeaderGradientStart = PrimaryGreen
private val HeaderGradientEnd = DarkGreen
private val BackgroundColor = Color(0xFAFAF8FF)

@Composable
fun HomeContent() {
    // 使用 LazyColumn 实现可滚动主页
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
    ) {
        // 1. 顶部欢迎和搜索栏
        item {
            HeaderSection()
        }

        // 2. 探索分类
        item {
            ExploreCategoriesSection()
        }

        // 3. 最近扫描
        item {
            RecentScansSection()
        }

        // 4. 每日小贴士
        item {
            DailyPlantTipsSection()
        }

        // 底部额外的空间
        item {
            Spacer(modifier = Modifier.height(48.dp))
        }
    }
}

// --- 1. 顶部欢迎和搜索栏 ---
@Composable
fun HeaderSection() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp) // 高度比图片稍微高一点以容纳内容
            .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(HeaderGradientStart, HeaderGradientEnd)
                )
            )
            .padding(24.dp)
    ) {
        Column {
            // 欢迎文本
            Text(
                text = "Hello, Plant Lover! 🌱",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Ready to explore the green world today?",
                fontSize = 14.sp,
                color = Color.White.copy(alpha = 0.8f)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // 识别植物按钮 (SearchBar样式)
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .clickable { /* TODO: 导航到扫描页 */ }
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.CameraAlt,
                        contentDescription = "Identify Plant",
                        tint = PrimaryGreen,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Identify a Plant",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.DarkGray
                    )
                }
            }
        }
    }
}

// --- 2. 探索分类 ---
@Composable
fun ExploreCategoriesSection() {
    Column(modifier = Modifier.padding(top = 24.dp)) {
        Text(
            text = "Explore Categories",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(horizontal = 24.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))

        // 可横向滚动的标签
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val categories = listOf("Indoor Plants", "Succulents", "Trees", "Flowers", "Cacti", "Herbs", "Vines")
            categories.forEach { category ->
                CategoryChip(category)
            }
        }
    }
}

@Composable
fun CategoryChip(text: String) {
    // 使用 SuggestionChip 替代 FilterChip，更简单
    SuggestionChip(
        onClick = { /* TODO: 过滤或搜索 */ },
        label = { Text(text) },
        colors = SuggestionChipDefaults.suggestionChipColors(
            containerColor = Color.LightGray.copy(alpha = 0.2f),
            labelColor = Color.DarkGray
        ),
        modifier = Modifier.height(32.dp)
    )
}

// --- 3. 最近扫描 ---
@Composable
fun RecentScansSection() {
    Column(modifier = Modifier.padding(top = 32.dp)) {
        Text(
            text = "Recent Scans",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(horizontal = 24.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))

        // 单个最近扫描项 (图片中只显示一个)
        Row(modifier = Modifier.padding(horizontal = 24.dp)) {
            RecentScanItem(
                commonName = "Persian Speedwell",
                scientificName = "Veronica persica",
                // 替换为您的实际图片资源 ID
                imagePainter = painterResource(id = R.drawable.ic_profile)
            )
        }
    }
}

@Composable
fun RecentScanItem(commonName: String, scientificName: String, imagePainter: Painter) {
    Column(
        modifier = Modifier
            .width(100.dp)
            .clickable { /* TODO: 查看详情 */ }
    ) {
        // 图片
        Image(
            painter = imagePainter,
            contentDescription = commonName,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(90.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color.Gray.copy(alpha = 0.3f))
        )
        Spacer(modifier = Modifier.height(4.dp))

        // 常用名
        Text(
            text = commonName,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        // 学名
        Text(
            text = scientificName,
            fontSize = 12.sp,
            color = Color.Gray,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

// --- 4. 每日小贴士 ---
@Composable
fun DailyPlantTipsSection() {
    Column(modifier = Modifier.padding(top = 32.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Daily Plant Tips",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
            )
            Text(
                text = "View All",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = PrimaryGreen,
                modifier = Modifier.clickable { /* TODO: 查看全部贴士 */ }
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        // 贴士列表
        Column(
            modifier = Modifier.padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            PlantTipItem(
                icon = Icons.Default.LightMode,
                title = "Light Matters",
                description = "South-facing windows provide the brightest light, perfect for succulents and cacti.",
                iconColor = Color(0xFFFFCC33) // 太阳色
            )
            PlantTipItem(
                icon = Icons.Default.WaterDrop,
                title = "Watering Basics",
                description = "Always check the top inch of soil before watering. Too much water is the number one killer!",
                iconColor = Color(0xFF6699FF) // 水滴色
            )
            // 更多贴士...
        }
    }
}

@Composable
fun PlantTipItem(icon: ImageVector, title: String, description: String, iconColor: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .clickable { /* TODO: 查看贴士详情 */ }
    ) {
        // 左侧图标
        Icon(
            imageVector = icon,
            contentDescription = title,
            tint = iconColor,
            modifier = Modifier
                .size(24.dp)
                .align(Alignment.Top)
        )
        Spacer(modifier = Modifier.width(16.dp))

        // 右侧内容
        Column {
            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )
            Text(
                text = description,
                fontSize = 14.sp,
                color = Color.Gray,
                lineHeight = 20.sp
            )
        }
    }
    // 添加一个分隔线，使列表看起来更整洁
    Divider(color = Color.LightGray.copy(alpha = 0.3f), thickness = 1.dp, modifier = Modifier.padding(top = 8.dp))
}