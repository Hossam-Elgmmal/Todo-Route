package com.route.todo.fragments.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.fragment.app.Fragment
import com.route.todo.R
import com.route.todo.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {
    private lateinit var binding: FragmentSettingsBinding
    lateinit var goToTasks: () -> Unit

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setLanguageSettings()
        setModeSettings()
    }

    private fun setLanguageSettings() {
        val data = arrayOf(getString(R.string.lang_name))
        val adapter = ArrayAdapter(requireContext(), R.layout.menu_item, data)
        binding.chooseLang.setAdapter(adapter)
        binding.chooseLang.setOnItemClickListener { _, _, _, _ ->
            changeLang(getString(R.string.lang_code))
        }
    }

    private fun changeLang(lang: String) {

        val appLocale = LocaleListCompat.forLanguageTags(lang)
        AppCompatDelegate.setApplicationLocales(appLocale)
        goToTasks()
    }

    private fun setModeSettings() {

        val altMode = arrayOf(getString(R.string.alt_mode_text))
        val adapter = ArrayAdapter(requireContext(), R.layout.menu_item, altMode)
        binding.chooseMode.setAdapter(adapter)
        binding.chooseMode.setOnItemClickListener { _, _, _, _ ->

            if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_NO) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            goToTasks()
        }
    }
}