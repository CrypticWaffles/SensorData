package com.prog.sensordata

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.widget.TextView

class MainActivity : AppCompatActivity(), SensorEventListener {
    //Declare sensor manager & variables
    private lateinit var sensorManager: SensorManager
    private var accelerometer: Sensor? = null
    private var gyroscope: Sensor? = null
    private var magnetometer: Sensor? = null
    private var light: Sensor? = null

    //Declare text views to display sensor data
    private lateinit var accelerometerTextView: TextView
    private lateinit var gyroscopeTextView: TextView
    private lateinit var magnetometerTextView: TextView
    private lateinit var lightTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Initialize TextViews
        accelerometerTextView = findViewById(R.id.accelerometerTextView)
        gyroscopeTextView = findViewById(R.id.gyroscopeTextView)
        magnetometerTextView = findViewById(R.id.magnetometerTextView)
        lightTextView = findViewById(R.id.lightTextView)

        //Initialize Sensor Manager & get in-use sensors
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
        magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
        light = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
    }

    override fun onResume() {
        super.onResume()

        //Register sensor listener when the activity resumes
        accelerometer?.also { sensor ->
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
        }
        gyroscope?.also { sensor ->
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
        }
        magnetometer?.also { sensor ->
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
        }
        light?.also { sensor ->
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onPause() {
        super.onPause()

        //Unregister sensor listeners when activity pauses
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {
            when (it.sensor.type) {
                Sensor.TYPE_ACCELEROMETER -> {
                    accelerometerTextView.text = getString(R.string.accelerometer_text,
                        it.values[0], it.values[1], it.values[2])
                }
                Sensor.TYPE_GYROSCOPE -> {
                    gyroscopeTextView.text = getString(R.string.gyroscope_text,
                        it.values[0], it.values[1], it.values[2])
                }
                Sensor.TYPE_MAGNETIC_FIELD -> {
                    magnetometerTextView.text = getString(R.string.magnetometer_text,
                        it.values[0], it.values[1], it.values[2])
                }
                Sensor.TYPE_LIGHT -> {
                    lightTextView.text = getString(R.string.light_text, it.values[0])
                }
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        //Do nothing
    }

}