package com.example.internalstorage

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.FileNotFoundException
import java.io.IOException

class MainActivity : AppCompatActivity() {
    private lateinit var editText: EditText
    private lateinit var btnSave: Button
    private lateinit var btnLoad: Button
    private lateinit var textView: TextView
    private val filename: String = "myfile.txt"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editText = findViewById(R.id.editText)
        btnSave = findViewById(R.id.btnSave)
        btnLoad = findViewById(R.id.btnLoad)
        textView = findViewById(R.id.textView)

        btnSave.setOnClickListener {
            val textToSave = editText.text.toString()
            if (textToSave.isEmpty()) {
                Toast.makeText(this, "Please enter text to save.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            try {
                openFileOutput(filename, Context.MODE_PRIVATE).use {
                    it.write(textToSave.toByteArray())
                }
                Toast.makeText(this, "Saved Successfully!", Toast.LENGTH_SHORT).show()
                editText.text.clear()
            } catch (e: IOException) {
                e.printStackTrace()
                Toast.makeText(this, "Error saving file!", Toast.LENGTH_SHORT).show()
            }
        }

        btnLoad.setOnClickListener {
            try {
                val loadedText = openFileInput(filename).bufferedReader().use { it.readText() }
                textView.text = loadedText
                Toast.makeText(this, "Loaded Successfully!", Toast.LENGTH_SHORT).show()
            } catch (e: FileNotFoundException) {
                // Handle case where file doesn't exist yet
                Toast.makeText(this, "File not found. Save some text first.", Toast.LENGTH_SHORT).show()
            } catch (e: IOException) {
                e.printStackTrace()
                Toast.makeText(this, "Error loading file!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}