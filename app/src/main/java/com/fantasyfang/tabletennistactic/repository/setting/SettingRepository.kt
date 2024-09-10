package com.fantasyfang.tabletennistactic.repository.setting

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import kotlinx.coroutines.flow.Flow

interface SettingRepository {
    val isPreventSleep: Flow<Boolean>
    val tableColor: Flow<Color>
    val floorColor: Flow<Color>
    val team1Color: Flow<Color>
    val team2Color: Flow<Color>
    val playerIconRadius: Flow<Float>
    val isShowPlayerName: Flow<Boolean>

    suspend fun saveIsPreventSleep(isPreventSleep: Boolean)
    suspend fun saveTableColor(color: Color)
    suspend fun saveFloorColor(color: Color)
    suspend fun saveTeam1Color(color: Color)
    suspend fun saveTeam2Color(color: Color)
    suspend fun savePlayerIconRadius(radius: Dp)
    suspend fun saveIsShowPlayerName(isShowPlayerName: Boolean)
}