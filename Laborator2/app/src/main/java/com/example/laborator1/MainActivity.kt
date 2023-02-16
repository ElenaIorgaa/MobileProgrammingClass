package com.example.laborator1

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.widget.EditText
import android.widget.TextView


class MainActivity : AppCompatActivity() {
    var message : String = ""
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        val textView = findViewById(R.id.message) as TextView

        if(savedInstanceState != null)
        {
            message = savedInstanceState.getString("message","")
        }

        textView.movementMethod = ScrollingMovementMethod()
    }

    fun sendMessage(view: View) {
        val intent = Intent(this, SecondActivity::class.java)
        val editText:EditText = findViewById(R.id.message)
        val message:String = editText.text.toString()
        intent.putExtra(EXTRA_MESSAGE, message)
        intent.putExtra("fromMain","da")

        println("Trimit da")
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        val textView = findViewById(R.id.message) as TextView
        textView.text = message
        val intent = intent
        val newMess = intent.getStringExtra(EXTRA_MESSAGE).toString()

        if(newMess=="apelata") {
            textView.text = ""
            intent.putExtra(EXTRA_MESSAGE,"")
        }
        else
            textView.text=message

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val textView = findViewById(R.id.message) as TextView
        message = textView.text.toString()
        outState.putString("message",message)
    }

    override fun onPause()
    {
        super.onPause()
        println("PUSA PE PAUZA")
    }

}