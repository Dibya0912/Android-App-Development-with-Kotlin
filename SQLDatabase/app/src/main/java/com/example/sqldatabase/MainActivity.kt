package com.example.sqldatabase

import android.database.Cursor
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var myDb: DatabaseHelper
    private lateinit var etName: EditText
    private lateinit var etSurname: EditText
    private lateinit var etMarks: EditText
    private lateinit var etId: EditText
    private lateinit var addBtn: Button
    private lateinit var viewBtn: Button
    private lateinit var updateBtn: Button
    private lateinit var delBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        myDb = DatabaseHelper(this)
        etId = findViewById(R.id.editTextTextPersonName4)
        etName = findViewById(R.id.editTextTextPersonName)
        etSurname = findViewById(R.id.editTextTextPersonName2)
        etMarks = findViewById(R.id.editTextTextPersonName3)
        addBtn = findViewById(R.id.button)
        viewBtn = findViewById(R.id.button2)
        updateBtn = findViewById(R.id.button3)
        delBtn = findViewById(R.id.button4)

        addData()
        viewData()
        updateData()
        deleteData()
    }

    private fun addData() {
        addBtn.setOnClickListener {
            val isInserted = myDb.insertData(
                etName.text.toString(),
                etSurname.text.toString(),
                etMarks.text.toString()
            )
            if (isInserted)
                showToast("Data Inserted")
            else
                showToast("Data Not Inserted")
        }
    }

    private fun viewData() {
        viewBtn.setOnClickListener {
            val res: Cursor = myDb.getData()
            if (res.count == 0) {
                showMessage("Error", "No Data Found")
                return@setOnClickListener
            }

            val buffer = StringBuilder()
            while (res.moveToNext()) {
                buffer.append("ID: ${res.getString(0)}\n")
                buffer.append("NAME: ${res.getString(1)}\n")
                buffer.append("SURNAME: ${res.getString(2)}\n")
                buffer.append("MARKS: ${res.getString(3)}\n\n")
            }
            showMessage("Data", buffer.toString())
        }
    }

    private fun updateData() {
        updateBtn.setOnClickListener {
            val isUpdate = myDb.updateData(
                etId.text.toString(),
                etName.text.toString(),
                etSurname.text.toString(),
                etMarks.text.toString()
            )
            if (isUpdate)
                showToast("Data Updated")
            else
                showToast("Data Not Updated")
        }
    }

    private fun deleteData() {
        delBtn.setOnClickListener {
            val deletedRows = myDb.delData(etId.text.toString())
            if (deletedRows > 0)
                showToast("Data Deleted")
            else
                showToast("Data Not Deleted")
        }
    }

    private fun showMessage(title: String, message: String) {
        AlertDialog.Builder(this)
            .setCancelable(true)
            .setTitle(title)
            .setMessage(message)
            .show()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}
