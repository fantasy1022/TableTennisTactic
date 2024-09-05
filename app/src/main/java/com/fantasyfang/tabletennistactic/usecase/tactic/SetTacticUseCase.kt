package com.fantasyfang.tabletennistactic.usecase.tactic

import androidx.compose.ui.graphics.Color
import com.fantasyfang.tabletennistactic.repository.brush.BrushRepository
import com.fantasyfang.tabletennistactic.repository.player.PlayerInfo

class SetTacticUseCase(
    private val brushRepository: BrushRepository,
) {

    sealed class SetTacticType {
        data class BrushColor(val color: Color) : SetTacticType()
        data class BrushWidth(val width: Float) : SetTacticType()
    }

    suspend fun execute(setting: SetTacticType) {
        when (setting) {
            is SetTacticType.BrushColor -> brushRepository.saveBrushColor(setting.color)
            is SetTacticType.BrushWidth -> brushRepository.saveBrushWidth(setting.width)
        }
    }
}