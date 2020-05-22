package com.example.bricklist
import android.app.Application

class PreservedSettings : Application(){
    companion object {
        var showArchived=false
        var page = "http://fcds.cs.put.poznan.pl/MyWeb/BL/"
    }
}