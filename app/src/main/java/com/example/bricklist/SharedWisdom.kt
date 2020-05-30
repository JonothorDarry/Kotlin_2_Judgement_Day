package com.example.bricklist

class SharedWisdom {
    companion object{
        var communicate="No new project"
        var all=0
        var current=0
        var imageDead=0
        var nameDead=0

        var start=0
        var finish=0

        fun isProcess(): Boolean{
            if (start==1 && finish==0) return true
            return false
        }
        fun isEndet(): Boolean{
            if (start==1 && finish==1) return true
            return false
        }
        fun zeroIfFinished(){
            if (start==0 || finish==0) return
            start=0
            finish=0
        }

        lateinit var element: DbInventories
    }
}