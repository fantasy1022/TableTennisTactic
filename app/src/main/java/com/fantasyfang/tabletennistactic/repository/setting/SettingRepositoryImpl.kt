package com.fantasyfang.tabletennistactic.repository.setting

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import com.fantasyfang.tabletennistactic.extension.getColorData
import com.fantasyfang.tabletennistactic.extension.getData
import com.fantasyfang.tabletennistactic.extension.storeColor
import com.fantasyfang.tabletennistactic.util.Const.Companion.DEFAULT_FLOOR_COLOR
import com.fantasyfang.tabletennistactic.util.Const.Companion.DEFAULT_IS_PREVENT_SLEEP
import com.fantasyfang.tabletennistactic.util.Const.Companion.DEFAULT_IS_SHOW_PLAYER_NAME
import com.fantasyfang.tabletennistactic.util.Const.Companion.DEFAULT_PLAYER_ICON_RADIUS
import com.fantasyfang.tabletennistactic.util.Const.Companion.DEFAULT_TABLE_COLOR
import com.fantasyfang.tabletennistactic.util.Const.Companion.DEFAULT_TEAM_1_COLOR
import com.fantasyfang.tabletennistactic.util.Const.Companion.DEFAULT_TEAM_2_COLOR
import kotlinx.coroutines.flow.Flow


class SettingRepositoryImpl(private val dataStore: DataStore<Preferences>) : SettingRepository {

    companion object {
        private val IS_PREVENT_SLEEP = booleanPreferencesKey("is_prevent_sleep")
        private val TABLE_COLOR = intPreferencesKey("table_color")
        private val FLOOR_COLOR = intPreferencesKey("border_color")
        private val TEAM_1_COLOR = intPreferencesKey("team_1_color")
        private val TEAM_2_COLOR = intPreferencesKey("team_2_color")
        private val PLAYER_ICON_RADIUS = floatPreferencesKey("player_icon_radius")
        private val IS_SHOW_PLAYER_NAME = booleanPreferencesKey("is_show_player_name")
    }

    override val isPreventSleep: Flow<Boolean>
        get() = dataStore.getData { preferences ->
            preferences[IS_PREVENT_SLEEP] ?: DEFAULT_IS_PREVENT_SLEEP
        }

    override val tableColor: Flow<Color>
        get() = dataStore.getColorData(TABLE_COLOR, DEFAULT_TABLE_COLOR)

    override val floorColor: Flow<Color>
        get() = dataStore.getColorData(FLOOR_COLOR, DEFAULT_FLOOR_COLOR)

    override val team1Color: Flow<Color>
        get() = dataStore.getColorData(TEAM_1_COLOR, DEFAULT_TEAM_1_COLOR)

    override val team2Color: Flow<Color>
        get() = dataStore.getColorData(TEAM_2_COLOR, DEFAULT_TEAM_2_COLOR)

    override val playerIconRadius: Flow<Float>
        get() = dataStore.getData { preferences ->
            preferences[PLAYER_ICON_RADIUS] ?: DEFAULT_PLAYER_ICON_RADIUS.value
        }

    override val isShowPlayerName: Flow<Boolean>
        get() = dataStore.getData { preferences ->
            preferences[IS_SHOW_PLAYER_NAME] ?: DEFAULT_IS_SHOW_PLAYER_NAME
        }

    override suspend fun saveIsPreventSleep(isPreventSleep: Boolean) {
        dataStore.edit { preferences ->
            preferences[IS_PREVENT_SLEEP] = isPreventSleep
        }
    }

    override suspend fun saveTableColor(color: Color) {
        dataStore.storeColor(TABLE_COLOR, color)
    }

    override suspend fun saveFloorColor(color: Color) {
        dataStore.storeColor(FLOOR_COLOR, color)
    }

    override suspend fun saveTeam1Color(color: Color) {
        dataStore.storeColor(TEAM_1_COLOR, color)
    }

    override suspend fun saveTeam2Color(color: Color) {
        dataStore.storeColor(TEAM_2_COLOR, color)
    }

    override suspend fun savePlayerIconRadius(radius: Dp) {
        dataStore.edit { preferences ->
            preferences[PLAYER_ICON_RADIUS] = radius.value
        }
    }

    override suspend fun saveIsShowPlayerName(isShowPlayerName: Boolean) {
        dataStore.edit { preferences ->
            preferences[IS_SHOW_PLAYER_NAME] = isShowPlayerName
        }
    }
}