package com.fantasyfang.tabletennistactic.usecase.tactic

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.fantasyfang.tabletennistactic.repository.brush.BrushRepository
import com.fantasyfang.tabletennistactic.repository.player.PlayerInfo
import com.fantasyfang.tabletennistactic.repository.player.PlayerRepository
import com.fantasyfang.tabletennistactic.repository.setting.SettingRepository
import com.fantasyfang.tabletennistactic.ui.tactic.TacticUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class GetTacticUseCase(
    private val brushRepository: BrushRepository,
    private val playerRepository: PlayerRepository,
    private val settingRepository: SettingRepository,
) {

    fun execute(): Flow<TacticUiState> {
        return combine(
            settingRepository.tableColor,
            settingRepository.floorColor,
            settingRepository.team1Color,
            settingRepository.team2Color,
            settingRepository.playerIconRadius,
            settingRepository.isShowPlayerName,
            brushRepository.brushColor,
            brushRepository.brushWidth,
            playerRepository.getAllPlayers()
        ) { result ->
            @Suppress("UNCHECKED_CAST")
            TacticUiState(
                tableColor = result[0] as Color,
                floorColor = result[1] as Color,
                team1Color = result[2] as Color,
                team2Color = result[3] as Color,
                playerIconRadius = (result[4] as Float).dp,
                isShowPlayerName = result[5] as Boolean,
                brushColor = result[6] as Color,
                brushWidth = result[7] as Float,
                player = result[8] as List<PlayerInfo>,
            )
        }
    }
}