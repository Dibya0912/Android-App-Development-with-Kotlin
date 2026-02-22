package com.example.jsonparsing

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import org.json.JSONArray

data class User(val name: String, val email: String, val age: Int)

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_json_parsing) // Must match the XML file name

        val textView = findViewById<TextView>(R.id.textView)

        val jsonString = """
            [
              {"name": "John Doe", "email": "john@example.com", "age": 25},
              {"name": "Jane Smith", "email": "jane@example.com", "age": 22}
            ]
        """

        val jsonArray = JSONArray(jsonString)
        val users = mutableListOf<User>()

        for (i in 0 until jsonArray.length()) {
            val obj = jsonArray.getJSONObject(i)
            users.add(User(obj.getString("name"), obj.getString("email"), obj.getInt("age")))
        }

        val result = users.joinToString("\n\n") { "Name: ${it.name}\nEmail: ${it.email}\nAge: ${it.age}" }
        textView.text = result
    }
}
