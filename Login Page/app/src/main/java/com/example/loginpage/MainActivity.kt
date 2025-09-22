package com.example.loginpage

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var uname: EditText
    private lateinit var epwd: EditText
    private lateinit var btnLogin: Button
    private lateinit var btnReset: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Link UI elements with their IDs
        uname = findViewById(R.id.etUsername)
        epwd = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.buttonLogin)
        btnReset = findViewById(R.id.btn_rst)

        // Reset button clears the text fields
        btnReset.setOnClickListener {
            uname.text.clear()
            epwd.text.clear()
        }

        // Login button logic
        btnLogin.setOnClickListener {
            val username = uname.text.toString()
            val password = epwd.text.toString()

            if (username == "Ankit" && password == "143") {
                Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
