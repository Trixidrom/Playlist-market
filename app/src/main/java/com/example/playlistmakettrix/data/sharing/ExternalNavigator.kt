package com.example.playlistmakettrix.data.sharing

import com.example.playlistmakettrix.domain.sharing.model.EmailData

interface ExternalNavigator {
    fun shareLink(shareAppLink: String)
    fun openLink(termsLink: String)
    fun openEmail(emailData: EmailData)
}