package com.fantasyfang.tabletennistactic.ui.component.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fantasyfang.tabletennistactic.repository.player.PlayerInfo
import com.fantasyfang.tabletennistactic.util.Const.Companion.PLAYER_ICON_RADIUS_INTERVAL
import com.fantasyfang.tabletennistactic.util.Const.Companion.PLAYER_ICON_RADIUS_MIN

@Composable
fun PlayerView(
    playerInfo: PlayerInfo,
    backgroundColor: Color,
    playerRadius: Dp,
    isShowPlayerName: Boolean,
    initialPosition: Offset,
    onPositionChange: (Offset) -> Unit,
    onClick: () -> Unit,
    onDoubleClick: () -> Unit,
) {
    val outlineRadius = playerRadius + 3.dp
    val indexTextMeasurer = rememberTextMeasurer()
    val portion = (playerRadius - PLAYER_ICON_RADIUS_MIN) / PLAYER_ICON_RADIUS_INTERVAL
    val indexTextStyle = TextStyle(
        fontSize = (10.sp.value + 30.sp.value * portion).sp,
        color = Color.White,
    )
    val indexTextToDraw = playerInfo.index.toString()
    val indexTextLayoutResult = remember(indexTextToDraw) {
        indexTextMeasurer.measure(indexTextToDraw, indexTextStyle)
    }

    val playerTextMeasurer = rememberTextMeasurer()
    val playerTextStyle = TextStyle(
        fontSize = 18.sp,
        color = Color.White,
    )
    val playerTextToDraw = playerInfo.name
    val playerTextLayoutResult = remember(playerTextToDraw) {
        playerTextMeasurer.measure(playerTextToDraw, playerTextStyle)
    }

    DraggableView(
        size = playerRadius + playerTextLayoutResult.size.height.dp,
        initialPosition = initialPosition,
        onPositionChange = onPositionChange,
        onClick = onClick,
        onDoubleClick = onDoubleClick,
    ) {
        drawCircle(
            color = Color.White,
            radius = outlineRadius.toPx(),
            center = Offset(playerRadius.toPx(), playerRadius.toPx())
        )
        drawCircle(
            color = backgroundColor,
            radius = playerRadius.toPx(),
            center = Offset(playerRadius.toPx(), playerRadius.toPx())
        )
        drawText(
            textMeasurer = indexTextMeasurer,
            text = indexTextToDraw,
            style = indexTextStyle,
            topLeft = Offset(
                x = playerRadius.toPx() - indexTextLayoutResult.size.width / 2,
                y = playerRadius.toPx() - indexTextLayoutResult.size.height / 2
            )
        )

        if (isShowPlayerName) {
            val textPositionX = playerRadius.toPx() - playerTextLayoutResult.size.width / 2
            val textPositionY = playerRadius.toPx() * 2
            drawText(
                textMeasurer = playerTextMeasurer,
                text = playerTextToDraw,
                style = playerTextStyle,
                topLeft = Offset(
                    x = textPositionX, y = textPositionY
                )
            )
        }
    }
}