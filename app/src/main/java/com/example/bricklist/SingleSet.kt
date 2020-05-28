package com.example.bricklist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView

class SingleSet : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_set)

        val allList=findViewById<LinearLayout>(R.id.showSet)
        val base=Databaze.dbCreator(applicationContext)
        if (base!=null) {
            val lst = base.getMyrDao().getInvParts(PreservedProjects.projectId)

            var view :TextView
            for (x in lst) {
                view = TextView(this)
                if (Databaze.myDb!=null){
                    view.text = Databaze.myDb?.getMyrDao()?.getName(x.ItemID)
                }
                allList.addView(view)
            }
        }

        val sMain = findViewById<Button>(R.id.mainRet)
        sMain?.setOnClickListener(){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


    }
}
