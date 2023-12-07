package com.asu.mc.zentrade.fuzzy

import com.asu.mc.zentrade.activities.*
import android.content.res.Resources
import net.sourceforge.jFuzzyLogic.FIS
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.asu.mc.zentrade.R
import java.io.IOException
import java.io.InputStream

class fuzzyEvaluate(private val mainActivity: RiskAppetiteActivity) : AppCompatActivity() {
    private val context: Context = mainActivity

    fun fuzzy(riskAppetite: String, heartRate: Int, respiratoryRate: Int): String {
        var risk = 0.0
        var portfolioFactor = ""

        // Initialize FIS
        try {
            val inputStream: InputStream = context.resources.openRawResource(R.raw.fuzzycode)
            val fis: FIS? = FIS.load(inputStream, true)

            val riskAppetiteLower = riskAppetite.lowercase()
            when (riskAppetiteLower) {
                "low" -> risk = 30.0
                "medium" -> risk = 60.0
                "high" -> risk = 90.0
            }

            // Set inputs
            fis?.let {
                it.setVariable("Risk", risk)
                it.setVariable("HeartRate", heartRate.toDouble())
                it.setVariable("RespiratoryRate", respiratoryRate.toDouble())

                // Evaluate
                it.evaluate()

                // Get output
                val outputVariable = it.getVariable("Factor").value

                portfolioFactor = when {
                    outputVariable <= 35 -> "Low"
                    outputVariable <= 65 -> "Medium"
                    else -> "High"
                }

                // Display output in TextView (Assuming textViewOutput is a TextView object)
                return portfolioFactor
            }
        } catch (e: IOException) {
            return e.toString()
        } catch (e: Exception) {
            return e.toString()
        }
        return "FIS NULL"
    }

}