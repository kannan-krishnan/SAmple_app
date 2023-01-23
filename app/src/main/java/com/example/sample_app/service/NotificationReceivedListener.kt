package com.example.sample_app.service

import com.example.sample_app.Model.NotificationMbo

/**
 * Created by #kannanpvm007 on  02/09/22.
 */
class NotificationReceivedListener {
    var listener: OnNotificationReceivedListener? = null

    interface OnNotificationReceivedListener{
        fun onNotificationReceived(bundle:  NotificationMbo.Data?)
    }
    fun setOnNotificationReceivedListener(param: OnNotificationReceivedListener) {
        listener = param
    }
    fun notificationReceived(bundle: NotificationMbo.Data){

        listener?.let {
            it.onNotificationReceived(bundle)
        }
    }
}