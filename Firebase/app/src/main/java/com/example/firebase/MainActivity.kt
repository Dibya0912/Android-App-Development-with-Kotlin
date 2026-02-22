package com.example.registernew

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.firebase.R
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    val ref = FirebaseAuth.getInstance()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val email = findViewById<EditText>(R.id.et1)
        val epwd = findViewById<EditText>(R.id.et2)
        val btnlgn = findViewById<Button>(R.id.buttonLogin)

        btnlgn.setOnClickListener {
            val e = email.text.toString().trim()
            val p = epwd.text.toString().trim()

            if (e.isEmpty() || p.isEmpty()) {
                Toast.makeText(this, "Enter both fields", Toast.LENGTH_SHORT).show()
            } else {
                ref.createUserWithEmailAndPassword(e, p)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this, "Error: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                        }
                    }
            }
        }
    }
}
