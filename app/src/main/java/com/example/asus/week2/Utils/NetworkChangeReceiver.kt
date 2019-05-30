package com.example.asus.week2.Utils

import android.app.AlertDialog
import android.os.AsyncTask.execute
import android.content.Intent
import android.content.BroadcastReceiver
import android.content.Context
import android.util.Log
import android.content.DialogInterface




class NetworkChangeReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        val status = NetworkUtils.getConnectivityStatusString(context)
        if ("android.net.conn.CONNECTIVITY_CHANGE" == intent.action) {
            if (status == NetworkUtils.NETWORK_STATUS_NOT_CONNECTED) {
                val builder = AlertDialog.Builder(context)
                builder.setTitle("Thông báo")
                builder.setMessage("Vui lòng kiếm tra lại kết nối Internet để tiếp tục trải nghiệm")
                builder.create().show()
            }
        }
    }
}