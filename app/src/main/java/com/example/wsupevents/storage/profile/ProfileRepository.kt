package com.example.wsupevents.storage.profile

import android.app.Application
import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.wsupevents.models.auth.User
import com.example.wsupevents.storage.data.PrefrenceManager

class ProfileRepository (application: Application) {
    val profileObservable = MutableLiveData<User>()
    private val context: Context = application.applicationContext

    fun getProfile () {
        profileObservable.postValue(PrefrenceManager(context).getUserSession()!!)
    }
}