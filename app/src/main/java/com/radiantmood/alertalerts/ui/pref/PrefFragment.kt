package com.radiantmood.alertalerts.ui.pref

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SeekBarPreference
import com.radiantmood.alertalerts.R
import com.radiantmood.alertalerts.core.snoozeDurationPref

class PrefFragment : PreferenceFragmentCompat() {

    private val snoozePref: SeekBarPreference? get() = findPreference(snoozeDurationPref)

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
        setupSnoozePref()
    }

    private fun setupSnoozePref() {
        snoozePref?.apply {
            min = 1
            max = 180
            seekBarIncrement = 5
            showSeekBarValue = true
        }
    }
}
