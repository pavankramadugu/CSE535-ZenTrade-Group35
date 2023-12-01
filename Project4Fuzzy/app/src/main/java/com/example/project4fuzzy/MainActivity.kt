package com.example.project4fuzzy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fuz = fuzzyEvaluate(this)
        val portfolioFactor = fuz.fuzzy("medium", Random.nextInt(50,130),Random.nextInt(10,30))
        val textView = findViewById<TextView>(R.id.TextView1)

        textView.text = portfolioFactor
    }
}