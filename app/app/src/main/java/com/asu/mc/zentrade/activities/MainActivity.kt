package com.asu.mc.zentrade.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.asu.mc.zentrade.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonGetStarted = findViewById<Button>(R.id.buttonGetStarted)
        buttonGetStarted.setOnClickListener {
            buttonGetStarted.setOnClickListener {
                val intent = Intent(this, PersonalInfoActivity::class.java)
                startActivity(intent)
            }
        }
    }
}
