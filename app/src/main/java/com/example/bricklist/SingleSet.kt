package com.example.bricklist

import android.R.attr.button
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.widget.*
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.scale
import kotlinx.android.synthetic.main.activity_single_set.*
import org.w3c.dom.Element
import java.io.File
import java.lang.Exception
import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.transform.OutputKeys
import javax.xml.transform.Transformer
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult


class SingleSet : AppCompatActivity() {

    private fun exportXML(activity: Activity, context: Context){
        val docBuilder: DocumentBuilder=DocumentBuilderFactory.newInstance().newDocumentBuilder()
        val doc=docBuilder.newDocument()


        val rootElement: Element =doc.createElement("INVENTORY")
        val base=Databaze.dbCreator(context)
        if (base!=null) {
            var lst: List<DbInventoriesParts>
            if (PreservedSettings.getBy=="Color") lst = base.getMyrDao().getInvPartsByColor(PreservedProjects.projectId)
            else lst = base.getMyrDao().getInvPartsByItem(PreservedProjects.projectId)

            for (x in lst){
                val item=doc.createElement("ITEM")
                if (x.QuantityInSet-x.QuantityInStore==0) continue

                val itemid=doc.createElement("ITEMID")
                val itemtype=doc.createElement("ITEMTYPE")
                val color=doc.createElement("COLOR")
                val qtyfilled=doc.createElement("QTYFILLED")

                color.appendChild(doc.createTextNode(base.getMyrDao().getColorNumber(x.ColorID)?.toString()))
                qtyfilled.appendChild(doc.createTextNode((x.QuantityInSet-x.QuantityInStore).toString()))
                var code=base.getMyrDao().getCode(x.ItemID)
                if (code==null) code=""
                itemid.appendChild(doc.createTextNode(code))
                itemtype.appendChild(doc.createTextNode(base.getMyrDao().getTypeCode(x.TypeID)))

                item.appendChild(itemid)
                item.appendChild(itemtype)
                item.appendChild(color)
                item.appendChild(qtyfilled)

                if (PreservedSettings.condition!="Omit"){
                    val cond=doc.createElement("CONDITION")
                    cond.appendChild(doc.createTextNode(PreservedSettings.condition))
                    item.appendChild(cond)
                }

                rootElement.appendChild(item)
            }
        }

        doc.appendChild(rootElement)

        val transformer: Transformer =TransformerFactory.newInstance().newTransformer()
        transformer.setOutputProperty(OutputKeys.INDENT, "yes")
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2")

        try{
            val filepath = PreservedSettings.fileDir
            val outDir=File(filepath)
            outDir.mkdirs()

            var file: File
            if (PreservedSettings.format=="Name") file=File(outDir, "${base?.getMyrDao()?.getNameByProjectId(PreservedProjects.projectId)}.xml")
            else file=File(outDir, "${base?.getMyrDao()?.getNameByProjectId(PreservedProjects.projectId)}${PreservedProjects.projectId}.xml")
            file.createNewFile()
            transformer.transform(DOMSource(doc), StreamResult(file))
        }
        catch (e: Exception){
            exportError.text="Failed to create file"
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_set)

        val allList=findViewById<LinearLayout>(R.id.showSet)
        val base=Databaze.dbCreator(applicationContext)
        if (base!=null) {
            var lst: List<DbInventoriesParts>
            if (PreservedSettings.getBy=="Color") lst = base.getMyrDao().getInvPartsByColor(PreservedProjects.projectId)
            else lst = base.getMyrDao().getInvPartsByItem(PreservedProjects.projectId)

            var name :TextView
            var color_id: TextView
            var plusButt: Button
            var minusButt: Button
            var wrapperLayout: TableRow
            var buttons: TableRow
            var image: ImageView
            var barray: ByteArray?
            var plname: String?
            var wisdom: LinearLayout

            //var vk : LinearLayout

            for (x in lst) {
                var vk =TableLayout(this)
                vk.layoutParams=ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
                vk.orientation=LinearLayout.VERTICAL

                name = TextView(this)
                color_id= TextView(this)
                var enumera= TextView(this)
                minusButt= Button(this)
                plusButt= Button(this)
                image= ImageView(this)
                buttons= TableRow(this)
                wisdom= LinearLayout(this)
                wrapperLayout=TableRow(this)

                wisdom.orientation=LinearLayout.VERTICAL
                buttons.orientation=LinearLayout.VERTICAL
                wrapperLayout.orientation=LinearLayout.VERTICAL

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
                        image.setImageBitmap(BitmapFactory.decodeByteArray(barray, 0, barray.size).scale(300, 300))
                    }
                }

                image.maxWidth=540
                image.minimumWidth=540
                name.maxWidth=540
                name.minWidth=540

                buttons.layoutParams=ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                vk.layoutParams=ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

                minusButt.setOnClickListener {
                    Buttonizer.change(-1, x, enumera)
                    if (x.QuantityInStore!=x.QuantityInSet) vk.setBackgroundColor(Color.rgb(255, 255, 255))
                }

                plusButt.setOnClickListener {
                    Buttonizer.change(1, x, enumera)
                    if (x.QuantityInStore==x.QuantityInSet) vk.setBackgroundColor(Color.rgb(204, 255, 153))
                }
                if (x.QuantityInStore==x.QuantityInSet) vk.setBackgroundColor(Color.rgb(204, 255, 153))

                wisdom.addView(name)
                wisdom.addView(color_id)
                wisdom.addView(enumera)
                wrapperLayout.addView(image)
                wrapperLayout.addView(wisdom)

                buttons.addView(plusButt)
                buttons.addView(minusButt)

                vk.addView(wrapperLayout)
                vk.addView(buttons)

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
            exportXML(this@SingleSet, applicationContext)
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

wrapperLayout.gravity=Gravity.LEFT
                wrapperLayout.layoutParams=ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                (wrapperLayout.layoutParams as LinearLayout.LayoutParams).weight= 0.5F
*/