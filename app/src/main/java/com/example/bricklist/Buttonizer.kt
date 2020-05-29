package com.example.bricklist

import android.util.Log
import android.widget.TextView

class Buttonizer {
    companion object{
        fun change(amount: Int, obj: DbInventoriesParts, changer: TextView){
            Log.d("TAG", (obj.QuantityInStore+amount).toString())
            if (obj.QuantityInStore+amount < 0 || obj.QuantityInStore+amount > obj.QuantityInSet) return
            val db=Databaze.myDb
            Log.d("TAG", "KAPPA")

            if (db!=null)  {
                Log.d("TAG", "PENIS")
                db.getMyrDao().updateStore(obj.QuantityInStore+amount, obj.id)
                obj.QuantityInStore+=amount
                changer.text=obj.QuantityInStore.toString()+" of "+obj.QuantityInSet.toString()
            }
        }
    }
}