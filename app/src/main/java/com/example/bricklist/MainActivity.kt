package com.example.bricklist

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val allList=findViewById<LinearLayout>(R.id.projects)

        val base=Databaze.dbCreator(applicationContext)
        val lst=base?.getMyrDao()?.loadColors()
        val view = TextView(this)
        view.text = lst?.get(1)
        allList.addView(view)



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
