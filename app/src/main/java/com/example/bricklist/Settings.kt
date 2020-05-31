package com.example.bricklist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import kotlinx.android.synthetic.main.activity_settings.*

class Settings : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        showarch.isChecked=PreservedSettings.showArchived
        url.setText(PreservedSettings.page)
        fileDir.setText(PreservedSettings.fileDir)

        when (PreservedSettings.condition){
            "U" -> conditionSpinner.setSelection(0)
            "N" -> conditionSpinner.setSelection(1)
            "Omit" -> conditionSpinner.setSelection(2)
        }

        when (PreservedSettings.format){
            "Name" -> formatSpinner.setSelection(0)
            "NameId" -> formatSpinner.setSelection(1)
        }

        when (PreservedSettings.getBy){
            "Color" -> preferenceSpinner.setSelection(0)
            "Item" -> preferenceSpinner.setSelection(1)
        }


        val sRet = findViewById<Button>(R.id.returner)
        sRet?.setOnClickListener(){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val sSav = findViewById<Button>(R.id.saver)
        sSav?.setOnClickListener(){
            PreservedSettings.showArchived=showarch.isChecked
            PreservedSettings.page=url.text.toString()
            PreservedSettings.fileDir=fileDir.text.toString()

            var prop=conditionSpinner.selectedItem
            when(prop.toString()){
                "New" -> PreservedSettings.condition="N"
                "Used" -> PreservedSettings.condition="U"
                "Omit" -> PreservedSettings.condition="Omit"
            }

            prop=formatSpinner.selectedItem
            when(prop.toString()){
                "Name" -> PreservedSettings.format="Name"
                "NameId" -> PreservedSettings.format="NameId"
            }

            prop=preferenceSpinner.selectedItem
            when(prop.toString()){
                "Item Name" -> PreservedSettings.getBy="Item"
                "Color" -> PreservedSettings.getBy="Color"
            }

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }
}
