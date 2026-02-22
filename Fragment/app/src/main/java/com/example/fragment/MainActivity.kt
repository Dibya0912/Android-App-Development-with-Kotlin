package com.example.fragment

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnNews: Button = findViewById(R.id.btnNews)
        val btnSports: Button = findViewById(R.id.btnSports)
        val btnEntertainment: Button = findViewById(R.id.btnEntertainment)

        // Load default fragment
        loadFragment(NewsFragment())

        btnNews.setOnClickListener {
            loadFragment(NewsFragment())
        }

        btnSports.setOnClickListener {
            loadFragment(SportsFragment())
        }

        btnEntertainment.setOnClickListener {
            loadFragment(EntertainmentFragment())
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView, fragment)
            .commit()
    }
}
