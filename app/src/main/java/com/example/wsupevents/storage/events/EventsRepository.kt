package com.example.wsupevents.storage.events

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.wsupevents.R
import com.example.wsupevents.models.events.EventCategoriesRes
import com.example.wsupevents.models.events.EventRes
import com.example.wsupevents.models.events.EventsRes
import com.example.wsupevents.models.xit.Resource
import com.example.wsupevents.utils.NetworkUtils
import com.example.wsupevents.utils.RequestService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EventsRepository (application: Application) {
    val eventObservable = MutableLiveData<Resource<EventRes>>()
    val eventsObservable = MutableLiveData<Resource<EventsRes>>()
    val categoriesObservable = MutableLiveData<Resource<EventCategoriesRes>>()
    private val context: Context = application.applicationContext
    fun viewEvent(id: Int) {
        if (NetworkUtils.isConnected(context)) {
            eventObservable.postValue(Resource.loading(null))
            GlobalScope.launch(context = Dispatchers.Main) {
                val call = RequestService.getService("").viewEvent(id)
                call.enqueue(object : Callback<EventRes> {
                    override fun onFailure(call: Call<EventRes>?, t: Throwable?) {
                        eventObservable.postValue(Resource.error(t.toString(), null))
                    }
                    override fun onResponse(call: Call<EventRes>?, response: Response<EventRes>?) {
                        if (response != null) {
                            if (response.isSuccessful) {
                                if (response.body()?.success !!) {
                                    if(response.body()?.status_code == 1){
                                        response.body()?.let { eventObservable.postValue(Resource.success(it))  }
                                    }else{
                                        response.body()?.errors?.let {  eventObservable.postValue(Resource.error(response.body()?.message + "\n" + it.joinToString { "\n" }, null)) }
                                    }
                                } else {
                                    response.body()?.errors?.let { eventObservable.postValue(Resource.error( response.body()?.message + "\n" + it.joinToString { "\n" }, null)) }
                                }
                            } else {
                                eventObservable.postValue(Resource.error( response.toString(), null))
                            }
                        } else {
                            eventObservable.postValue(Resource.error("Error Logging In", null))
                        }
                    }
                })
            }
        } else {
            eventObservable.postValue(Resource.error(context.getString(R.string.no_connection), null))
        }
    }
    fun fetchEvents() {
        if (NetworkUtils.isConnected(context)) {
            eventsObservable.postValue(Resource.loading(null))
            GlobalScope.launch(context = Dispatchers.Main) {
                val call = RequestService.getService("").fetchEvents()
                call.enqueue(object : Callback<EventsRes> {
                    override fun onFailure(call: Call<EventsRes>?, t: Throwable?) {
                        eventsObservable.postValue(Resource.error(t.toString(), null))
                    }
                    override fun onResponse(call: Call<EventsRes>?, response: Response<EventsRes>?) {
                        if (response != null) {
                            if (response.isSuccessful) {
                                if (response.body()?.success !!) {
                                    if(response.body()?.status_code == 1){
                                        response.body()?.let { eventsObservable.postValue(Resource.success(it))  }
                                    }else{
                                        response.body()?.errors?.let {  eventsObservable.postValue(Resource.error(response.body()?.message + "\n" + it.joinToString { "\n" }, null)) }
                                    }
                                } else {
                                    response.body()?.errors?.let { eventsObservable.postValue(Resource.error( response.body()?.message + "\n" + it.joinToString { "\n" }, null)) }
                                }
                            } else {
                                eventsObservable.postValue(Resource.error( response.toString(), null))
                            }
                        } else {
                            eventsObservable.postValue(Resource.error("Error Logging In", null))
                        }
                    }
                })
            }
        } else {
            eventsObservable.postValue(Resource.error(context.getString(R.string.no_connection), null))
        }
    }

    fun fetchCategories() {
        if (NetworkUtils.isConnected(context)) {
            categoriesObservable.postValue(Resource.loading(null))
            GlobalScope.launch(context = Dispatchers.Main) {
                val call = RequestService.getService("").fetchCategories()
                call.enqueue(object : Callback<EventCategoriesRes> {
                    override fun onFailure(call: Call<EventCategoriesRes>?, t: Throwable?) {
                        categoriesObservable.postValue(Resource.error(t.toString(), null))
                    }
                    override fun onResponse(call: Call<EventCategoriesRes>?, response: Response<EventCategoriesRes>?) {
                        if (response != null) {
                            if (response.isSuccessful) {
                                if (response.body()?.success !!) {
                                    if(response.body()?.status_code == 1){
                                        response.body()?.let { categoriesObservable.postValue(Resource.success(it))  }
                                    }else{
                                        response.body()?.errors?.let {  categoriesObservable.postValue(Resource.error(response.body()?.message + "\n" + it.joinToString { "\n" }, null)) }
                                    }
                                } else {
                                    response.body()?.errors?.let { categoriesObservable.postValue(Resource.error( response.body()?.message + "\n" + it.joinToString { "\n" }, null)) }
                                }
                            } else {
                                categoriesObservable.postValue(Resource.error( response.toString(), null))
                            }
                        } else {
                            categoriesObservable.postValue(Resource.error("Error Logging In", null))
                        }
                    }
                })
            }
        } else {
            categoriesObservable.postValue(Resource.error(context.getString(R.string.no_connection), null))
        }
    }
}