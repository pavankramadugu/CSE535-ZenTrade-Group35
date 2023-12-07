package com.asu.mc.zentrade.activities

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import com.asu.mc.zentrade.R
import com.asu.mc.zentrade.helper.ApiClient
import com.asu.mc.zentrade.helper.RecommendationsApiService
import com.asu.mc.zentrade.models.PersonalInfo
import com.asu.mc.zentrade.models.RecommendationsRequest
import com.asu.mc.zentrade.models.RecommendationsResponse
import com.asu.mc.zentrade.models.UserRequest
import com.asu.mc.zentrade.models.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class RecommendationsActivity : AppCompatActivity() {

    private lateinit var tableRecommendations: TableLayout
    private lateinit var progressDialog: ProgressDialog
    companion object {
        @JvmStatic
        lateinit var finalRecommendations: List<String>
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recommendations)

        val personalInfo = intent.getSerializableExtra("personalInfo") as? PersonalInfo ?: return

        val tvPersonalInfoSummary = findViewById<TextView>(R.id.tvPersonalInfoSummary)
        tvPersonalInfoSummary.text = buildPersonalInfoSummary(personalInfo)

        tableRecommendations = findViewById(R.id.tableRecommendations)
        progressDialog = ProgressDialog(this).apply {
            setMessage("Computing Recommendations...")
            setCancelable(false)
            show()
        }

        val btnClose = findViewById<Button>(R.id.btnClose)
        val btnBack = findViewById<Button>(R.id.btnBack)

        btnClose.setOnClickListener {
            clearAllSharedPreferences(this)
            navigateToMainActivity()
        }

        btnBack.setOnClickListener {
            finish()
        }

        fetchRecommendations(personalInfo)

    }

    private fun fetchRecommendations(personalInfo: PersonalInfo) {
        val apiService = ApiClient.retrofitInstance.create(RecommendationsApiService::class.java)

        val request = RecommendationsRequest(
            frequency = personalInfo.frequency,
            appetite = personalInfo.appetite,
            countries = personalInfo.countries
        )

        apiService.getRecommendations(request).enqueue(object : Callback<RecommendationsResponse> {
            override fun onResponse(
                call: Call<RecommendationsResponse>,
                response: Response<RecommendationsResponse>
            ) {
                progressDialog.dismiss()
                if (response.isSuccessful && response.body() != null) {
                    val recommendations = response.body()!!.recommendations
                    finalRecommendations  = recommendations
                    displayRecommendations(recommendations)
                    create_user_in_db(personalInfo)
                } else {
                    showError("Failed to fetch recommendations")
                }
            }

            override fun onFailure(call: Call<RecommendationsResponse>, t: Throwable) {
                progressDialog.dismiss()
                showError("Network error: " + t.message)
            }
        })
    }

    private fun showError(message: String) {
        AlertDialog.Builder(this)
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .show()
    }

    private fun displayRecommendations(recommendations: List<String>) {
        // Add table headers
        val headerRow = TableRow(this)
        addTextToRow(headerRow, "S.No", true)
        addTextToRow(headerRow, "Instrument", true)
        tableRecommendations.addView(headerRow)

        // Add recommendations to the table
        recommendations.forEachIndexed { index, recommendation ->
            val row = TableRow(this)
            val lp = TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT
            )
            row.layoutParams = lp

            addTextToRow(row, "${index + 1}", false)
            addTextToRow(row, recommendation, false)

            tableRecommendations.addView(row)
        }
    }

    private fun addTextToRow(row: TableRow, text: String, isHeader: Boolean) {
        val textView = TextView(this).apply {
            this.text = text
            textSize = if (isHeader) 18f else 16f
            setPadding(16, 8, 16, 8)
        }
        row.addView(textView)
    }

    private fun buildPersonalInfoSummary(personalInfo: PersonalInfo): String {
        return "Portfolio Factor: ${personalInfo.appetite}\n" +
                "Countries: ${personalInfo.countries.joinToString(", ")}\n" +
                "Trading Frequency: ${personalInfo.frequency}\n" +
                "Stress Level: ${personalInfo.stress}"
    }

    private fun clearAllSharedPreferences(context: Context) {
        val sharedPreferencesPath =
            File(context.filesDir.parentFile!!.absolutePath + File.separator + "shared_prefs")
        sharedPreferencesPath.listFiles()?.forEach { file ->
            context.getSharedPreferences(file.nameWithoutExtension, Context.MODE_PRIVATE)
                .edit { clear() }
        }
    }

    private fun navigateToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun create_user_in_db(personalInfo: PersonalInfo) {

        val apiService = ApiClient.retrofitInstance.create(RecommendationsApiService::class.java)

        val userRequest = UserRequest(
            firstname = personalInfo.firstName,
            lastname = personalInfo.lastName,
            email = personalInfo.email,
            frequency = personalInfo.frequency,
            appetite = personalInfo.appetite,
            stress = personalInfo.stress,
            recommendations = finalRecommendations
        )

        apiService.usercreation(userRequest).enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                progressDialog.dismiss()
                if (response.isSuccessful && response.body() != null) {
                    val message = response.body()!!.message
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                progressDialog.dismiss()
                // Handle failure, such as network errors or other issues
            }
        })
    }
}
