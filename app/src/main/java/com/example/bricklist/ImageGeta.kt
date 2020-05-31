package com.example.bricklist

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.net.HttpURLConnection
import java.net.URL

class ImageGeta {
    companion object{
        fun checkExistence(url: String): Bitmap?{
            val huc: HttpURLConnection = java.net.URL(url).openConnection() as HttpURLConnection
            val responseCode: Int = huc.responseCode
            if (responseCode == 404) return null

            return BitmapFactory.decodeStream(URL(url).openConnection().getInputStream())
        }

        fun getImage(colorID: Int, itemID: Int): Bitmap?{
            val db=Databaze.myDb
            val st1="https://www.lego.com/service/bricks/5/2/"
            val st2="http://img.bricklink.com/P/"
            val st3="https://www.bricklink.com/PL/"
            var im1: Bitmap?
            var kode: Int?=0
            var kolor: Int?=0
            var itype: String?=""

            if (db!=null) {
                kode = db.getMyrDao().getCode(colorID, itemID)
                kolor=db.getMyrDao().getColorNumber(colorID)
                itype=db.getMyrDao().getCode(itemID)
            }

            im1=checkExistence(st1+kode?.toString())
            if (im1!=null) return im1

            im1=checkExistence("$st2$kolor/$itype.gif")
            if (im1!=null) return im1

            im1= checkExistence("$st3$itype.jpg")
            return im1
        }
    }
}