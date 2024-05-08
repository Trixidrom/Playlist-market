package com.example.playlistmakettrix.domain.settings.impl

import com.example.playlistmakettrix.domain.settings.ThemeSwitchInteractor
import com.example.playlistmakettrix.domain.settings.ThemeSwitchRepository

class ThemeSwitchInteractorImpl (private val themeSwitchRepository: ThemeSwitchRepository) : ThemeSwitchInteractor {

    override fun switch(isDarkModeOn: Boolean) {
        themeSwitchRepository.switchTheme(isDarkModeOn)
    }

    override fun isDarkModeOn(): Boolean {
        return themeSwitchRepository.isDarkModeOn()
    }

    override fun applyCurrentTheme() {
        themeSwitchRepository.applyCurrentTheme()
    }
}