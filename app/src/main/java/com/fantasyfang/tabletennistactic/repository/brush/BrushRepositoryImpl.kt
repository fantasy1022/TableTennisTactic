package com.fantasyfang.tabletennistactic.repository.brush

import androidx.compose.ui.graphics.Color
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import com.fantasyfang.tabletennistactic.extension.getColor
import com.fantasyfang.tabletennistactic.extension.getData
import com.fantasyfang.tabletennistactic.extension.storeColor
import com.fantasyfang.tabletennistactic.util.Const.Companion.DEFAULT_BRUSH_COLOR
import com.fantasyfang.tabletennistactic.util.Const.Companion.DEFAULT_BRUSH_WIDTH
import kotlinx.coroutines.flow.Flow


class BrushRepositoryImpl(private val dataStore: DataStore<Preferences>) : BrushRepository {
    companion object {
        private val BRUSH_COLOR = intPreferencesKey("brush_color")
        private val BRUSH_WIDTH = floatPreferencesKey("brush_width")
    }

    override val brushColor: Flow<Color>
        get() = dataStore.getData { preferences ->
            preferences.getColor(BRUSH_COLOR, DEFAULT_BRUSH_COLOR)
        }

    override val brushWidth: Flow<Float>
        get() = dataStore.getData { preferences ->
            preferences[BRUSH_WIDTH] ?: DEFAULT_BRUSH_WIDTH
        }

    override suspend fun saveBrushColor(color: Color) {
        dataStore.storeColor(BRUSH_COLOR, color)
    }

    override suspend fun saveBrushWidth(width: Float) {
        dataStore.edit { preferences ->
            preferences[BRUSH_WIDTH] = width
        }
    }
}