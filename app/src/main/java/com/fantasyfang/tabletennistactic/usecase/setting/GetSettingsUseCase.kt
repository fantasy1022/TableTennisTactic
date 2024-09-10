package com.fantasyfang.tabletennistactic.usecase.setting

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.fantasyfang.tabletennistactic.repository.setting.SettingRepository
import com.fantasyfang.tabletennistactic.ui.setting.SettingUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine


class GetSettingsUseCase(
    private val settingRepository: SettingRepository,
) {

    fun execute(): Flow<SettingUiState> {
        return combine(
            settingRepository.isPreventSleep,
            settingRepository.tableColor,
            settingRepository.floorColor,
            settingRepository.team1Color,
            settingRepository.team2Color,
            settingRepository.playerIconRadius,
            settingRepository.isShowPlayerName,
        ) { result ->
            SettingUiState(
                isPreventSleep = result[0] as Boolean,
                tableColor = result[1] as Color,
                floorColor = result[2] as Color,
                team1Color = result[3] as Color,
                team2Color = result[4] as Color,
                playerIconRadius = (result[5] as Float).dp,
                isShowPlayerName = result[6] as Boolean,
            )
        }
    }
}