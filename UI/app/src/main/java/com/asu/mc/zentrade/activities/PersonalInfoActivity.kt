package com.asu.mc.zentrade.activities

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import com.asu.mc.zentrade.R
import com.asu.mc.zentrade.models.PersonalInfo
import com.google.android.material.textfield.TextInputEditText

class PersonalInfoActivity : AppCompatActivity() {

    private lateinit var firstNameInput: TextInputEditText
    private lateinit var lastNameInput: TextInputEditText
    private lateinit var ageInput: TextInputEditText
    private lateinit var emailInput: TextInputEditText
    private lateinit var phoneInput: TextInputEditText
    private lateinit var sexRadioGroup: RadioGroup
    private lateinit var radioMale: RadioButton
    private lateinit var radioFemale: RadioButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personal_info)

        initializeViews()
        loadFormData()

        val btnBack = findViewById<Button>(R.id.btnBack)
        val btnNext = findViewById<Button>(R.id.btnNext)

        btnNext.isEnabled = false

        btnBack.setOnClickListener {
            finish()
        }

        btnNext.setOnClickListener {
            if (isFormValid()) {
                val personalInfo = PersonalInfo(
                    firstName = firstNameInput.text.toString(),
                    lastName = lastNameInput.text.toString(),
                    age = ageInput.text.toString().toIntOrNull() ?: 0,
                    email = emailInput.text.toString(),
                    phoneNumber = phoneInput.text.toString(),
                    sex = when(sexRadioGroup.checkedRadioButtonId) {
                        R.id.radioMale -> "Male"
                        R.id.radioFemale -> "Female"
                        else -> ""
                    }
                )
                val intent = Intent(this, InstrumentsToTradeActivity::class.java)
                intent.putExtra("personalInfo", personalInfo)
                startActivity(intent)
            }
        }

        setupTextWatchers()
    }

    private fun initializeViews() {
        firstNameInput = findViewById(R.id.firstNameInput)
        lastNameInput = findViewById(R.id.lastNameInput)
        ageInput = findViewById(R.id.ageInput)
        emailInput = findViewById(R.id.emailInput)
        phoneInput = findViewById(R.id.phoneInput)
        sexRadioGroup = findViewById(R.id.sexRadioGroup)
        radioMale = findViewById(R.id.radioMale)
        radioFemale = findViewById(R.id.radioFemale)
    }

    private fun setupTextWatchers() {
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                findViewById<Button>(R.id.btnNext).isEnabled = isFormValid()
            }

            override fun afterTextChanged(s: Editable) {}
        }

        firstNameInput.addTextChangedListener(textWatcher)
        lastNameInput.addTextChangedListener(textWatcher)
        ageInput.addTextChangedListener(textWatcher)
        emailInput.addTextChangedListener(textWatcher)
        phoneInput.addTextChangedListener(textWatcher)

        sexRadioGroup.setOnCheckedChangeListener { _, _ ->
            findViewById<Button>(R.id.btnNext).isEnabled = isFormValid()
        }
    }

    private fun isFormValid(): Boolean {
        if (firstNameInput.text.isNullOrEmpty()) return false
        if (lastNameInput.text.isNullOrEmpty()) return false
        if (ageInput.text.isNullOrEmpty()) return false
        if (emailInput.text.isNullOrEmpty()) return false
        if (phoneInput.text.isNullOrEmpty()) return false
        if (sexRadioGroup.checkedRadioButtonId == -1) return false
        return true
    }

    private fun saveFormData() {
        val preferences = getSharedPreferences("PersonalInfoPrefs", MODE_PRIVATE)
        preferences.edit().apply {
            putString("firstName", firstNameInput.text.toString())
            putString("lastName", lastNameInput.text.toString())
            putString("age", ageInput.text.toString())
            putString("email", emailInput.text.toString())
            putString("phone", phoneInput.text.toString())
            val selectedSexId = sexRadioGroup.checkedRadioButtonId
            putInt("selectedSex", selectedSexId)
            apply()
        }
    }

    private fun loadFormData() {
        val preferences = getSharedPreferences("PersonalInfoPrefs", MODE_PRIVATE)
        firstNameInput.setText(preferences.getString("firstName", ""))
        lastNameInput.setText(preferences.getString("lastName", ""))
        ageInput.setText(preferences.getString("age", ""))
        emailInput.setText(preferences.getString("email", ""))
        phoneInput.setText(preferences.getString("phone", ""))
        val selectedSexId = preferences.getInt("selectedSex", -1)
        if (selectedSexId != -1) {
            when(selectedSexId) {
                R.id.radioMale -> radioMale.isChecked = true
                R.id.radioFemale -> radioFemale.isChecked = true
            }
        }
    }

    override fun onPause() {
        super.onPause()
        saveFormData()
    }
}
