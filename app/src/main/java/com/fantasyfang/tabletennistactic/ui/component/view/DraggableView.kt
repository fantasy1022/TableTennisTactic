package com.fantasyfang.tabletennistactic.ui.component.view

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue

@Composable
fun DraggableView(
    size: Dp,
    initialPosition: Offset = Offset(200.dp.value, 200.dp.value),
    onPositionChange: (Offset) -> Unit = {},
    onClick: () -> Unit = {},
    onDoubleClick: () -> Unit = {},
    drawContent: DrawScope.() -> Unit,
) {
    var position by remember { mutableStateOf(initialPosition) }

    Canvas(modifier = Modifier
        .size(size)
        .offset { IntOffset(position.x.roundToInt(), position.y.roundToInt()) }
        .background(Color.Transparent)
        .pointerInput(Unit) {
            detectTapGestures(
                onDoubleTap = {
                    onDoubleClick()
                },
                onTap = {
                    onClick()
                }
            )
        }
        .pointerInput(Unit) {
            detectDragGestures { _, dragAmount ->
                val newPosition = position + dragAmount
                position = newPosition
                onPositionChange(position)
            }
        }
    ) {
        drawContent()
    }
}