package com.asu.mc.zentrade.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import com.asu.mc.zentrade.R
import com.asu.mc.zentrade.models.PersonalInfo

class RiskAppetiteActivity : AppCompatActivity() {

    private lateinit var riskRadioGroup: RadioGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_risk_appetite)

        val personalInfo = intent.getSerializableExtra("personalInfo") as? PersonalInfo
        riskRadioGroup = findViewById<RadioGroup>(R.id.riskRadioGroup)
        val btnNext = findViewById<Button>(R.id.btnNext)

        riskRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            btnNext.isEnabled = checkedId != -1 // Enable Next button if any radio button is checked
            val selectedRadioButton = findViewById<RadioButton>(checkedId)
            if (selectedRadioButton != null) {
                personalInfo?.appetite = selectedRadioButton.text.toString()
            }
        }

        btnNext.isEnabled = riskRadioGroup.checkedRadioButtonId != -1 // Initial state of the Next button
        btnNext.setOnClickListener {
            val intent = Intent(this, MarketsToTradeActivity::class.java)
            intent.putExtra("personalInfo", personalInfo)
            startActivity(intent)
        }

        val btnBack = findViewById<Button>(R.id.btnBack)
        btnBack.setOnClickListener {
            finish() // Navigates back to PersonalInfoActivity
        }

        loadCheckboxStates() // Load saved state
    }

    private fun saveCheckboxStates() {
        val preferences = getSharedPreferences("RiskAppetitePrefs", MODE_PRIVATE)
        with(preferences.edit()) {
            putInt("selectedRisk", riskRadioGroup.checkedRadioButtonId)
            apply()
        }
    }

    private fun loadCheckboxStates() {
        val preferences = getSharedPreferences("RiskAppetitePrefs", MODE_PRIVATE)
        val selectedRiskId = preferences.getInt("selectedRisk", -1)
        if (selectedRiskId != -1) {
            riskRadioGroup.check(selectedRiskId)
        }
    }

    override fun onPause() {
        super.onPause()
        saveCheckboxStates()
    }
}
