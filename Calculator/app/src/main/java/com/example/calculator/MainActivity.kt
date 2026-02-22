package com.example.calculator

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private var Num1: EditText? = null
    private var Num2: EditText? = null
    private var Add: Button? = null
    private var Sub: Button? = null
    private var Mul: Button? = null
    private var Div: Button? = null
    private var Mod: Button? = null
    private var Results: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        Num1 = findViewById(R.id.etNum1)
        Num2 = findViewById(R.id.etNum2)
        Add = findViewById(R.id.btnAdd)
        Sub = findViewById(R.id.btnSub)
        Mul = findViewById(R.id.btnMul)
        Div = findViewById(R.id.btnDiv)
        Mod = findViewById(R.id.btnMod)
        Results = findViewById(R.id.tvAnswer)

        Add!!.setOnClickListener {
            val n1 = Num1!!.text.toString().toInt()
            val n2 = Num2!!.text.toString().toInt()
            val sum = n1 + n2
            Results!!.text = "Result: $sum"
        }

        Sub!!.setOnClickListener {
            val n1 = Num1!!.text.toString().toInt()
            val n2 = Num2!!.text.toString().toInt()
            val diff = n1 - n2
            Results!!.text = "Result: $diff"
        }

        Mul!!.setOnClickListener {
            val n1 = Num1!!.text.toString().toInt()
            val n2 = Num2!!.text.toString().toInt()
            val product = n1 * n2
            Results!!.text = "Result: $product"
        }

        Div!!.setOnClickListener {
            val n1 = Num1!!.text.toString().toInt()
            val n2 = Num2!!.text.toString().toInt()
            if (n2 != 0) {
                val quotient = n1 / n2
                Results!!.text = "Result: $quotient"
            } else {
                Results!!.text = "Cannot divide by zero"
            }
        }

        Mod!!.setOnClickListener {
            val n1 = Num1!!.text.toString().toInt()
            val n2 = Num2!!.text.toString().toInt()
            if (n2 != 0) {
                val remainder = n1 % n2
                Results!!.text = "Result: $remainder"
            } else {
                Results!!.text = "Cannot mod by zero"
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
