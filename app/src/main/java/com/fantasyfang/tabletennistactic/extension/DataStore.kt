package com.fantasyfang.tabletennistactic.extension

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

fun <T> DataStore<Preferences>.getData(
    storePreferences: (preferences: Preferences) -> T
): Flow<T> {
    return this.data
        .catch {
            if (it is IOException) {
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preferences ->
            storePreferences(preferences)
        }
}

fun Preferences.getColor(key: Preferences.Key<Int>, defaultColor: Color): Color {
    val colorInt = this[key] ?: defaultColor.toArgb()
    return Color(colorInt)
}

suspend fun DataStore<Preferences>.storeColor(key: Preferences.Key<Int>, color: Color) {
    edit { preferences ->
        preferences[key] = color.toArgb()
    }
}
