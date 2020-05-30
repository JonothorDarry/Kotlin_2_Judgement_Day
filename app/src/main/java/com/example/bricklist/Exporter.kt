package com.example.bricklist

import android.app.Activity
import android.content.Context
import android.os.Environment
import android.util.Log
import org.w3c.dom.Element
import java.io.*
import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.transform.OutputKeys
import javax.xml.transform.Transformer
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult

class Exporter {
    companion object{
        fun exportXML(activity: Activity, context: Context){
            PreservedSettings.verifyStoragePermissions(activity)
            val docBuilder: DocumentBuilder=DocumentBuilderFactory.newInstance().newDocumentBuilder()
            val doc=docBuilder.newDocument()


            val rootElement: Element =doc.createElement("INVENTORY")
            val base=Databaze.dbCreator(context)
            if (base!=null) {
                val lst = base.getMyrDao().getInvParts(PreservedProjects.projectId)
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

                    rootElement.appendChild(item)
                }
            }

            doc.appendChild(rootElement)

            val transformer: Transformer =TransformerFactory.newInstance().newTransformer()
            transformer.setOutputProperty(OutputKeys.INDENT, "yes")
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2")

            val filepath = "/mnt/sdcard/DCIM/"

            val outDir=File(filepath, "Output")
            outDir.mkdir()
            val file=File(outDir, "Text.xml")
            file.createNewFile()
            transformer.transform(DOMSource(doc), StreamResult(file))

        }
    }
}