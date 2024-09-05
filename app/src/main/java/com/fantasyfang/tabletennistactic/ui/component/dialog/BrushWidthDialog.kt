package com.fantasyfang.tabletennistactic.ui.component.dialog

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.fantasyfang.tabletennistactic.R
import com.fantasyfang.tabletennistactic.util.Const.Companion.BRUSH_WIDTH_MAX
import com.fantasyfang.tabletennistactic.util.Const.Companion.BRUSH_WIDTH_MIN
import java.util.Locale

@Composable
fun BrushWidthDialog(
    brushSize: Float, onBrushSizeChange: (Float) -> Unit, onDismissRequest: () -> Unit
) {
    val formattedBrushSize = String.format(Locale.getDefault(), "%.1f", brushSize)

    AlertDialog(onDismissRequest = onDismissRequest,
        title = { Text(stringResource(id = R.string.brush_width_title)) },
        text = {
            Column {
                Canvas(
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .fillMaxWidth()
                        .wrapContentHeight()
                ) {
                    drawLine(
                        color = Color.Black,
                        start = Offset(x = 0f, y = size.height),
                        end = Offset(x = size.width, y = size.height),
                        strokeWidth = brushSize,
                        cap = StrokeCap.Round
                    )
                }

                Slider(
                    modifier = Modifier.padding(bottom = 8.dp),
                    value = brushSize,
                    onValueChange = onBrushSizeChange,
                    valueRange = BRUSH_WIDTH_MIN..BRUSH_WIDTH_MAX,
                    steps = 0,
                )
                Text(
                    text = "${stringResource(id = R.string.brush_width_value)}: $formattedBrushSize",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        },
        confirmButton = { })
}