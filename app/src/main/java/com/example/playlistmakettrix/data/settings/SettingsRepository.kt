package com.example.playlistmakettrix.data.settings

import com.example.playlistmakettrix.domain.settings.model.ThemeSettings

interface SettingsRepository {
    fun getThemeSettings(): ThemeSettings
    fun updateThemeSettings(settings: ThemeSettings)
}