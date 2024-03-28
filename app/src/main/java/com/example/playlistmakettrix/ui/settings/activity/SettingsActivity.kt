package com.example.playlistmakettrix.ui.settings.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.playlistmakettrix.App
import com.example.playlistmakettrix.GeneralConstants
import com.example.playlistmakettrix.databinding.ActivitySettingsBinding
import com.example.playlistmakettrix.ui.settings.view_model.SettingsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding
    private val settingsViewModel by viewModel<SettingsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.topAppBar.setNavigationOnClickListener{
            this.finish()
        }
        binding.themeSwitcher.setOnCheckedChangeListener { _, checked ->
            (applicationContext as App).switchTheme(checked)

            getSharedPreferences(GeneralConstants.PLAY_LIST_MAKET_SHARED_PREFF, MODE_PRIVATE)
                .edit()
                .putBoolean(GeneralConstants.MODE_DARK, checked)
                .apply()
        }
        binding.themeSwitcher.isChecked = (applicationContext as App).darkTheme

        binding.shareApp.setOnClickListener {
            settingsViewModel.shareApp()
        }
        binding.writeToSupport.setOnClickListener {
            settingsViewModel.openSupport()
        }
        binding.termsOfUse.setOnClickListener {
            settingsViewModel.openTerms()
        }
    }
}