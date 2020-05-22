package com.example.bricklist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val allList=findViewById<LinearLayout>(R.id.projects)
        for (x in PreservedProjects.Companion.allProjects){
            val view = TextView(this)
            view.text = x.html()
            allList.addView(view)
        }

        val sSet = findViewById<Button>(R.id.settings)
        sSet?.setOnClickListener(){
            val intent = Intent(this, Settings::class.java)
            startActivity(intent)
        }

        val sNew = findViewById<Button>(R.id.newp)
        sNew?.setOnClickListener(){
            val intent = Intent(this, NewProject::class.java)
            startActivity(intent)
        }
    }
}
