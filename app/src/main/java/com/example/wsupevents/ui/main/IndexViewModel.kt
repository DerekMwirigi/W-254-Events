package com.example.wsupevents.ui.main

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

class IndexViewModel (application: Application) : AndroidViewModel(application) {
    private var context: Context = application.applicationContext
    private val titleObservable = MediatorLiveData<String>()
    fun observeTitle () : LiveData<String> {
        return titleObservable
    }
    fun setTitle (title: String) {
        titleObservable.postValue(title)
    }
}