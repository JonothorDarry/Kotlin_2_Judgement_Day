package com.example.bricklist

import android.graphics.Bitmap
import android.util.Log
import androidx.core.graphics.drawable.toDrawable
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import java.io.ByteArrayOutputStream

class XMLOperations {
    companion object{
        var id=-1

        fun createInvPartFromXml(xml: Document, InventoryID: Int, db: Databaze){
            if (id==-1){
                val st=db.getMyrDao().getMaxInvPartId()
                if (st!=null) id=st+1
                else id=1
                Log.d("TAG", id.toString())
            }

            var y: Element
            var Extra=0
            var QuantityInSet=0
            var QuantityInStore=0
            var color=0

            var itemid: String
            var itemtype: String

            var TypeID: Int
            var ItemID: Int
            var ColorID: Int
            var vect: List<Int>



            for (x in xml.getElementsByTag("ITEM")){
                if (x.getElementsByTag("ALTERNATE").text()!="N") continue

                y=x.getElementsByTag("EXTRA")[0]
                if (y.text()=="P") Extra=1

                QuantityInSet=x.getElementsByTag("QTY")[0].text().toInt()
                color=x.getElementsByTag("COLOR")[0].text().toInt()

                itemid=x.getElementsByTag("ITEMID")[0].text()
                itemtype=x.getElementsByTag("ITEMTYPE")[0].text()


                ColorID=db.getMyrDao().getColorID(color)[0]

                vect=db.getMyrDao().getItemID(itemid)
                if (vect.isNotEmpty()) ItemID=vect[0]
                else ItemID=0

                TypeID=db.getMyrDao().getTypeID(itemtype)[0]

                val elem=DbInventoriesParts(id, InventoryID, TypeID, ItemID, QuantityInSet, QuantityInStore, ColorID, Extra)
                db.getMyrDao().insertInventoryPart(elem)
                val img=ImageGeta.getImage(ColorID, ItemID)
                if (img!=null){
                    val stream = ByteArrayOutputStream()
                    img.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                    val bitmapdata = stream.toByteArray()

                    if (db.getMyrDao().existCode(ItemID, ColorID)==0) db.getMyrDao().createCode(db.getMyrDao().getMaxCode()+1, ItemID, ColorID)
                    db.getMyrDao().setImage(ColorID, ItemID, bitmapdata)
                }

                id += 1
                Log.d("TAG", id.toString())
            }
            db.getMyrDao().setArchivize(InventoryID, 2)
        }
    }
}