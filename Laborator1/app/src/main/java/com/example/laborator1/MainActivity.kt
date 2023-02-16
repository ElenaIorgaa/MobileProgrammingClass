package com.example.laborator1

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.makeText

class MainActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn_click_me = findViewById(R.id.button) as Button
        val textView = findViewById(R.id.textView) as TextView
        textView.movementMethod = ScrollingMovementMethod()
        var flag = true
        btn_click_me.setOnClickListener {
            flag = if(flag) {
                textView.text = ""
                false
            } else {
                textView.text = "Hello Elenaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                true
            }
        }
    }
}