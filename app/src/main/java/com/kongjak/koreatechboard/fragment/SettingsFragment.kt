package com.kongjak.koreatechboard.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.kongjak.koreatechboard.R


class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }

    override fun onPreferenceTreeClick(preference: Preference?): Boolean {
        when (preference?.key) {
            "license" -> {
                startActivity(Intent(context, OssLicensesMenuActivity::class.java))
            }
            "source_code" -> {
                val intent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://github.com/kongwoojin/koreatech_board_client_android")
                )
                startActivity(intent)
            }
        }
        return super.onPreferenceTreeClick(preference)
    }
}