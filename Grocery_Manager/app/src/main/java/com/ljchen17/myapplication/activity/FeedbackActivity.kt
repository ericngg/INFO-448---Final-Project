package com.ljchen17.myapplication.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.preference.PreferenceManager
import com.ljchen17.myapplication.R
import com.muddzdev.styleabletoast.StyleableToast

class FeedbackActivity : AppCompatActivity() {

    private lateinit var btnSubmit: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val name = sharedPreferences.getBoolean("theme",  true)
        if (name) {
            setTheme(R.style.dark)
        } else {
            setTheme(R.style.AppTheme)
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        btnSubmit = findViewById(R.id.btnSubmit)

        btnSubmit.setOnClickListener {
            finish()
            StyleableToast.makeText(this, "Feedback sent successfully", Toast.LENGTH_LONG, R.style.saved).show()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onNavigateUp()
    }
}
