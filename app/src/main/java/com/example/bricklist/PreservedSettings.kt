package com.example.bricklist

import android.Manifest
import android.app.Activity
import android.app.Application
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat


class PreservedSettings : Application(){
    companion object {
        var showArchived=false
        var page = "http://fcds.cs.put.poznan.pl/MyWeb/BL/"
        var condition="U"
        var format="Name"
        var fileDir="/mnt/sdcard/DCIM/"



        // Storage Permissions
        private const val REQUEST_EXTERNAL_STORAGE = 1
        private val PERMISSIONS_STORAGE = arrayOf<String>(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        fun verifyStoragePermissions(activity: Activity?) {
            val permission = ActivityCompat.checkSelfPermission(
                activity!!,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            if (permission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
                )
            }
        }
    }


}