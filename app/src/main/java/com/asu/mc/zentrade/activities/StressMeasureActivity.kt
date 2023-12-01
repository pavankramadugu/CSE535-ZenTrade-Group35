package com.asu.mc.zentrade.activities

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.asu.mc.zentrade.R
import com.asu.mc.zentrade.models.PersonalInfo
import kotlin.random.Random

class StressMeasureActivity : AppCompatActivity() {

    private lateinit var personalInfo: PersonalInfo
    private lateinit var btnNext: Button
    private lateinit var progressDialog: ProgressDialog
    private lateinit var handler: Handler
    private var currentMessageIndex = 0

    private val messages = arrayOf(
        "Retrieving Heart rate from your watch sensors...",
        "Retrieving Respiratory rate from watch sensors...",
        "Retrieving Sleep Time and Sleep Quality from watch sensors..."
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stress_measure)

        personalInfo = intent.getSerializableExtra("personalInfo") as? PersonalInfo ?: return

        val btnMeasureStress = findViewById<Button>(R.id.btnMeasureStress)
        btnNext = findViewById<Button>(R.id.btnNext)
        val btnBack = findViewById<Button>(R.id.btnBack)

        progressDialog = ProgressDialog(this)
        handler = Handler(mainLooper)

        btnMeasureStress.setOnClickListener {
            currentMessageIndex = 0
            showProgressDialog()
        }

        btnBack.setOnClickListener {
            finish()
        }

        btnNext.setOnClickListener {
            val intent = Intent(this, RecommendationsActivity::class.java)
            intent.putExtra("personalInfo", personalInfo)
            startActivity(intent)
        }

        btnNext.isEnabled = false
    }

    private fun showProgressDialog() {
        if (currentMessageIndex < messages.size) {
            progressDialog.setMessage(messages[currentMessageIndex])
            progressDialog.setCancelable(false)
            progressDialog.show()

            handler.postDelayed({
                currentMessageIndex++
                progressDialog.dismiss()
                showProgressDialog()
            }, 3000) // Delay for 3 seconds
        } else {
            determineStressLevel()
        }
    }

    private fun determineStressLevel() {
        val stressLevels = arrayOf("Low", "Medium", "High")
        val selectedStress = stressLevels[Random.nextInt(stressLevels.size)]
        personalInfo.stress = selectedStress
        showStressResult(selectedStress)
    }

    private fun showStressResult(stressLevel: String) {
        AlertDialog.Builder(this)
            .setMessage("Stress Level: $stressLevel")
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
                btnNext.isEnabled = true
            }
            .setCancelable(false)
            .show()
    }
}
