package com.fantasyfang.tabletennistactic.usecase.setting

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.fantasyfang.tabletennistactic.repository.setting.SettingRepository

class SetSettingsUseCase(private val settingRepository: SettingRepository) {

    sealed class SettingType {
        data class IsPreventSleep(val preventSleep: Boolean) : SettingType()
        data class TableColor(val color: Color) : SettingType()
        data class FloorColor(val color: Color) : SettingType()
        data class Team1Color(val color: Color) : SettingType()
        data class Team2Color(val color: Color) : SettingType()
        data class PlayerIconRadius(val radius: Dp) : SettingType()
        data class IsShowPlayerName(val show: Boolean) : SettingType()
    }

    suspend fun execute(setting: SettingType) {
        when (setting) {
            is SettingType.IsPreventSleep -> settingRepository.saveIsPreventSleep(setting.preventSleep)
            is SettingType.TableColor -> settingRepository.saveTableColor(setting.color)
            is SettingType.FloorColor -> settingRepository.saveFloorColor(setting.color)
            is SettingType.Team1Color -> settingRepository.saveTeam1Color(setting.color)
            is SettingType.Team2Color -> settingRepository.saveTeam2Color(setting.color)
            is SettingType.PlayerIconRadius -> settingRepository.savePlayerIconRadius(
                setting.radius
            )
            is SettingType.IsShowPlayerName -> settingRepository.saveIsShowPlayerName(
                setting.show
            )
        }
    }

}