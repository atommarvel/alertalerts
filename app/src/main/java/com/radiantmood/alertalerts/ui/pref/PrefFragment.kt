package com.radiantmood.alertalerts.ui.pref

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.radiantmood.alertalerts.R

class PrefFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
    }
}
