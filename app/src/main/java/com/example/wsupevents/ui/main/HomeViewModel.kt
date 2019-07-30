package com.example.wsupevents.ui.main

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.wsupevents.R
import com.example.wsupevents.models.auth.User
import com.example.wsupevents.models.xit.Resource
import com.example.wsupevents.storage.data.PrefrenceManager
import com.example.wsupevents.storage.main.HomeRepository
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId

class HomeViewModel  (application: Application) : AndroidViewModel(application) {
    private var context: Context = application.applicationContext
    var homeRepository: HomeRepository = HomeRepository(application)
    private val updateFirebaseTokenObservable = MediatorLiveData<Resource<User>>()
    init {
        initNotificationChannel()
        initFirebase()
        updateFirebaseTokenObservable.addSource(homeRepository.updateFirebaseTokenObservable) { data -> updateFirebaseTokenObservable.setValue(data) }
    }

    fun observeFirebaseUpdate () : LiveData<Resource<User>> {
        return updateFirebaseTokenObservable
    }

    private fun initNotificationChannel () {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create channel to show notifications.
            val channelId = context.getString(R.string.default_notification_channel_id)
            val channelName = context.getString(R.string.default_notification_channel_id)
            val notificationManager = context.getSystemService(NotificationManager::class.java)
            notificationManager?.createNotificationChannel(
                NotificationChannel(channelId,
                    channelName, NotificationManager.IMPORTANCE_LOW)
            )
        }
    }
    private fun initFirebase (){
        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w("firebaseToken", "getInstanceId failed", task.exception)
                    return@OnCompleteListener
                }
                PrefrenceManager(context).setFirebaseToken(task.result?.token)
            })
        var user: User? = PrefrenceManager(context).getUserSession()
        if(user?.firebaseToken != PrefrenceManager(context).getFirebaseToken()){
            user?.firebaseToken = PrefrenceManager(context).getFirebaseToken()
            PrefrenceManager(context).setUserSession(user)
            homeRepository.updateFirebaseToken(PrefrenceManager(context).getFirebaseToken()!!)
        }
    }
}