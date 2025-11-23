// ui/content/ProfileContent.kt
package com.hailong.floralens.ui.content

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CrisisAlert
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Grass
import androidx.compose.material.icons.filled.HourglassTop
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ViewModule
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// --- 颜色定义 ---
private val PrimaryGreen = Color(0xFF059669)
private val LightBackground = Color(0xFFFAFAF8)
private val DarkGrayText = Color(0xFF333333)
private val LightGrayBackground = Color(0xFFE0E0E0)

@Composable
fun ProfileContent() {
    // 状态变量，用于切换 My Garden 和 Stats & Badges 视图
    var selectedTab by remember { mutableStateOf(ProfileTab.STATS) } // 默认显示 Stats & Badges

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(LightBackground),
        containerColor = LightBackground
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(LightBackground)
        ) {
            // 1. 用户资料区
            ProfileHeader()

            // 2. 标签切换器
            TabSwitcher(
                selectedTab = selectedTab,
                onTabSelected = { selectedTab = it }
            )

            Spacer(modifier = Modifier.height(24.dp))

            // 3. 根据选择的标签显示内容
            when (selectedTab) {
                ProfileTab.GARDEN -> MyGardenView()
                ProfileTab.STATS -> StatsAndBadgesView()
            }
        }
    }
}

// --- 枚举定义标签页 ---
enum class ProfileTab {
    GARDEN, STATS
}

// --- 1. 用户资料区 (上部) ---
@Composable
fun ProfileHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // 头像和信息
        Row(verticalAlignment = Alignment.CenterVertically) {
            // 头像 (PL)
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(PrimaryGreen),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "PL",
                    color = Color.White,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = "Plant Lover",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkGrayText
                )
                Spacer(modifier = Modifier.height(4.dp))
                // 等级徽章
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFFF0F0F0))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Rank",
                        tint = Color(0xFFFFCC33),
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "SEEDLING",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = DarkGrayText
                    )
                }
            }
        }

        // 设置按钮
        Icon(
            imageVector = Icons.Default.Settings,
            contentDescription = "Settings",
            tint = DarkGrayText.copy(alpha = 0.6f),
            modifier = Modifier
                .size(28.dp)
                .clickable { /* TODO: 导航到设置页 */ }
        )
    }
}

// --- 2. 标签切换器 ---
@Composable
fun TabSwitcher(selectedTab: ProfileTab, onTabSelected: (ProfileTab) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(LightGrayBackground.copy(alpha = 0.7f)) // 浅灰色背景
            .height(48.dp)
    ) {
        Row(modifier = Modifier.fillMaxSize()) {
            // My Garden 标签
            TabItem(
                text = "My Garden",
                icon = Icons.Default.ViewModule,
                isSelected = selectedTab == ProfileTab.GARDEN,
                onClick = { onTabSelected(ProfileTab.GARDEN) }
            )

            // Stats & Badges 标签
            TabItem(
                text = "Stats & Badges",
                icon = Icons.Default.CrisisAlert,
                isSelected = selectedTab == ProfileTab.STATS,
                onClick = { onTabSelected(ProfileTab.STATS) }
            )
        }
    }
}

@Composable
fun RowScope.TabItem(text: String, icon: ImageVector, isSelected: Boolean, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .weight(1f)
            .fillMaxHeight()
            .clip(RoundedCornerShape(12.dp))
            .background(if (isSelected) Color.White else Color.Transparent)
            .clickable(onClick = onClick)
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = text,
            tint = if (isSelected) PrimaryGreen else Color.Gray,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = text,
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold,
            color = if (isSelected) PrimaryGreen else Color.Gray
        )
    }
}

// --- 3.1. Stats & Badges 视图 ---
@Composable
fun StatsAndBadgesView() {
    Column(modifier = Modifier.padding(horizontal = 24.dp)) {
        // 统计卡片
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            StatCard(
                title = "TOTAL IDENTIFIED",
                value = "0",
                unit = "species",
                modifier = Modifier.weight(1f)
            )
            StatCard(
                title = "CURRENT STREAK",
                value = "3",
                unit = "days",
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // 徽章区
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.EmojiEvents,
                contentDescription = "Badges",
                tint = Color(0xFFFFCC33),
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Badges",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = DarkGrayText
            )
        }

        // 徽章列表
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp, horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                BadgeItem(
                    icon = Icons.Default.Grass,
                    title = "First Find",
                    unlocked = true,
                    iconTint = PrimaryGreen,
                    iconBackground = PrimaryGreen.copy(alpha = 0.1f)
                )
                BadgeItem(
                    icon = Icons.Default.Spa,
                    title = "Collector",
                    unlocked = false,
                    iconTint = Color.Gray,
                    iconBackground = Color.LightGray.copy(alpha = 0.5f)
                )
                BadgeItem(
                    icon = Icons.Default.Star,
                    title = "Master",
                    unlocked = false,
                    iconTint = Color.Gray,
                    iconBackground = Color.LightGray.copy(alpha = 0.5f)
                )
            }
        }
    }
}

@Composable
fun StatCard(title: String, value: String, unit: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.height(100.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = title,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Gray
            )
            Column {
                Text(
                    text = value,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkGrayText
                )
                Text(
                    text = unit,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
        }
    }
}

@Composable
fun BadgeItem(icon: ImageVector, title: String, unlocked: Boolean, iconTint: Color, iconBackground: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.width(80.dp)) {
        // 图标背景
        Box(
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
                .background(iconBackground)
                .padding(12.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = iconTint,
                modifier = Modifier.size(32.dp)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = title,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = if (unlocked) DarkGrayText else Color.Gray,
            textAlign = TextAlign.Center
        )
    }
}

// --- 3.2. My Garden 视图 ---
@Composable
fun MyGardenView() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // 空状态图标
        Icon(
            imageVector = Icons.Default.HourglassTop,
            contentDescription = "No plants scanned yet",
            tint = Color.LightGray,
            modifier = Modifier.size(64.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "No plants scanned yet.",
            fontSize = 18.sp,
            color = Color.Gray,
            fontWeight = FontWeight.Medium
        )
    }
}