package com.example.bricklist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Switch
import kotlinx.android.synthetic.main.activity_settings.*

class Settings : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        showarch.isChecked=PreservedSettings.showArchived
        url.setText(PreservedSettings.page)
        fileDir.setText(PreservedSettings.fileDir)

        when (PreservedSettings.condition){
            "U" -> conditionGroup.check(usedRadio.id)
            "N" -> conditionGroup.check(newRadio.id)
            "Omit" -> conditionGroup.check(omitRadio.id)
        }

        when (PreservedSettings.format){
            "Name" -> formatGroup.check(nameRadio.id)
            "NameId" -> formatGroup.check(nameIdRadio.id)
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

            var prop=conditionGroup.checkedRadioButtonId==newRadio.id
            if (prop) PreservedSettings.condition="N"
            prop=conditionGroup.checkedRadioButtonId==usedRadio.id
            if (prop) PreservedSettings.condition="U"
            prop=conditionGroup.checkedRadioButtonId==omitRadio.id
            if (prop) PreservedSettings.condition="Omit"

            prop=formatGroup.checkedRadioButtonId==nameRadio.id
            if (prop) PreservedSettings.format="Name"
            prop=formatGroup.checkedRadioButtonId==nameIdRadio.id
            if (prop) PreservedSettings.format="NameId"

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }
}
