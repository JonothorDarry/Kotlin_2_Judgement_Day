package com.example.bricklist

import android.app.Application
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import kotlin.concurrent.fixedRateTimer


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val allList=findViewById<LinearLayout>(R.id.projects)

        val base=Databaze.dbCreator(applicationContext)
        if (base!=null) {
            var view: TextView

            val namez = base.getMyrDao().getInvNames(if (PreservedSettings.showArchived) 1 else 2)
            for (x in namez) {
                view = TextView(this)
                view.text = x.Name
                view.setOnClickListener {
                    PreservedProjects.projectId=x.id
                    val time=base.getMyrDao().getMaxTime()+1
                    base.getMyrDao().changeProjectTime(x.id, time)
                    val intent = Intent(this, SingleSet::class.java)
                    startActivity(intent)
                }
                allList.addView(view)
            }
        }


        val sSet = findViewById<Button>(R.id.settings)
        sSet?.setOnClickListener {
            val intent = Intent(this, Settings::class.java)
            startActivity(intent)
        }

        fixedRateTimer("timer", false, 0L, 1000) {
            this@MainActivity.runOnUiThread {
                projectText.text = SharedWisdom.communicate
                infText.text=SharedWisdom.current.toString()
            }
        }

        val sNew = findViewById<Button>(R.id.newp)
        sNew?.setOnClickListener(){
            val intent = Intent(this, NewProject::class.java)
            startActivity(intent)
        }
    }
}
