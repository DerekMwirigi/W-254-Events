package com.example.wsupevents.ui.profile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.wsupevents.models.auth.User
import com.example.wsupevents.storage.profile.ProfileRepository

class ProfileViewModel  (application: Application) : AndroidViewModel(application) {
    private val profileObservable = MediatorLiveData<User>()
    var authRepository: ProfileRepository = ProfileRepository(application)

    init {
        profileObservable.addSource(authRepository.profileObservable) { data -> profileObservable.setValue(data) }
    }
    fun observeProfile(): LiveData<User> {
        return profileObservable
    }

    fun getProfile(){
        authRepository.getProfile()
    }
}