package com.example.bricklist

import android.os.AsyncTask
import android.os.Looper

class DoAsync (val handler: () -> Unit) : AsyncTask<Void, Void, Void>() {
    override fun doInBackground(vararg params: Void?): Void? {
        handler()
        return null
    }
}