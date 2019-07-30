package com.example.wsupevents.ui.auth

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.auth.models.VerifyUId
import com.example.auth.models.VerifyUSecret
import com.example.wsupevents.models.auth.VerifyUIdRes
import com.example.wsupevents.models.auth.VerifyUSecretRes
import com.example.wsupevents.models.xit.Resource
import com.example.wsupevents.storage.auth.AuthRepository

class AuthViewModel  (application: Application) : AndroidViewModel(application) {
    private val verifyUIDObservable = MediatorLiveData<Resource<VerifyUIdRes>>()
    private val verifyUSecretObservable = MediatorLiveData<Resource<VerifyUSecretRes>>()
    var authRepository: AuthRepository = AuthRepository(application)

    init {
        verifyUIDObservable.addSource(authRepository.verifyUIDObservable) { data -> verifyUIDObservable.setValue(data) }
        verifyUSecretObservable.addSource(authRepository.verifyUSecretObservable) { data -> verifyUSecretObservable.setValue(data) }
    }
    fun observeVerifyUId(): LiveData<Resource<VerifyUIdRes>> {
        return verifyUIDObservable
    }
    fun verifyUID(verifyUId: VerifyUId){
        authRepository.verifyUID(verifyUId)
    }
    fun observeVerifyUSecret(): LiveData<Resource<VerifyUSecretRes>> {
        return verifyUSecretObservable
    }
    fun verifyUSecret(verifyUSecret: VerifyUSecret){
        authRepository.verifyUSecret(verifyUSecret)
    }
}