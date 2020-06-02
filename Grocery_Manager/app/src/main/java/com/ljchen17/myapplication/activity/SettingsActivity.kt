package com.ljchen17.myapplication.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.ljchen17.myapplication.MainPreference
import com.ljchen17.myapplication.R

class SettingsActivity : AppCompatActivity(), PreferenceFragmentCompat.OnPreferenceStartFragmentCallback {

    companion object  {
        private val TAG = "SETTINGS_ACTIVITY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        if(savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.flSettingContent, MainPreference())
                .commit()
        } else {
            title = savedInstanceState.getCharSequence(TAG)
        }

        supportFragmentManager.addOnBackStackChangedListener {
            if (supportFragmentManager.backStackEntryCount == 0) {
                title = "Settings"
            }
        }

        setUpToolbar()
    }

    private fun setUpToolbar() {
        supportActionBar?.title = "Settings"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState.putCharSequence(TAG, title)
    }

    override fun onSupportNavigateUp(): Boolean {
        Log.i("test", "test")
        supportFragmentManager.popBackStack()

        return super.onSupportNavigateUp()
    }




    override fun onPreferenceStartFragment(
        caller: PreferenceFragmentCompat?,
        pref: Preference?
    ): Boolean {
        val arg = pref?.extras
        val fragment = pref?.fragment?.let { frag ->
            supportFragmentManager.fragmentFactory.instantiate(
                classLoader, frag
            ).apply {
                arguments = arg
                setTargetFragment(caller, 0)
            }
        }

        fragment?.let { frag ->
            supportFragmentManager.beginTransaction()
                .replace(R.id.flSettingContent, frag)
                .addToBackStack(TAG)
                .commit()
        }

        title = pref?.title
        return true

    }
}
