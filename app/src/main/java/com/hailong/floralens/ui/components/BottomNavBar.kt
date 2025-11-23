// ui/components/BottomNavBar.kt
package com.hailong.floralens.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hailong.floralens.R

data class BottomNavItem(
    val iconResId: Int,
    val label: String,
    val route: String,
    val isScanButton: Boolean = false // 标记是否为扫描按钮
)

val bottomNavItems = listOf(
    BottomNavItem(R.drawable.ic_home, "Home", "home"),
    BottomNavItem(R.drawable.ic_scan, "Scan", "scan", true), // 中间扫描按钮
    BottomNavItem(R.drawable.ic_profile, "Profile", "profile")
)

@Composable
fun FloraLensBottomNavBar(
    currentRoute: String,
    onNavigateTo: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
//            .height(100.dp)
//            .background(Color.Transparent),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.Bottom
    ) {
        bottomNavItems.forEach { item ->
            val isSelected = currentRoute == item.route
            val tint = if (isSelected) Color(0xFF059669) else Color.Gray

            val animDuration = 150
            val textDelay = 50

            val scale by animateFloatAsState(
                targetValue = if (isSelected) 1.1f else 1f,
                animationSpec = tween(animDuration, easing = FastOutSlowInEasing),
                label = "iconScale"
            )
            val alpha by animateFloatAsState(
                targetValue = if (item.isScanButton) 1f else if (isSelected) 1f else 0.7f,
                animationSpec = tween(animDuration),
                label = "iconAlpha"
            )

            val offsetY by animateDpAsState(
                targetValue = if (isSelected) {
                    if (item.isScanButton) (-42).dp else (-35).dp
                } else {
                    if (item.isScanButton) (-40).dp else (-25).dp
                },
                animationSpec = tween(animDuration, easing = FastOutSlowInEasing),
                label = "iconOffsetY"
            )

            val textAlpha by animateFloatAsState(
                targetValue = if (isSelected) 1f else 0f,
                animationSpec = tween(
                    durationMillis = animDuration,
                    delayMillis = if (isSelected) textDelay else 0,
                    easing = FastOutSlowInEasing
                ),
                label = "textAlpha"
            )
            val textOffsetY by animateDpAsState(
                targetValue = if (isSelected) (-15).dp else 4.dp,
                animationSpec = tween(
                    durationMillis = animDuration,
                    delayMillis = if (isSelected) textDelay else 0,
                    easing = FastOutSlowInEasing
                ),
                label = "textOffsetY"
            )

            val interactionSource = remember { MutableInteractionSource() }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(75.dp)
                    .background(Color(0xFFFFFFFF)),
                contentAlignment = Alignment.BottomCenter
            ) {
                Box(
                    modifier = Modifier
                        .wrapContentSize()
                        .offset(y = offsetY)
                        .graphicsLayer {
                            scaleX = scale
                            scaleY = scale
                            this.alpha = alpha
                        },
                    contentAlignment = Alignment.Center
                ) {
                    if (item.isScanButton) {
                        // 扫描按钮背景色根据选中状态变化
                        val scanButtonColor = if (isSelected) {
                            Color(0xFF059669) // 选中时绿色
                        } else {
                            Color(0xFF090909) // 未选中时黑色
                        }

                        Box(
                            modifier = Modifier
                                .size(62.dp)
                                .clip(CircleShape)
                                .background(scanButtonColor) // 👈 使用动态颜色
                                .clickable(
                                    interactionSource = interactionSource,
                                    indication = null
                                ) { onNavigateTo(item.route) },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(id = item.iconResId),
                                contentDescription = null,
                                tint = Color.White, // 图标始终为白色
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    } else {
                        Icon(
                            painter = painterResource(id = item.iconResId),
                            contentDescription = null,
                            tint = tint,
                            modifier = Modifier
                                .size(26.dp)
                                .clickable(
                                    interactionSource = interactionSource,
                                    indication = null
                                ) { onNavigateTo(item.route) }
                        )
                    }
                }
                if (!item.isScanButton) {
                    Text(
                        text = item.label,
                        color = tint.copy(alpha = textAlpha),
                        fontSize = 10.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .offset(y = textOffsetY)
                    )
                }
            }
        }
    }
}