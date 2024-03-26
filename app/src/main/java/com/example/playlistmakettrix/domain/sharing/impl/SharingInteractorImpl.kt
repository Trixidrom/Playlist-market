package com.example.playlistmakettrix.domain.sharing.impl

import com.example.playlistmakettrix.data.sharing.ExternalNavigator
import com.example.playlistmakettrix.domain.sharing.model.EmailData
import com.example.playlistmakettrix.domain.sharing.SharingInteractor

class SharingInteractorImpl(
    private val externalNavigator: ExternalNavigator
) : SharingInteractor {

    companion object {
        private const val TERMS_OF_USE_URL = "https://yandex.ru/legal/practicum_offer/"
        private const val YANDEX_PRACTICUM_URL = "https://practicum.yandex.ru/profile/android-developer/"
        private const val E_MAIL = "trix2006@mail.ru"
    }

    override fun shareApp() {
        externalNavigator.shareLink(getShareAppLink())
    }

    override fun openTerms() {
        externalNavigator.openLink(getTermsLink())
    }

    override fun openSupport() {
        externalNavigator.openEmail(getEmailData())
    }

    private fun getEmailData(): EmailData {
        return EmailData(E_MAIL)
    }

    private fun getTermsLink(): String {
        return TERMS_OF_USE_URL
    }

    private fun getShareAppLink(): String {
        return  YANDEX_PRACTICUM_URL
    }
}