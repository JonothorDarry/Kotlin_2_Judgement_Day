package com.example.bricklist

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_new_project.*
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.net.HttpURLConnection


class NewProject : AppCompatActivity() {
    companion object{
        var projId=-1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_new_project)

        val sOK = findViewById<Button>(R.id.ok)
        sOK?.setOnClickListener(){
            if (SharedWisdom.isProcess()){
                npError.text="You cannot create new project while one is still being dowloaded!"
            }
            else {
                val newPage = findViewById<EditText>(R.id.urlEnd)
                val strNewPage = newPage.text.toString()
                val projName = findViewById<EditText>(R.id.projName).text.toString()
                var doc: Document

                val base = Databaze.dbCreator(applicationContext)
                if (projId == -1) {
                    val st = base?.getMyrDao()?.getMaxInvPartId()
                    if (st != null) projId = st + 1
                    else projId = 1
                }

                SharedWisdom.start = 1
                SharedWisdom.imageDead = 0
                SharedWisdom.nameDead = 0
                DoAsync {
                    val huc: HttpURLConnection =
                        java.net.URL(PreservedSettings.page + strNewPage + ".xml").openConnection() as HttpURLConnection
                    val responseCode: Int = huc.responseCode
                    if (responseCode == 404) {
                        SharedWisdom.all = -1
                        SharedWisdom.start = 0
                    }
                    else {
                        doc = Jsoup.connect(PreservedSettings.page + strNewPage + ".xml").get()

                        var inv = DbInventories(projId, 0, 0, projName)
                        SharedWisdom.element = inv
                        projId++

                        if (base != null) {
                            base.getMyrDao().insertInventory(inv)
                            SharedWisdom.communicate = projName
                            XMLOperations.createInvPartFromXml(doc, projId - 1, base)
                        }
                    }
                }.execute()

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }

        val sCanc = findViewById<Button>(R.id.cancel)
        sCanc?.setOnClickListener() {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
