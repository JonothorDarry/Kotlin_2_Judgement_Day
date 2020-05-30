package com.example.bricklist

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
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
            var plusButt: Button
            var minusButt: Button
            var image: ImageView
            var barray: ByteArray?
            var plname: String?

            var vk : LinearLayout

            for (x in lst) {
                vk =LinearLayout(this)
                vk.layoutParams=ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
                vk.orientation=LinearLayout.VERTICAL

                name = TextView(this)
                color_id= TextView(this)
                var enumera= TextView(this)
                minusButt= Button(this)
                plusButt= Button(this)
                image= ImageView(this)

                minusButt.text="-"
                plusButt.text="+"

                if (Databaze.myDb!=null) {
                    plname=Databaze.myDb?.getMyrDao()?.getPlName(x.ItemID)
                    if (plname!=null) name.text=plname
                    else name.text = Databaze.myDb?.getMyrDao()?.getName(x.ItemID)


                    color_id.text=Databaze.myDb?.getMyrDao()?.getColor(x.ColorID)+" ["+Databaze.myDb?.getMyrDao()?.getCode(x.ItemID)+"]"
                    enumera.text=x.QuantityInStore.toString()+" of "+x.QuantityInSet.toString()
                    barray=Databaze.myDb?.getMyrDao()?.getImage(x.ColorID, x.ItemID)
                    if (barray!=null){
                        image.setImageBitmap(BitmapFactory.decodeByteArray(barray, 0, barray.size))
                    }
                }

                minusButt.layoutParams=ViewGroup.LayoutParams(120, ViewGroup.LayoutParams.WRAP_CONTENT)
                plusButt.layoutParams=ViewGroup.LayoutParams(120, ViewGroup.LayoutParams.WRAP_CONTENT)

                minusButt.setOnClickListener {
                    Buttonizer.change(-1, x, enumera)
                }

                plusButt.setOnClickListener {
                    Buttonizer.change(1, x, enumera)
                }

                vk.addView(name)
                vk.addView(color_id)
                vk.addView(enumera)
                vk.addView(minusButt)
                vk.addView(plusButt)
                vk.addView(image)

                allList.addView(vk)
            }
        }

        val sMain = findViewById<Button>(R.id.mainRet)
        sMain?.setOnClickListener(){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val sExpo=findViewById<Button>(R.id.exporter)
        sExpo?.setOnClickListener(){
            Exporter.exportXML(this@SingleSet, applicationContext)
        }

        val isSetArch=base?.getMyrDao()?.getArchive(PreservedProjects.projectId)
        val sArch=findViewById<Button>(R.id.archiv)
        if (isSetArch==1) sArch.text="Unarchivize"
        sArch.setOnClickListener {
            base?.getMyrDao()?.setArchivize(PreservedProjects.projectId, if (isSetArch==1) 2 else 1)
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