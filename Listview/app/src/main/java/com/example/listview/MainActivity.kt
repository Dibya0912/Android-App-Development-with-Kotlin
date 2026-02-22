package com.example.listview

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    lateinit var lv: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lv = findViewById(R.id.lview)

        // Data source
        val names = arrayOf("C", "JAVA", "PYTHON", "C++", "HTML", "PHP")

        // Use the custom adapter
        val customAdapter = CustomAdapter(this, names)
        lv.adapter = customAdapter
    }
}

// Custom Adapter class to handle the custom layout
class CustomAdapter(context: Context, private val languages: Array<String>) :
    ArrayAdapter<String>(context, R.layout.custom_list_item, languages) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // Inflate the custom layout for each row
        val rowView = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.custom_list_item, parent, false)

        // Get references to the views in the custom layout
        val textView = rowView.findViewById<TextView>(R.id.textViewLanguage)
        val spinner = rowView.findViewById<Spinner>(R.id.spinnerOptions)

        // Get the data for the current row
        val currentLanguage = languages[position]

        // Set the text for the TextView
        textView.text = currentLanguage

        // Create an adapter for the spinner inside the row
        val spinnerAdapter = ArrayAdapter(
            context,
            android.R.layout.simple_spinner_item,
            languages
        ).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }

        // Set the spinner adapter
        spinner.adapter = spinnerAdapter

        // Set the spinner's selection to match the current row's language
        spinner.setSelection(position)

        return rowView
    }
}