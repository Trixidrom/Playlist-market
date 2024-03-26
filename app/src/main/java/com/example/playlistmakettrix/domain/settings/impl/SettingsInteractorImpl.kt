package com.example.playlistmakettrix.domain.settings.impl

import com.example.playlistmakettrix.data.settings.SettingsRepository
import com.example.playlistmakettrix.domain.settings.SettingsInteractor
import com.example.playlistmakettrix.domain.settings.model.ThemeSettings

class SettingsInteractorImpl (private val settingsRepository: SettingsRepository): SettingsInteractor { override fun getThemeSettings(): ThemeSettings {
        return settingsRepository.getThemeSettings()
    }

    override fun updateThemeSettings(themeSettings: ThemeSettings) {
        settingsRepository.updateThemeSettings(themeSettings)
    }
}