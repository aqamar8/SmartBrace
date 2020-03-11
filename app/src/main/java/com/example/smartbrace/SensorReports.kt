package com.example.smartbrace

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_sensor_reports.*
import kotlinx.android.synthetic.main.content_main.*

class SensorReports : AppCompatActivity () {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sensor_reports)

        //Gyroscope A
        gyroA.setOnClickListener {
            Log.d("SensorReports", "Attempt to access Gyroscope A")
            startActivity(Intent(this, GyroscopeA::class.java))
        }

    }
}