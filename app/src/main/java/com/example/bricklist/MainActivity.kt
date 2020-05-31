package com.example.bricklist

import android.app.Application
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.size
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import kotlin.concurrent.fixedRateTimer


class MainActivity : AppCompatActivity() {
    private fun viewPrepare(view: TextView, x: DbInventories, base:Databaze?){
        if (base==null) return
        view.text = x.Name
        //view.setBackgroundResource(android.R.drawable.editbox_background)
        view.visibility=View.VISIBLE
        view.setOnClickListener {
            PreservedProjects.projectId=x.id
            val time=base.getMyrDao().getMaxTime()+1
            base.getMyrDao().changeProjectTime(x.id, time)
            val intent = Intent(this, SingleSet::class.java)
            startActivity(intent)
        }
    }

    fun viewChanger(x: DbInventories, base: Databaze?){
        val view=TextView(this)
        if (base!=null) viewPrepare(view, x, base)
        this.projects.addView(view)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        PreservedSettings.verifyStoragePermissions(this@MainActivity)

        val allList=findViewById<LinearLayout>(R.id.projects)

        val base=Databaze.dbCreator(applicationContext)
        var view: TextView

        if (base!=null) {
            val namez = base.getMyrDao().getInvNames(if (PreservedSettings.showArchived) 1 else 2)
            Log.d("TAG", namez.size.toString())
            for (x in namez) {
                view = Button(this)
                viewPrepare(view, x, base)
                allList.addView(view)
            }
        }
        SharedWisdom.zeroIfFinished()

        view = Button(this)
        view.visibility=View.INVISIBLE
        view.text=""

        allList.addView(view)

        val sSet = findViewById<Button>(R.id.settings)
        sSet?.setOnClickListener {
            val intent = Intent(this, Settings::class.java)
            startActivity(intent)
        }

        fixedRateTimer("timer", false, 0L, 1500) {
            this@MainActivity.runOnUiThread {
                projectText.text = SharedWisdom.communicate
                if (SharedWisdom.all<0) infText.text="The URL is incorrect, there is no such xml"
                else infText.text=SharedWisdom.current.toString()+" of "+SharedWisdom.all.toString()+" items were processed"
                if (SharedWisdom.current==SharedWisdom.all && SharedWisdom.all!=0){
                    preciseText.text="Project is finished, ${SharedWisdom.imageDead} images and ${SharedWisdom.nameDead} names were not found "
                }
                else preciseText.text=""

                if (SharedWisdom.isEndet()){
                    viewPrepare(view, SharedWisdom.element, Databaze.dbCreator(applicationContext))
                }
            }
        }

        val sNew = findViewById<Button>(R.id.newp)
        sNew?.setOnClickListener(){
            if (SharedWisdom.isProcess()) urDead.text="You cannot create new project while one is still being dowloaded!"

            else {
                SharedWisdom.zeroIfFinished()
                val intent = Intent(this, NewProject::class.java)
                startActivity(intent)
            }
        }
    }
}
//@android:drawable/editbox_background