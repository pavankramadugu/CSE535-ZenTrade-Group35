package com.asu.mc.zentrade.sensing

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.asu.mc.zentrade.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

class RespRateFragment : Fragment(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var accelValuesX = mutableListOf<Float>()
    private var accelValuesY = mutableListOf<Float>()
    private var accelValuesZ = mutableListOf<Float>()
    private lateinit var startCaptureButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_resp_rate, container, false)

        sensorManager = activity?.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val progressDialog = ProgressDialog(context)
        startCaptureButton = view.findViewById(R.id.startCaptureButton)


        startCaptureButton.setOnClickListener {
            it.isEnabled = false
            accelValuesX.clear()
            accelValuesY.clear()
            accelValuesZ.clear()

            val accelSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
            sensorManager.registerListener(this, accelSensor, SensorManager.SENSOR_DELAY_NORMAL)

            object : CountDownTimer(45000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    startCaptureButton.text = "${millisUntilFinished / 1000} secs left"
                }

                override fun onFinish() {
                    sensorManager.unregisterListener(this@RespRateFragment)

                    progressDialog.show()
                    CoroutineScope(Dispatchers.Main).launch {
                        val rate = captureAndCalculateRate()
                        withContext(Dispatchers.IO) {
                        }
                        progressDialog.dismiss()
                        AlertDialog.Builder(context)
                            .setTitle("Result")
                            .setMessage("Your Resp Rate is: $rate")
                            .setPositiveButton("OK") { _, _ ->
                            }
                            .show()
                        it.isEnabled = true
                        startCaptureButton.text = "Start Capture"
                    }
                }

            }.start()
        }

        return view
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {
            accelValuesX.add(it.values[0])
            accelValuesY.add(it.values[1])
            accelValuesZ.add(it.values[2])
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

    private fun captureAndCalculateRate(): Int {
        var previousValue = 0f
        var k = 0
        previousValue = 10f

        for (i in 11 until minOf(accelValuesX.size, accelValuesY.size, accelValuesZ.size)) {
            val currentValue = sqrt(
                accelValuesX[i].toDouble().pow(2.0) +
                        accelValuesY[i].toDouble().pow(2.0) +
                        accelValuesZ[i].toDouble().pow(2.0)
            ).toFloat()

            if (abs(previousValue - currentValue) > 0.15) {
                k++
            }
            previousValue = currentValue
        }

        val ret = k / 45.00
        return (ret * 30).toInt()
    }
}
