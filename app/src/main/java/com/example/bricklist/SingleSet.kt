package com.example.bricklist

import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class SingleSet : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_set)

        val allList=findViewById<LinearLayout>(R.id.showSet)
        val base=Databaze.dbCreator(applicationContext)
        if (base!=null) {
            val lst = base.getMyrDao().getInvParts(PreservedProjects.projectId)

            var name :TextView
            var color_id: TextView
            var enumera: TextView

            var vk : LinearLayout

            for (x in lst) {
                vk =LinearLayout(this)
                vk.layoutParams=ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
                vk.orientation=LinearLayout.VERTICAL

                name = TextView(this)
                color_id= TextView(this)
                enumera= TextView(this)

                if (Databaze.myDb!=null) {
                    name.text = Databaze.myDb?.getMyrDao()?.getName(x.ItemID)
                    color_id.text=Databaze.myDb?.getMyrDao()?.getColor(x.ColorID)+" ["+Databaze.myDb?.getMyrDao()?.getCode(x.ItemID)+"]"
                    enumera.text=x.QuantityInStore.toString()+" of "+x.QuantityInSet.toString()
                }

                vk.addView(name)
                vk.addView(color_id)
                vk.addView(enumera)
                allList.addView(vk)
            }
        }

        val sMain = findViewById<Button>(R.id.mainRet)
        sMain?.setOnClickListener(){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


    }
}






/*
val param2 = view.layoutParams as ViewGroup.MarginLayoutParams
param2.setMargins(100,100,0,100)
view.layoutParams = param2*/
/*v2.layoutParams=LinearLayout.LayoutParams(
    ViewGroup.LayoutParams.WRAP_CONTENT,
    ViewGroup.LayoutParams.WRAP_CONTENT
)
val param = v2.layoutParams as ViewGroup.MarginLayoutParams
param.setMargins(0,100,100,100)
v2.layoutParams = param
*/