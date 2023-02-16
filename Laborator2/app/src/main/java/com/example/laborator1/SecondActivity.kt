package com.example.laborator1

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.core.app.NavUtils


class SecondActivity : AppCompatActivity() {
    var mess: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val intent = intent
        val sharedPref = this.getPreferences(Context.MODE_PRIVATE)?:return
        val textView: TextView = findViewById(R.id.result)
        textView.movementMethod = ScrollingMovementMethod()

        val textViewIstoric: TextView = findViewById(R.id.textViewIstoric)
        textViewIstoric.movementMethod = ScrollingMovementMethod()

        if(sharedPref.getString("result","")!=null)
            mess = sharedPref.getString("result","").toString()
        val newMess = intent.getStringExtra(EXTRA_MESSAGE).toString()

        textViewIstoric.text = mess

        println("----------------------------------------------")
        println(intent.getStringExtra("fromMain").toString())
        //if(mess!="") {
            if(newMess != "\n" && intent.getStringExtra("fromMain").toString() == "da")
            {
                println("face concatenare")
                mess = "$mess \n $newMess"
                intent.putExtra("fromMain","")
            }
            else if(intent.getStringExtra("fromMain").toString() != "da")
            {
                println("ggggggggggggggggggggggggggggggggggggggggggggggggggggg")
            }
            else {
                println("pune mesajul vechi")
            }
        //}
        //else
          //      mess = newMess
        with (sharedPref.edit())
        {
            putString("result",mess)
            apply()
        }
        textView.text = newMess


    }

    fun goBack(view: View) {

       // val intent = Intent(this, MainActivity::class.java)
        val intent = NavUtils.getParentActivityIntent(this)
        intent?.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        intent?.putExtra(EXTRA_MESSAGE, "apelata")
        startActivity(intent)


        startActivity(intent)
    }

    fun deleteSavedMessages(view: View) {
        val sharedPref = this.getPreferences(Context.MODE_PRIVATE)
        sharedPref.edit().remove("result").apply()
        val textView: TextView = findViewById(R.id.textViewIstoric)
        textView.movementMethod = ScrollingMovementMethod()
        textView.text = ""
    }
    fun sendMessage(view: View) {

    }

    override fun onDestroy() {
        super.onDestroy()
        val intent = intent
        println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!")
        intent.putExtra("fromMain","")

    }

}