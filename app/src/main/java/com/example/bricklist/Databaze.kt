package com.example.bricklist

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [DbColors::class, DbItemTypes::class, DbParts::class, DbInventories::class, DbInventoriesParts::class, DbCodes::class], version = 3)
abstract class Databaze : RoomDatabase(){

    abstract fun getMyrDao(): MyDao

    companion object{
        var myDb :Databaze?=null
        var exist=0

        fun dbCreator(context: Context): Databaze?{
            if (exist==1) return myDb
            myDb= Room.databaseBuilder(context,
                Databaze::class.java, "BrickList.db")
                .createFromAsset("databases/BrickList.db")
                .allowMainThreadQueries()
                .build()
            exist=1
            return myDb
        }
    }
}