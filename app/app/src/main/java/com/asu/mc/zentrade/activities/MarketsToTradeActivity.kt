package com.asu.mc.zentrade.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import androidx.appcompat.app.AppCompatActivity
import com.asu.mc.zentrade.R
import com.asu.mc.zentrade.models.PersonalInfo

class MarketsToTradeActivity : AppCompatActivity() {

    private lateinit var checkBoxUSA: CheckBox
    private lateinit var checkBoxChina: CheckBox
    private lateinit var checkBoxGermany: CheckBox
    private lateinit var checkBoxIndia: CheckBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_markets_to_trade)

        val personalInfo = intent.getSerializableExtra("personalInfo") as? PersonalInfo

        checkBoxUSA = findViewById(R.id.checkBoxUSA)
        checkBoxChina = findViewById(R.id.checkBoxChina)
        checkBoxGermany = findViewById(R.id.checkBoxGermany)
        checkBoxIndia = findViewById(R.id.checkBoxIndia)

        val checkBoxes = listOf(checkBoxUSA, checkBoxChina, checkBoxGermany, checkBoxIndia)

        val btnNext = findViewById<Button>(R.id.btnNext)
        btnNext.isEnabled = checkBoxes.any { it.isChecked }

        checkBoxes.forEach { checkBox ->
            checkBox.setOnCheckedChangeListener { _, _ ->
                btnNext.isEnabled = checkBoxes.any { it.isChecked }
            }
        }

        val btnBack = findViewById<Button>(R.id.btnBack)
        btnBack.setOnClickListener {
            finish()  // Navigates back to the previous activity
        }

        btnNext.setOnClickListener {
            personalInfo?.countries =
                checkBoxes.filter { it.isChecked }.map { it.text.toString() }.toMutableList()

            val intent = Intent(this, TradingFrequencyAnalyzerActivity::class.java)
            intent.putExtra("personalInfo", personalInfo)
            startActivity(intent)
        }

        loadCheckboxStates()
    }

    private fun saveCheckboxStates() {
        val preferences = getSharedPreferences("MarketsPrefs", MODE_PRIVATE)
        with(preferences.edit()) {
            putBoolean("usaChecked", checkBoxUSA.isChecked)
            putBoolean("chinaChecked", checkBoxChina.isChecked)
            putBoolean("germanyChecked", checkBoxGermany.isChecked)
            putBoolean("indiaChecked", checkBoxIndia.isChecked)
            apply()
        }
    }

    private fun loadCheckboxStates() {
        val preferences = getSharedPreferences("MarketsPrefs", MODE_PRIVATE)
        checkBoxUSA.isChecked = preferences.getBoolean("usaChecked", false)
        checkBoxChina.isChecked = preferences.getBoolean("chinaChecked", false)
        checkBoxGermany.isChecked = preferences.getBoolean("germanyChecked", false)
        checkBoxIndia.isChecked = preferences.getBoolean("indiaChecked", false)
    }

    override fun onPause() {
        super.onPause()
        saveCheckboxStates()
    }
}
