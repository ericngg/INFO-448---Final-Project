package com.ljchen17.myapplication.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.InputType
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.*
import com.ljchen17.myapplication.FeedbackActivity
import com.ljchen17.myapplication.R
import com.muddzdev.styleabletoast.StyleableToast
import java.util.*
import kotlin.concurrent.schedule
import kotlin.system.exitProcess


class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val name = sharedPreferences.getBoolean("theme",  true)
        if (name) {
            setTheme(R.style.dark)
        } else {
            setTheme(R.style.AppTheme)
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.settings, SettingsFragment())
            .commit()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    override fun onSupportNavigateUp(): Boolean {
        startActivity(Intent(this, ComposeActivity::class.java))
        finish()
        return super.onNavigateUp()
    }

}

class SettingsFragment : PreferenceFragmentCompat() {
    private lateinit var cont: Context;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        cont = this.requireContext()

        val theme: SwitchPreferenceCompat? = findPreference("theme")
        theme?.setOnPreferenceChangeListener { preference, newValue ->
            if (newValue as Boolean) {
                context?.let { cont ->
                    StyleableToast.makeText(cont, "Dark theme applied", Toast.LENGTH_SHORT, R.style.themeToast).show()
                }
            } else {
                context?.let { cont ->
                    StyleableToast.makeText(cont, "Light theme applied", Toast.LENGTH_SHORT, R.style.themeToast).show()
                }
            }
            true
        }

        val signout: Preference? = findPreference("signout")
        signout?.setOnPreferenceClickListener {

            StyleableToast.makeText(cont, "Exiting App..", Toast.LENGTH_SHORT, R.style.quitToast).show()
            Timer("Quit", false).schedule(5000) {
                exitProcess(0);
            }

            true
        }

        val feedback: Preference? = findPreference("feedback")
        feedback?.setOnPreferenceClickListener {
            startActivity(Intent(context, FeedbackActivity::class.java))

            true
        }

    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

        val phone: EditTextPreference? = findPreference("phoneNumber")

        phone?.setOnBindEditTextListener { editText ->
            editText.inputType = InputType.TYPE_CLASS_NUMBER
        }
    }
}