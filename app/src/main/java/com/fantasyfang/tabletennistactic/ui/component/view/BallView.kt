package com.fantasyfang.tabletennistactic.ui.component.view

import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

@Composable
fun BallView(ballRadius: Dp, ballColor: Color) {
    DraggableView(size = ballRadius * 2) {
        drawCircle(
            color = ballColor,
            radius = ballRadius.toPx(),
            center = Offset(ballRadius.toPx(), ballRadius.toPx())
        )
    }
}