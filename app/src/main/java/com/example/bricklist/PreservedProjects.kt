package com.example.bricklist

import android.annotation.SuppressLint
import android.app.Application
import org.jsoup.nodes.Document

@SuppressLint("Registered")
class PreservedProjects : Application() {
    companion object {
        var allProjects=mutableListOf<Document>()
    }
}