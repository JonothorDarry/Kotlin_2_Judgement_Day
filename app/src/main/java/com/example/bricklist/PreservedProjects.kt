package com.example.bricklist

import android.app.Application
import org.jsoup.nodes.Document

class PreservedProjects : Application() {
    companion object {
        var allProjects=mutableListOf<Document>()
    }
}