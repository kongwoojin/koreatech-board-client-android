package com.kongjak.koreatechboard.ui.fragment

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.google.android.material.snackbar.Snackbar
import com.kongjak.koreatechboard.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }

    override fun onPreferenceTreeClick(preference: Preference): Boolean {
        when (preference.key) {
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
            "mail" -> {
                val intent = Intent(Intent.ACTION_SENDTO)
                intent.data = Uri.parse("mailto:")
                intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(getString(R.string.mail_address)))
                intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.mail_subject))
                intent.putExtra(
                    Intent.EXTRA_TEXT,
                    getString(R.string.mail_text, Build.MODEL, Build.VERSION.SDK_INT)
                )
                try {
                    startActivity(intent)
                } catch (e: ActivityNotFoundException) {
                    Snackbar.make(
                        requireView(),
                        getString(R.string.mail_app_not_found),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
        return super.onPreferenceTreeClick(preference)
    }
}
