package com.example.bricklist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Switch

class Settings : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val Arch=findViewById<Switch>(R.id.showarch)
        Arch.isChecked=PreservedSettings.Companion.showArchived

        val Url=findViewById<EditText>(R.id.url)
        Url.setText(PreservedSettings.Companion.page)

        val sRet = findViewById<Button>(R.id.returner)
        sRet?.setOnClickListener(){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val sSav = findViewById<Button>(R.id.saver)
        sSav?.setOnClickListener(){
            PreservedSettings.Companion.showArchived=Arch.isChecked
            PreservedSettings.Companion.page=Url.text.toString()

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }
}
