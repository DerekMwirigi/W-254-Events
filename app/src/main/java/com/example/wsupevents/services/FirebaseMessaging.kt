package com.example.wsupevents.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.wsupevents.R
import com.example.wsupevents.storage.data.PrefrenceManager
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import org.json.JSONObject
import java.util.*

class FirebaseMessaging : FirebaseMessagingService() {
    val name = "dawaswiftNotifications"
    private val TAG = "taggs"
    override fun onNewToken(p0: String?) {
        super.onNewToken(p0)
        if (p0 != null) {
            PrefrenceManager(applicationContext).setFirebaseToken(p0)
            Log.d("firebaseToken", "Available Firebase$p0")
        }else{
            Log.d("firebaseToken", "No Firebase")
        }
    }
    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        if (remoteMessage!!.data.size > 0) {
            Log.d("messfiew", remoteMessage.data.toString())
            handleNow(remoteMessage.data.toString())
        }
    }
    private fun handleNow(result: String) {
        try {
            val json = JSONObject(result)
            val data = json.getJSONObject("data")
            val title = data.getString("title")
            val message = data.getString("message")
            val code = data.getInt("code")
            sendNotification(title, message, code)
        } catch (e: Exception) {
            Log.e(TAG, e.toString())
        }
    }

    private fun sendNotification(title: String, message: String, code: Int) {
        try {
            showNotification(title, message, code)
        } catch (x: java.lang.Exception) {
            Log.e(TAG, x.toString())
        }
    }

    internal fun showNotification(title: String, content: String, code: Int) {
        try {
            createNotificationChannel()
            //val intent = Intent(applicationContext, MainActivity::class.java)
           // intent.putExtra("type", "notification_cart")
            Log.e(TAG, "Tumefika hapa")
            //val pi = PendingIntent.getActivity(applicationContext, 0, intent, 0)
            //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            val mNotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                val channel = NotificationChannel(
                    name,
                    name,
                    NotificationManager.IMPORTANCE_DEFAULT
                )
                channel.description = "eric"
                mNotificationManager.createNotificationChannel(channel)
            }
            val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

            val mBuilder = NotificationCompat.Builder(applicationContext, "default")
                .setSmallIcon(R.mipmap.ic_launcher) // notification icon
                .setContentTitle(title) // title for notification
                .setContentText(content)// message for notification
                .setSound(defaultSoundUri) // set alarm sound for notification
                .setAutoCancel(true) // clear notification after click
            //Intent intent = new Intent(getApplicationContext(), ProductsActivity.class);
//            if (intent != null) {
//                val pi = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
//                mBuilder.setContentIntent(pi)
//            }
            mNotificationManager.notify(Random().nextInt(), mBuilder.build())
            var builder = NotificationCompat.Builder(this, name)
                .setSmallIcon(R.drawable.ic_shortcut_events)
                .setContentTitle(title)
                .setContentText(content)
                .setStyle(
                    NotificationCompat.BigTextStyle()
                        .bigText(content)
                )
                .setPriority(NotificationCompat.PRIORITY_HIGH)

            with(NotificationManagerCompat.from(this)) {
                // notificationId is a unique int for each notification that you must define
                notify(Random().nextInt(), builder.build())
            }
            // mNotificationManager.notify(Random().nextInt(), builder.build())

        } catch (x: java.lang.Exception) {
            Log.e(TAG, x.toString())

        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val descriptionText = "Display important notifications"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(name, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}
