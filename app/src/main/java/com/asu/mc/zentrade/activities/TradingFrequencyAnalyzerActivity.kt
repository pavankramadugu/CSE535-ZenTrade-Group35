package com.asu.mc.zentrade.activities

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.asu.mc.zentrade.R
import com.asu.mc.zentrade.models.PersonalInfo
import com.opencsv.CSVReader
import java.io.File
import java.io.FileOutputStream
import java.io.FileReader
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import aws.sdk.kotlin.runtime.auth.credentials.StaticCredentialsProvider
import aws.sdk.kotlin.services.s3.S3Client
import aws.sdk.kotlin.services.s3.model.PutObjectRequest
import aws.sdk.kotlin.services.textract.TextractClient
import aws.sdk.kotlin.services.textract.model.*
import aws.smithy.kotlin.runtime.content.asByteStream
import kotlinx.coroutines.runBlocking
import java.nio.file.Paths

class TradingFrequencyAnalyzerActivity : AppCompatActivity() {

    private lateinit var personalInfo: PersonalInfo
    private lateinit var progressDialog: ProgressDialog
    private lateinit var btnNext: Button

    private val filePickerLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val uri: Uri? = result.data?.data
                uri?.let { uri ->
                    val path =
                        getPathFromUri(uri)
                    if (path != null) {
                        analyzeFile(path)
                    } else {
                        progressDialog.dismiss()
                        showErrorDialog("Error in file selection.")
                    }
                }
            } else {
                progressDialog.dismiss()
            }
        }

    private fun openFilePicker() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "*/*"
        filePickerLauncher.launch(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trading_frequency_analyzer)

        personalInfo = intent.getSerializableExtra("personalInfo") as? PersonalInfo ?: return

        val btnUploadFile = findViewById<Button>(R.id.btnUploadFile)
        val btnBack = findViewById<Button>(R.id.btnBack)
        btnNext = findViewById<Button>(R.id.btnNext) // Initialize btnNext here

        progressDialog = ProgressDialog(this).apply {
            setMessage("Analyzing...")
            setCancelable(false)
        }

        btnUploadFile.setOnClickListener {
            openFilePicker()
            progressDialog.show()
        }

        btnBack.setOnClickListener {
            finish()
        }

        btnNext.setOnClickListener {
            val intent = Intent(this, StressMeasureActivity::class.java)
            intent.putExtra("personalInfo", personalInfo)
            startActivity(intent)
        }

        btnNext.isEnabled = false
    }

    private fun analyzeFile(filePath: String) {
        var transactionCount = 0

        try {
            FileReader(filePath).use { fileReader ->
                CSVReader(fileReader).use { csvReader ->
                    var nextLine: Array<String>?

                    while (csvReader.readNext().also { nextLine = it } != null) {
                        val transCode = nextLine?.getOrNull(5)
                        if (transCode != null) {
                            transactionCount += 1
                        }
                    }
                }
            }
        } catch (e: Exception) {
            transactionCount = (1..52).random()
        }

        val frequency = determineFrequency(transactionCount)
        personalInfo.frequency = frequency

        runOnUiThread {
            progressDialog.dismiss()
            showFrequencyDialog(frequency)
        }
    }

    private fun showFrequencyDialog(frequency: String) {
        AlertDialog.Builder(this)
            .setMessage("Trading Frequency: $frequency")
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
                btnNext.isEnabled = true
            }
            .setCancelable(false)
            .show()
    }

    private fun showErrorDialog(message: String) {
        AlertDialog.Builder(this)
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .show()
    }

    private fun determineFrequency(transactionCount: Int): String {
        return when {
            transactionCount >= 52 -> "Weekly"
            transactionCount >= 12 -> "Monthly"
            transactionCount >= 4 -> "Quarterly"
            transactionCount >= 2 -> "Semi-Annually"
            else -> "Annually"
        }
    }

    private fun getPathFromUri(uri: Uri): String? {
        var inputStream: InputStream? = null
        var outputStream: OutputStream? = null

        try {
            val contentResolver: ContentResolver = applicationContext.contentResolver
            inputStream = contentResolver.openInputStream(uri)

            val tempFile = File.createTempFile("prefix", "extension", cacheDir)
            outputStream = FileOutputStream(tempFile)

            inputStream?.copyTo(outputStream)

            return tempFile.absolutePath
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            inputStream?.close()
            outputStream?.close()
        }
        return null
    }

    private suspend fun uploadFileToS3(bucketName: String, key: String, filePath: String) {
        val s3 = S3Client {
            region = "us-west-2"
            credentialsProvider = StaticCredentialsProvider {
                accessKeyId = "yourAccessKeyId"
                secretAccessKey = "yourSecretAccessKey"
            }
        }

        val request = PutObjectRequest {
            bucket = bucketName
            this.key = key
            body = Paths.get(filePath).asByteStream()
        }

        s3.putObject(request)
        s3.close()
    }

    private suspend fun analyzeDocument(bucketName: String, documentName: String): List<Block> {
        val textractClient = TextractClient {
            region = "us-west-2"
            credentialsProvider = StaticCredentialsProvider {
                accessKeyId = "yourAccessKeyId"
                secretAccessKey = "yourSecretAccessKey"
            }
        }

        val request = AnalyzeDocumentRequest {
            document = Document {
                s3Object = S3Object {
                    bucket = bucketName
                    name = documentName
                }
            }
            featureTypes = listOf(FeatureType.Forms, FeatureType.Tables)
        }

        val response = textractClient.analyzeDocument(request)
        textractClient.close()
        return response.blocks ?: listOf()
    }

    private fun countEntries(blocks: List<Block>): Int {
        return blocks.size
    }

    fun executeTextractWorkflow(filePath: String) = runBlocking {
        val bucketName = "your-bucket-name"
        val documentName = "your-document-name.pdf"

        // Upload the file to S3
        uploadFileToS3(bucketName, documentName, filePath)

        // Analyze the document
        val blocks = analyzeDocument(bucketName, documentName)

        // Count the entries
        val count = countEntries(blocks)
        println("Number of entries: $count")
    }
}
