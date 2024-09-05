package com.fantasyfang.tabletennistactic.repository.brush

import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.flow.Flow

interface BrushRepository {
    val brushColor: Flow<Color>
    val brushWidth: Flow<Float>

    suspend fun saveBrushColor(color: Color)
    suspend fun saveBrushWidth(width: Float)
}