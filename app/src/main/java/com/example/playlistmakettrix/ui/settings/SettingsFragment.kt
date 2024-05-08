package com.example.playlistmakettrix.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.playlistmakettrix.databinding.FragmentSettingsBinding
import com.example.playlistmakettrix.ui.settings.view_model.SettingsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsBinding
    private val settingsViewModel by viewModel<SettingsViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.themeSwitcher
            .apply {
                isChecked = settingsViewModel.isDarkThemeOn()
                setOnCheckedChangeListener { _, isChecked ->
                    settingsViewModel.switchTheme(isChecked)
                }
            }

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