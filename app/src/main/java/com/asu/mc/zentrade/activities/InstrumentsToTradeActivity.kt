package com.asu.mc.zentrade.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import androidx.appcompat.app.AppCompatActivity
import com.asu.mc.zentrade.R
import com.asu.mc.zentrade.models.PersonalInfo

class InstrumentsToTradeActivity : AppCompatActivity() {

    @SuppressLint("CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_instruments_to_trade)

        loadCheckboxStates()

        val personalInfo = intent.getSerializableExtra("personalInfo") as? PersonalInfo

        val checkBoxStocks = findViewById<CheckBox>(R.id.checkBoxStocks)
        val checkBoxBonds = findViewById<CheckBox>(R.id.checkBoxBonds)
        val checkBoxPreciousMetals = findViewById<CheckBox>(R.id.checkBoxPreciousMetals)
        val checkBoxGlobalMarketETFs = findViewById<CheckBox>(R.id.checkBoxGlobalMarketETFs)

        val checkBoxes = listOf(checkBoxStocks, checkBoxBonds, checkBoxPreciousMetals, checkBoxGlobalMarketETFs)
        findViewById<Button>(R.id.btnNext).isEnabled = checkBoxes.any { it.isChecked }
        checkBoxes.forEach { checkBox ->
            checkBox.setOnCheckedChangeListener { _, _ ->
                findViewById<Button>(R.id.btnNext).isEnabled = checkBoxes.any { it.isChecked }
            }
        }

        val btnBack = findViewById<Button>(R.id.btnBack)
        btnBack.setOnClickListener {
            finish()  // Navigates back to PersonalInfoActivity
        }

        val btnNext = findViewById<Button>(R.id.btnNext)
        btnNext.setOnClickListener {
            personalInfo?.instruments =
                checkBoxes.filter { it.isChecked }.map { it.text.toString() }.toMutableList()
            val intent = Intent(this, RiskAppetiteActivity::class.java)
            intent.putExtra("personalInfo", personalInfo)
            startActivity(intent)
        }
    }

    private fun loadCheckboxStates() {
        val preferences = getSharedPreferences("InstrumentsPrefs", MODE_PRIVATE)
        findViewById<CheckBox>(R.id.checkBoxStocks).isChecked = preferences.getBoolean("stocksChecked", false)
        findViewById<CheckBox>(R.id.checkBoxBonds).isChecked = preferences.getBoolean("bondsChecked", false)
        findViewById<CheckBox>(R.id.checkBoxPreciousMetals).isChecked = preferences.getBoolean("preciousMetalsChecked", false)
        findViewById<CheckBox>(R.id.checkBoxGlobalMarketETFs).isChecked = preferences.getBoolean("globalMarketETFsChecked", false)
    }

    private fun saveCheckboxStates() {
        val preferences = getSharedPreferences("InstrumentsPrefs", MODE_PRIVATE)
        with(preferences.edit()) {
            putBoolean("stocksChecked", findViewById<CheckBox>(R.id.checkBoxStocks).isChecked)
            putBoolean("bondsChecked", findViewById<CheckBox>(R.id.checkBoxBonds).isChecked)
            putBoolean("preciousMetalsChecked", findViewById<CheckBox>(R.id.checkBoxPreciousMetals).isChecked)
            putBoolean("globalMarketETFsChecked", findViewById<CheckBox>(R.id.checkBoxGlobalMarketETFs).isChecked)
            apply()
        }
    }

    override fun onPause() {
        super.onPause()
        saveCheckboxStates()
    }
}
