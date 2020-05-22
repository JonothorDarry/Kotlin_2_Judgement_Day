package com.example.bricklist

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import org.jsoup.Jsoup
import org.jsoup.nodes.Document


class NewProject : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val TAG="StateChange"
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_project)

        val sOK = findViewById<Button>(R.id.ok)
        sOK?.setOnClickListener(){
            val newPage=findViewById<EditText>(R.id.urlEnd)
            val strNewPage=newPage.text.toString()

            var doc: Document

            DoAsync {
                doc = Jsoup.connect(PreservedSettings.page+strNewPage+".xml").get()
                PreservedProjects.Companion.allProjects.add(doc)
            }.execute()

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val sCanc = findViewById<Button>(R.id.cancel)
        sCanc?.setOnClickListener() {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
