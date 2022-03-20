package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class SecondAct : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val btnSecond = findViewById<Button>(R.id.button_second_act)
        val textViewSecond = findViewById<TextView>(R.id.textview_second_Act)
        btnSecond.setOnClickListener{
            textViewSecond.text = "changed text !"
        }
    }
}