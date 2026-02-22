package com.example.broadcasting
import android.annotation.SuppressLint
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
class MainActivity : AppCompatActivity() {
    private lateinit var tv: TextView
    private lateinit var pbBattery: ProgressBar
    private var batteryReceiver: BatteryReceiver? = null
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tv = findViewById(R.id.tv1)
        pbBattery = findViewById(R.id.pbBattery)

        batteryReceiver = BatteryReceiver(tv, pbBattery)

        registerReceiver(batteryReceiver, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
    }
    override fun onStop() {
        super.onStop()
        // Unregister receiver safely
        batteryReceiver?.let { unregisterReceiver(it) }
    }
}
