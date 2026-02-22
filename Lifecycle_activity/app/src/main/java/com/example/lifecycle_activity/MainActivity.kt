package com.example.lifecycle_activity

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private val TAG = "LifecycleActivity"
    private lateinit var lifecycleTextView: TextView

    private fun updateLifecycleState(state: String) {
        lifecycleTextView.text = "Current Lifecycle State: $state"
        Log.d(TAG, "$state called")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        lifecycleTextView = findViewById(R.id.lifecycleTextView)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        updateLifecycleState("onCreate")
    }

    override fun onStart() {
        super.onStart()
        updateLifecycleState("onStart")
    }

    override fun onResume() {
        super.onResume()
        updateLifecycleState("onResume")
    }

    override fun onPause() {
        super.onPause()
        updateLifecycleState("onPause")
    }

    override fun onStop() {
        super.onStop()
        updateLifecycleState("onStop")
    }

    override fun onRestart() {
        super.onRestart()
        updateLifecycleState("onRestart")
    }

    override fun onDestroy() {
        super.onDestroy()
        updateLifecycleState("onDestroy")
    }
}