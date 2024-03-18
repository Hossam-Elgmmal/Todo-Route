package com.route.todo.fragments.settings

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.route.todo.R
import com.route.todo.databinding.FragmentSettingsBinding
import java.util.Locale

class SettingsFragment : Fragment() {
    private lateinit var binding: FragmentSettingsBinding
    lateinit var goToTasks: () -> Unit

    companion object {
        val langList = arrayListOf("العربية", "English")
        val modeList = arrayListOf("Night", "Day")
    }

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
        val data = arrayOf(langList[0])
        val adapter = ArrayAdapter(requireContext(), R.layout.menu_item, data)
        binding.chooseLang.setAdapter(adapter)
        binding.chooseLang.setOnItemClickListener { _, _, _, _ ->
            if (langList[0] == "العربية")
                changeLang("ar")
            else if (langList[0] == "English")
                changeLang("en")
        }
    }

    private fun changeLang(lang: String) {
        val locale = Locale(lang)
        Locale.setDefault(locale)
        val configuration = Configuration(context?.resources?.configuration)
        configuration.setLocale(locale)
        langList.add(langList.removeAt(0))
        goToTasks()
        requireContext().resources.updateConfiguration(
            configuration,
            context?.resources?.displayMetrics
        )
        requireActivity().recreate()
    }

    private fun setModeSettings() {
        val data = arrayOf(modeList[0])
        val adapter = ArrayAdapter(requireContext(), R.layout.menu_item, data)
        binding.chooseMode.setAdapter(adapter)
        binding.chooseMode.setOnItemClickListener { _, _, _, _ ->
            if (modeList[0] == "Night") {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else if (modeList[0] == "Day") {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            modeList.add(modeList.removeAt(0))
            goToTasks()
            requireActivity().recreate()
        }
    }
}