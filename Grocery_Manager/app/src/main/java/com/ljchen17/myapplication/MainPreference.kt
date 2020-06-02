package com.ljchen17.myapplication

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat

class MainPreference : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preference, rootKey)
    }
}