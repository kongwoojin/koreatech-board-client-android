package com.kongjak.koreatechboard.fragment

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.kongjak.koreatechboard.R


class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }
}