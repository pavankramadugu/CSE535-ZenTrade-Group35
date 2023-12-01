package com.example.project4fuzzy

import org.junit.Assert
import org.junit.Before
import org.junit.Test
import com.example.project4fuzzy.fuzzyEvaluate
//import androidx.test.core.app.ApplicationProvider
import com.example.project4fuzzy.R
import net.sourceforge.jFuzzyLogic.FIS
import org.junit.Assert.assertEquals
import java.io.InputStream

class fuzzyEvaluateTest {

        private lateinit var fuzzyEvaluate: fuzzyEvaluate

        @Before
        fun setUp() {
            val mainActivity = MainActivity()
            fuzzyEvaluate = fuzzyEvaluate(mainActivity)
        }

        @Test
        fun testFuzzyLowRisk() {
            val result = fuzzyEvaluate.fuzzy("low", 70, 18)
            Assert.assertEquals("Low", result)
        }

        @Test
        fun testFuzzyMediumRisk() {
            val result = fuzzyEvaluate.fuzzy("medium", 130, 20)
            Assert.assertEquals("Low", result)
        }

        @Test
        fun testFuzzyHighRisk() {
            val result = fuzzyEvaluate.fuzzy("high", 90, 22)
            Assert.assertEquals("High", result)
        }

        @Test
        fun testFuzzyMedRisk() {
            val result = fuzzyEvaluate.fuzzy("medium", 90, 14)
            Assert.assertEquals("Medium", result)
        }

}