package com.example.playlistmakettrix.domain.settings

import com.example.playlistmakettrix.domain.settings.model.ThemeSettings

interface SettingsInteractor {
    fun getThemeSettings(): ThemeSettings
    fun updateThemeSettings(settings: ThemeSettings)
}