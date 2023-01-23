package com.example.sample_app.service


import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.media.AudioAttributes
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.sample_app.Model.Data
import com.example.sample_app.Model.NotificationMbo
import com.example.sample_app.Model.SendPushMessage
import com.example.sample_app.R
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 * Created by #kannanpvm007 on  21/07/22.
 */

class FirebaseMessagingService : com.google.firebase.messaging.FirebaseMessagingService() {
    private val TAG="FirebaseMessaging"

companion object{
    var notificationReceivedListener = NotificationReceivedListener()


    fun subscribeTopic(context: Context, topic: String) {
        FirebaseMessaging.getInstance().subscribeToTopic(topic).addOnSuccessListener {
            Toast.makeText(context, "Subscribed $topic", Toast.LENGTH_LONG).show()
        }.addOnFailureListener {
            Toast.makeText(context, "Failed to Subscribe $topic", Toast.LENGTH_LONG).show()
        }
    }
    fun unsubscribeTopic(context: Context, topic: String) {
        FirebaseMessaging.getInstance().unsubscribeFromTopic(topic).addOnSuccessListener {
            Toast.makeText(context, "Unsubscribed $topic", Toast.LENGTH_LONG).show()
        }.addOnFailureListener {
            Toast.makeText(context, "Failed to Unsubscribe $topic", Toast.LENGTH_LONG).show()
        }
    }

    fun sendNotificationToUser(user: String?, message: String?) {

        val headers = HashMap<String, String>()
        headers["Authorization"] = "key= AAAAEeeM-Zw:APA91bFdD4MBPT7x6HyNscfci2keX4LfPaK69Z6m0LtEFq6rIaN6F6Av5z6mskTZIB7oko8dpST867Qhq1FEt2ueaeNWAIaR8W_e43HDtBtYDEc1dX94usPo8bllMvjcxAhyZ6uUpmer "
        headers["Content-Type"] = "application/json"

        val jsonObject= JsonObject()

        val apiService: ApiInterface? = ApiClient().getClient()?.create(ApiInterface::class.java)
        apiService?.sendNotification(headers, SendPushMessage( to="$user", data = Data(
            body = "send DAta----------------->"
        , key1 = message
        )
        )
        )?.enqueue(object :
            Callback<JsonElement?> {
            override fun onResponse(call: Call<JsonElement?>?, response: Response<JsonElement?>?) {
                Log.d("TAG", "Successfully notification send by using retrofit. ${response?.raw()}")
                Log.d("TAG", "Successfully notification send by using retrofit. ${response?.body().toString()}")
            }

            override fun onFailure(call: Call<JsonElement?>?, t: Throwable?) {
            }
        })
    }
}


    override fun onCreate() {
        Log.d(TAG, "onCreate: onNewToken ")
        super.onCreate()

    }


    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        Log.d(TAG, "onMessageReceived: ${message.data.size}")



        message.data.let {
            Log.d(TAG, "onMessageReceived: ${message.data}")
          val remoteMessage = Gson().fromJson(Gson().toJson(message.data), NotificationMbo.Data::class.java)

            Log.d(TAG, "onMessageReceived: remoteMessage-------------->${remoteMessage}")
            showNotification(remoteMessage)
            notificationReceivedListener.notificationReceived(remoteMessage)
        }
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)

        Log.e(TAG, "onNewToken token : $token" )
    }


    private fun showNotification(remoteMessage: NotificationMbo.Data) {


        providesNotificationManager(applicationContext)

            .notify(1,
                providesNotificationBuilder(applicationContext)
                    .setContentText(remoteMessage.body)
                    .setContentTitle(remoteMessage.title)
//                    .setSound(soundUri, attributes)
//                    .setContentIntent(chatScreenIntent)

                .build()
            )
    }


    private fun providesNotificationBuilder(context: Context): NotificationCompat.Builder {

        return NotificationCompat
            .Builder(context, "_rx_channel_id")
            .setContentTitle("")
            .setContentText("")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
    }


    private fun providesNotificationManager(context: Context): NotificationManagerCompat {
//        val soundUri =Uri.parse("android.resource://" + applicationContext.packageName + "/" + R.raw.send_message)
        val attributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_NOTIFICATION)
            .build()
        val notificationManager = NotificationManagerCompat.from(context)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                "_rx_channel_id", "main",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationChannel.enableLights(true)
            notificationChannel.enableVibration(true);
//            notificationChannel.setSound(soundUri,  Notification.AUDIO_ATTRIBUTES_DEFAULT)
            notificationManager.createNotificationChannel(notificationChannel)
        }
        return notificationManager

    }



}