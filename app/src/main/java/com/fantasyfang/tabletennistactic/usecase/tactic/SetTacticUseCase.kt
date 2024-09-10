package com.fantasyfang.tabletennistactic.usecase.tactic

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import com.fantasyfang.tabletennistactic.repository.brush.BrushRepository
import com.fantasyfang.tabletennistactic.repository.player.PlayerInfo
import com.fantasyfang.tabletennistactic.repository.player.PlayerRepository

class SetTacticUseCase(
    private val brushRepository: BrushRepository,
    private val playerRepository: PlayerRepository,
) {

    sealed class SetTacticType {
        data class BrushColor(val color: Color) : SetTacticType()
        data class BrushWidth(val width: Float) : SetTacticType()
        data class PlayerInsert(val playerInfo: PlayerInfo) : SetTacticType()
        data class PlayerUpdate(val playerInfo: PlayerInfo) : SetTacticType()
        data class PlayerPosition(val playerId: Int, val newPosition: Offset) : SetTacticType()
        data class PlayerDelete(val playerId: Int) : SetTacticType()
    }

    suspend fun execute(setting: SetTacticType) {
        when (setting) {
            is SetTacticType.BrushColor -> brushRepository.saveBrushColor(setting.color)
            is SetTacticType.BrushWidth -> brushRepository.saveBrushWidth(setting.width)
            is SetTacticType.PlayerInsert -> playerRepository.insertPlayer(setting.playerInfo)
            is SetTacticType.PlayerUpdate -> playerRepository.updatePlayer(setting.playerInfo)
            is SetTacticType.PlayerPosition -> playerRepository.updatePlayerPosition(
                setting.playerId,
                setting.newPosition,
            )
            is SetTacticType.PlayerDelete -> playerRepository.deletePlayer(setting.playerId)
        }
    }
}