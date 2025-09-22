package com.example.myintentapp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intent_main)

        val btnExplicit = findViewById<Button>(R.id.btnExplicit)
        val btnImplicit = findViewById<Button>(R.id.btnImplicit)

        // Explicit Intent → Open SecondActivity
        btnExplicit.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)
        }

        // Implicit Intent → Open Google in Browser
        btnImplicit.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://chatgpt.com/"))
            startActivity(intent)
        }
    }
}
