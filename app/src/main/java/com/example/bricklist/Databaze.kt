package com.example.bricklist

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [DbColors::class], version = 3)
abstract class Databaze : RoomDatabase(){

    abstract fun getMyrDao(): MyDao

    companion object{
        var myDb :Databaze?=null

        fun dbCreator(context: Context): Databaze?{
            myDb= Room.databaseBuilder(context,
                Databaze::class.java, "BrickList.db")
                .createFromAsset("databases/BrickList.db")
                .allowMainThreadQueries()
                .build()
            return myDb
        }
    }
}