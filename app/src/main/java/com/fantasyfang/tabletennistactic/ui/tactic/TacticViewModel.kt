package com.fantasyfang.tabletennistactic.ui.tactic

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fantasyfang.tabletennistactic.repository.player.PlayerInfo
import com.fantasyfang.tabletennistactic.usecase.tactic.GetTacticUseCase
import com.fantasyfang.tabletennistactic.usecase.tactic.SetTacticUseCase
import com.fantasyfang.tabletennistactic.usecase.tactic.SetTacticUseCase.SetTacticType.PlayerInsert
import com.fantasyfang.tabletennistactic.util.Const.Companion.DEFAULT_BRUSH_COLOR
import com.fantasyfang.tabletennistactic.util.Const.Companion.DEFAULT_BRUSH_WIDTH
import com.fantasyfang.tabletennistactic.util.Const.Companion.DEFAULT_FLOOR_COLOR
import com.fantasyfang.tabletennistactic.util.Const.Companion.DEFAULT_IS_SHOW_PLAYER_NAME
import com.fantasyfang.tabletennistactic.util.Const.Companion.DEFAULT_PLAYER_ICON_RADIUS
import com.fantasyfang.tabletennistactic.util.Const.Companion.DEFAULT_TABLE_COLOR
import com.fantasyfang.tabletennistactic.util.Const.Companion.DEFAULT_TEAM_1_COLOR
import com.fantasyfang.tabletennistactic.util.Const.Companion.DEFAULT_TEAM_2_COLOR
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TacticViewModel(
    getTacticUseCase: GetTacticUseCase,
    private val setTacticUseCase: SetTacticUseCase
) :
    ViewModel() {

    val uiState: StateFlow<TacticUiState> = getTacticUseCase.execute().stateIn(
        scope = viewModelScope,
        // Flow is set to emits value for when app is on the foreground
        // 5 seconds stop delay is added to ensure it flows continuously
        // for cases such as configuration change
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = TacticUiState()
    )

    fun saveSetting(setTacTicType: SetTacticUseCase.SetTacticType) {
        viewModelScope.launch {
            setTacticUseCase.execute(setTacTicType)
        }
    }

    fun insertPlayerInfo(playerInfo: PlayerInfo) {
        viewModelScope.launch {
            setTacticUseCase.execute(PlayerInsert(playerInfo))
        }
    }
}

data class TacticUiState(
    val tableColor: Color = DEFAULT_TABLE_COLOR,
    val floorColor: Color = DEFAULT_FLOOR_COLOR,
    val team1Color: Color = DEFAULT_TEAM_1_COLOR,
    val team2Color: Color = DEFAULT_TEAM_2_COLOR,
    val playerIconRadius: Dp = DEFAULT_PLAYER_ICON_RADIUS,
    val isShowPlayerName: Boolean = DEFAULT_IS_SHOW_PLAYER_NAME,
    val brushColor: Color = DEFAULT_BRUSH_COLOR,
    val brushWidth: Float = DEFAULT_BRUSH_WIDTH,
    val player: List<PlayerInfo> = emptyList()
)