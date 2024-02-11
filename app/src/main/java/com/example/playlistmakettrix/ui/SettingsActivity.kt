package com.example.playlistmakettrix.ui

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.playlistmakettrix.App
import com.example.playlistmakettrix.GeneralConstants
import com.example.playlistmakettrix.R
import com.example.playlistmakettrix.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {

    companion object {
        private const val TERMS_OF_USE_URL = "https://yandex.ru/legal/practicum_offer/"
        private const val YANDEX_PRACTICUM_URL = "https://practicum.yandex.ru/profile/android-developer/"
        private const val E_MAIL = "trix2006@mail.ru"
    }

    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.topAppBar.setNavigationOnClickListener{
            this.finish()
        }
        binding.themeSwitcher.setOnCheckedChangeListener { switcher, checked ->
            (applicationContext as App).switchTheme(checked)

            getSharedPreferences(GeneralConstants.PLAY_LIST_MAKET_SHARED_PREFF, MODE_PRIVATE)
                .edit()
                .putBoolean(GeneralConstants.MODE_DARK, checked)
                .apply()
        }
        binding.themeSwitcher.isChecked = (applicationContext as App).darkTheme

        binding.shareApp.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND).apply {
                putExtra(Intent.EXTRA_TEXT, YANDEX_PRACTICUM_URL)
                type = "text/plain"
            }
            startActivity(intent)
        }
        binding.writeToSupport.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + E_MAIL)).apply {
                putExtra(Intent.EXTRA_SUBJECT, getString(R.string.letter_subject))
                putExtra(Intent.EXTRA_TEXT, getString(R.string.letter_body))
            }
            startActivity(intent)
        }
        binding.termsOfUse.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(TERMS_OF_USE_URL))
            startActivity(intent)
        }
    }
}