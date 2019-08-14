package com.example.wsupevents.storage.events

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.MediatorLiveData
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
    val featuredObservable = MediatorLiveData<Resource<EventsRes>>()
    val favouriteObservable = MediatorLiveData<Resource<EventsRes>>()
    val moreObservable = MediatorLiveData<Resource<EventsRes>>()
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

    fun featuredEvents() {
        if (NetworkUtils.isConnected(context)) {
            featuredObservable.postValue(Resource.loading(null))
            GlobalScope.launch(context = Dispatchers.Main) {
                val call = RequestService.getService("").featuredEvents()
                call.enqueue(object : Callback<EventsRes> {
                    override fun onFailure(call: Call<EventsRes>?, t: Throwable?) {
                        featuredObservable.postValue(Resource.error(t.toString(), null))
                    }
                    override fun onResponse(call: Call<EventsRes>?, response: Response<EventsRes>?) {
                        if (response != null) {
                            if (response.isSuccessful) {
                                if (response.body()?.success !!) {
                                    if(response.body()?.status_code == 1){
                                        response.body()?.let { featuredObservable.postValue(Resource.success(it))  }
                                    }else{
                                        response.body()?.errors?.let {  featuredObservable.postValue(Resource.error(response.body()?.message + "\n" + it.joinToString { "\n" }, null)) }
                                    }
                                } else {
                                    response.body()?.errors?.let { featuredObservable.postValue(Resource.error( response.body()?.message + "\n" + it.joinToString { "\n" }, null)) }
                                }
                            } else {
                                featuredObservable.postValue(Resource.error( response.toString(), null))
                            }
                        } else {
                            featuredObservable.postValue(Resource.error("Error Logging In", null))
                        }
                    }
                })
            }
        } else {
            featuredObservable.postValue(Resource.error(context.getString(R.string.no_connection), null))
        }
    }

    fun favouriteEvents() {
        if (NetworkUtils.isConnected(context)) {
            favouriteObservable.postValue(Resource.loading(null))
            GlobalScope.launch(context = Dispatchers.Main) {
                val call = RequestService.getService("").favouriteEvents()
                call.enqueue(object : Callback<EventsRes> {
                    override fun onFailure(call: Call<EventsRes>?, t: Throwable?) {
                        favouriteObservable.postValue(Resource.error(t.toString(), null))
                    }
                    override fun onResponse(call: Call<EventsRes>?, response: Response<EventsRes>?) {
                        if (response != null) {
                            if (response.isSuccessful) {
                                if (response.body()?.success !!) {
                                    if(response.body()?.status_code == 1){
                                        response.body()?.let { favouriteObservable.postValue(Resource.success(it))  }
                                    }else{
                                        response.body()?.errors?.let {  favouriteObservable.postValue(Resource.error(response.body()?.message + "\n" + it.joinToString { "\n" }, null)) }
                                    }
                                } else {
                                    response.body()?.errors?.let { favouriteObservable.postValue(Resource.error( response.body()?.message + "\n" + it.joinToString { "\n" }, null)) }
                                }
                            } else {
                                favouriteObservable.postValue(Resource.error( response.toString(), null))
                            }
                        } else {
                            favouriteObservable.postValue(Resource.error("Error Logging In", null))
                        }
                    }
                })
            }
        } else {
            favouriteObservable.postValue(Resource.error(context.getString(R.string.no_connection), null))
        }
    }

    fun moreEvents() {
        if (NetworkUtils.isConnected(context)) {
            moreObservable.postValue(Resource.loading(null))
            GlobalScope.launch(context = Dispatchers.Main) {
                val call = RequestService.getService("").moreEvents()
                call.enqueue(object : Callback<EventsRes> {
                    override fun onFailure(call: Call<EventsRes>?, t: Throwable?) {
                        moreObservable.postValue(Resource.error(t.toString(), null))
                    }
                    override fun onResponse(call: Call<EventsRes>?, response: Response<EventsRes>?) {
                        if (response != null) {
                            if (response.isSuccessful) {
                                if (response.body()?.success !!) {
                                    if(response.body()?.status_code == 1){
                                        response.body()?.let { moreObservable.postValue(Resource.success(it))  }
                                    }else{
                                        response.body()?.errors?.let {  moreObservable.postValue(Resource.error(response.body()?.message + "\n" + it.joinToString { "\n" }, null)) }
                                    }
                                } else {
                                    response.body()?.errors?.let { moreObservable.postValue(Resource.error( response.body()?.message + "\n" + it.joinToString { "\n" }, null)) }
                                }
                            } else {
                                moreObservable.postValue(Resource.error( response.toString(), null))
                            }
                        } else {
                            moreObservable.postValue(Resource.error("Error Logging In", null))
                        }
                    }
                })
            }
        } else {
            moreObservable.postValue(Resource.error(context.getString(R.string.no_connection), null))
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