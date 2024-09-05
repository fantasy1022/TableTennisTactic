package com.fantasyfang.tabletennistactic.usecase.tactic

import androidx.compose.ui.graphics.Color
import com.fantasyfang.tabletennistactic.repository.brush.BrushRepository
import com.fantasyfang.tabletennistactic.ui.tactic.TacticUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class GetTacticUseCase(
    private val brushRepository: BrushRepository,
) {

    fun execute(): Flow<TacticUiState> {
        return combine(
            brushRepository.brushColor,
            brushRepository.brushWidth,
        ) { result ->
            TacticUiState(
//                courtColor = result[0] as Color,
//                borderColor = result[1] as Color,
//                team1Color = result[2] as Color,
//                team2Color = result[3] as Color,
//                playerIconRadius = (result[4] as Float).dp,
//                isShowPlayerName = result[5] as Boolean,
                brushColor = result[0] as Color,
                brushWidth = result[1] as Float,
//                player = result[8] as List<PlayerInfo>
            )
        }
    }
}