package com.example.hp.sensors

import android.content.Context
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() , SensorEventListener {

    val sensorManager : SensorManager by lazy {
        getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

    lateinit var gravitySensorValues : FloatArray

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

    override fun onSensorChanged(event: SensorEvent?) {

        event?.let {sensorEvent ->

            sensorEvent.values.forEachIndexed { index, fl ->

                gravitySensorValues = sensorEvent.values
                val x = Math.abs(gravitySensorValues[0])
                val y = Math.abs(gravitySensorValues[1])
                val z = Math.abs(gravitySensorValues[2])

                // Scale the value of sensors from 0-9.8 to 0-255
                // i.e. 0 in gravity means 0 in RGB
                // 9.8 in gravity means 255 in RGB

                val red :Int= ((x*255)/9.8).toInt()
                val green:Int = ((y*255)/9.8).toInt()
                val blue:Int = ((z*255)/9.8).toInt()

                val color = Color.rgb(red , green ,blue)

                bg.setBackgroundColor(color)

            }

        }


    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val accelerometer: Sensor =sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

  //      val sensors : List<Sensor> = sensorManager.getSensorList(Sensor.TYPE_ALL)
//        Log.e("TAG" , "number of sensors present are ${sensors.size}")
//
 //      for(s in sensors){
//
//            Log.e("TAG" , "------------")
////            Log.e("TAG" , "Sensor's name is ${s.name}")
////            Log.e("TAG" , "Sensor's power is ${s.power}")
////            Log.e("TAG" , "Sensor's type is ${s.type}")
////            Log.e("TAG" , "Sensor's vendor is ${s.vendor}")
////            Log.e("TAG" , "Sensor's version is ${s.version}")
////            Log.e("TAG" , "Sensor's max Delay is ${s.maxDelay}")
////            Log.e("TAG" , "Sensor's max Range  is ${s.maximumRange}")
////            Log.e("TAG" , "Sensor's min Delay is ${s.minDelay}")
////            Log.e("TAG" , "Sensor's resolution is ${s.resolution}")
////            Log.e("TAG" , "Sensor's max event count is ${s.fifoMaxEventCount}")
////            Log.e("TAG" , "---")
//            Log.e("TAG" , s.toString())
//            Log.e("TAG" , "------------")
//
 //  }


    }

    override fun onStart() {
        super.onStart()
        val gravity = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY)

        sensorManager.registerListener(this , gravity , 50000000)

    }

    override fun onStop() {
        super.onStop()
        sensorManager.unregisterListener(this)
    }
}
