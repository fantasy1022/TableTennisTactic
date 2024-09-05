package com.fantasyfang.tabletennistactic.ui.component.view

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.fantasyfang.tabletennistactic.util.Const.Companion.DEFAULT_TABLE_SCREEN_WIDTH_RATIO
import com.fantasyfang.tabletennistactic.util.Const.Companion.TABLE_HEIGHT_WIDTH_RATIO

@Composable
fun TennisTableView(backgroundColor: Color) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    Canvas(modifier = Modifier.fillMaxSize()) {
        val tableWidth = (screenWidth * DEFAULT_TABLE_SCREEN_WIDTH_RATIO).toPx()
        val tableHeight = (tableWidth * TABLE_HEIGHT_WIDTH_RATIO)
        val lineThickness = tableWidth * 0.01f

        // the center of the screen
        val centerX = size.width / 2
        val centerY = size.height / 2
        // the center of the table
        val tableCenterX = tableWidth / 2
        val tableCenterY = tableHeight / 2

        // table
        drawTableLine(
            color = backgroundColor,
            x = centerX - tableCenterX,
            y = centerY - tableCenterY,
            width = tableWidth,
            height = tableHeight
        )

        // net
        val netCenterX = tableWidth / 2
        drawTableLine(
            x = centerX - netCenterX,
            y = centerY,
            width = tableWidth,
            height = lineThickness
        )

        // center vertically
        drawTableLine(
            x = centerX,
            y = centerY - tableCenterY,
            width = lineThickness,
            height = tableHeight
        )

        // left line
        drawTableLine(
            x = centerX - tableCenterX,
            y = centerY - tableCenterY,
            width = lineThickness,
            height = tableHeight
        )

        // right line
        drawTableLine(
            x = centerX + tableCenterX,
            y = centerY - tableCenterY,
            width = lineThickness,
            height = tableHeight
        )

        // top
        drawTableLine(
            x = centerX - tableCenterX,
            y = centerY - tableCenterY,
            width = tableWidth,
            height = lineThickness
        )

        // bottom
        drawTableLine(
            x = centerX - tableCenterX,
            y = centerY + tableCenterY,
            width = tableWidth,
            height = lineThickness
        )
    }
}


private fun DrawScope.drawTableLine(
    color: Color = Color.White,
    x: Float,
    y: Float,
    width: Float,
    height: Float,
) {
    drawRoundRect(
        color = color,
        topLeft = Offset(x, y),
        size = size.copy(width = width, height = height),
        cornerRadius = CornerRadius.Zero
    )
}