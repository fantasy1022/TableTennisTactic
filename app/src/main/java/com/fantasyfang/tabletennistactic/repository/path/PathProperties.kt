package com.fantasyfang.tabletennistactic.repository.path

import androidx.compose.ui.graphics.Color
import com.fantasyfang.tabletennistactic.util.Const.Companion.DEFAULT_BRUSH_COLOR
import com.fantasyfang.tabletennistactic.util.Const.Companion.DEFAULT_BRUSH_WIDTH

data class PathProperties(
    var strokeWidth: Float = DEFAULT_BRUSH_WIDTH,
    var color: Color = DEFAULT_BRUSH_COLOR,
    var eraseMode: Boolean = false
)