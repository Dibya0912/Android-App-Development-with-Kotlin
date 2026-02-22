package com.example.externalstorage

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private lateinit var editTextData: EditText
    private lateinit var btnSave: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextData = findViewById(R.id.editTextData)
        btnSave = findViewById(R.id.btnSave)

        btnSave.setOnClickListener {
            saveToFile()
        }
    }

    private fun saveToFile() {
        val data = editTextData.text.toString().trim()
        if (data.isEmpty()) {
            Toast.makeText(this, "Please enter some text first", Toast.LENGTH_SHORT).show()
            return
        }

        val dir = getExternalFilesDir(null)
        val file = File(dir, "saved_text.txt")

        try {
            FileOutputStream(file).use { fos ->
                fos.write(data.toByteArray())
                Toast.makeText(this, "Saved to: ${file.absolutePath}", Toast.LENGTH_LONG).show()
            }
        } catch (e: IOException) {
            Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
}
