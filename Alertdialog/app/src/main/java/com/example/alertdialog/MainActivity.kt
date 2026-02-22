package com.example.alertdialog

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.alertdialog.R

class MainActivity : AppCompatActivity() {

    // Define constants for SharedPreferences
    private val PREFS_NAME = "ExitPrefs"
    private val KEY_DONT_ASK_AGAIN = "dontAskAgain"

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)

        val exitButton: Button = findViewById(R.id.exitButton)

        exitButton.setOnClickListener {
            // Check the saved preference
            val dontAsk = sharedPreferences.getBoolean(KEY_DONT_ASK_AGAIN, false)

            if (dontAsk) {
                // If the user previously chose "Don't ask again", exit directly
                finish()
            } else {
                // Otherwise, show the confirmation dialog
                showExitDialog()
            }
        }
    }

    private fun showExitDialog() {
        // Inflate the custom layout with the CheckBox
        val dialogView = layoutInflater.inflate(R.layout.dialog_with_checkbox, null)
        val dontAskAgainCheckBox = dialogView.findViewById<CheckBox>(R.id.dontAskAgainCheckBox)

        // Create an AlertDialog.Builder
        val builder = AlertDialog.Builder(this)

        // Set the properties of the dialog
        builder.setTitle("Confirm Exit")
        // The custom view now contains the message and checkbox
        builder.setView(dialogView)
        builder.setCancelable(true)

        // Set the "Yes" button and its click listener
        builder.setPositiveButton("Yes") { dialog, which ->
            // Check if the "Don't ask again" checkbox is checked
            if (dontAskAgainCheckBox.isChecked) {
                // Save the preference to not show the dialog again
                val editor = sharedPreferences.edit()
                editor.putBoolean(KEY_DONT_ASK_AGAIN, true)
                editor.apply()
            }
            // Close the activity and exit the app
            finish()
        }

        // Set the "No" button and its click listener
        builder.setNegativeButton("No") { dialog, which ->
            dialog.dismiss()
        }

        // Create and show the AlertDialog
        val alertDialog = builder.create()
        alertDialog.show()
    }
}