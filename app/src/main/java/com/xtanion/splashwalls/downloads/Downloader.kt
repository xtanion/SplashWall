package com.xtanion.splashwalls.downloads

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.widget.Toast

private var downloadID:Long = 0
fun downloadWall(
    context: Context?,
    url: String,
    title:String
){
    val request = DownloadManager.Request (
        Uri.parse(url))
        .setTitle("${title}.jpg")
        .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        .setAllowedOverMetered(true)
        .setAllowedOverRoaming(true)
        .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,"${title}.jpg")

    val dm = context?.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
    downloadID = dm.enqueue(request)
    val br = object : BroadcastReceiver(){
        override fun onReceive(p0: Context?, p1: Intent?) {
            val id: Long? = p1?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID,-1)
            if (id== downloadID){
                Toast.makeText(context,"Downloaded Successfully",Toast.LENGTH_SHORT).show()
            }
        }
    }

    context.registerReceiver(br, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
}