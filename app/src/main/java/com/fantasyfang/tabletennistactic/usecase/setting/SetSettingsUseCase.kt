package com.fantasyfang.tabletennistactic.usecase.setting

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.fantasyfang.tabletennistactic.repository.setting.SettingRepository

class SetSettingsUseCase(private val settingRepository: SettingRepository) {

    sealed class SettingType {
        data class IsPreventSleep(val preventSleep: Boolean) : SettingType()
        data class CourtColor(val color: Color) : SettingType()
        data class BorderColor(val color: Color) : SettingType()
        data class Team1Color(val color: Color) : SettingType()
        data class Team2Color(val color: Color) : SettingType()
        data class PlayerIconRadius(val radius: Dp) : SettingType()
        data class IsShowPlayerName(val show: Boolean) : SettingType()
        // Add other settings as needed
    }

    suspend fun execute(setting: SettingType) {

    }

}