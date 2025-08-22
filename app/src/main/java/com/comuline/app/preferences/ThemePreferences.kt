package com.comuline.app.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "theme_preferences")

enum class ThemeMode(val value: Int) {
    LIGHT(0),
    DARK(1);
    
    companion object {
        fun fromValue(value: Int): ThemeMode = entries.find { it.value == value } ?: LIGHT
    }
}

@Singleton
class ThemePreferences @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val dataStore = context.dataStore
    
    companion object {
        private val THEME_MODE_KEY = intPreferencesKey("theme_mode")
    }
    
    val themeMode: Flow<ThemeMode> = dataStore.data.map { preferences ->
        ThemeMode.fromValue(preferences[THEME_MODE_KEY] ?: ThemeMode.LIGHT.value)
    }
    
    suspend fun setThemeMode(themeMode: ThemeMode) {
        dataStore.updateData { preferences ->
            preferences.toMutablePreferences().apply {
                this[THEME_MODE_KEY] = themeMode.value
            }
        }
    }
}