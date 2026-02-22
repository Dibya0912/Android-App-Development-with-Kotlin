package com.example.broadcasting
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.drawable.LayerDrawable
import android.widget.ProgressBar
import android.widget.TextView
class BatteryReceiver(
    private val tv: TextView,
    private val progressBar: ProgressBar
) : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val percentage = intent.getIntExtra("level", 0)
        tv.text = "$percentage%"
        progressBar.progress = percentage
        val batteryDrawable = progressBar.progressDrawable as? LayerDrawable
        val progressDrawable = batteryDrawable?.findDrawableByLayerId(android.R.id.progress)
        progressDrawable?.level = percentage
    }
}