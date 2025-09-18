package com.example.materialdesign

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Dashboard : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_dashboard)

        // Handle system bars padding
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.gridLayout)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 1. Profile → LinkedIn
        findViewById<CardView>(R.id.card_profile).setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(
                "https://www.linkedin.com/in/dibya-ranjan-pradhan-61b75b365/"
            )))
        }

        // 2. Educational Qualification → CUTM homepage
        findViewById<CardView>(R.id.card_profile2).setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(
                "https://cutm.ac.in/"
            )))
        }

        // 3. BTech → BTech CSE course page
        findViewById<CardView>(R.id.card_profile3).setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(
                "https://cutm.ac.in/courses/bachelor-of-technology-in-computer-science-and-engineering/"
            )))
        }

        // 4. MCA → MCA course page
        findViewById<CardView>(R.id.card_profile4).setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(
                "https://cutm.ac.in/courses/master-of-computer-applications/"
            )))
        }

        // 5. MBA → MBA (Healthcare Management) course page
        findViewById<CardView>(R.id.card_profile5).setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(
                "https://cutm.ac.in/courses/master-of-business-administration-healthcare-management/"
            )))
        }

        // 6. BCA → BCA course page
        findViewById<CardView>(R.id.card_profile6).setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(
                "https://cutm.ac.in/courses/bachelor-of-computer-application/"
            )))
        }

        // 7. BBA → BBA course page
        findViewById<CardView>(R.id.card_profile7).setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(
                "https://cutm.ac.in/courses/bachelor-of-business-administration-2/"
            )))
        }

        // 8. MSc → MSc Applied Chemistry course page
        findViewById<CardView>(R.id.card_profile8).setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(
                "https://cutm.ac.in/courses/master-of-science-in-applied-chemistry/"
            )))
        }

        // 9. CUTM Café → Open Food Ordering App (MainActivity)
        findViewById<CardView>(R.id.card_profile9).setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
