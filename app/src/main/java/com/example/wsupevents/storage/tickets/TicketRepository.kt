package com.example.wsupevents.storage.tickets

import android.app.Application
import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.wsupevents.R
import com.example.wsupevents.models.tickets.HistoryTicketsRes
import com.example.wsupevents.models.xit.Resource
import com.example.wsupevents.utils.NetworkUtils
import com.example.wsupevents.utils.RequestService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TicketRepository (application: Application) {
    val ticketsObservable = MutableLiveData<Resource<HistoryTicketsRes>>()
    private val context: Context = application.applicationContext
    fun fetchTickets() {
        if (NetworkUtils.isConnected(context)) {
            ticketsObservable.postValue(Resource.loading(null))
            GlobalScope.launch(context = Dispatchers.Main) {
                val call = RequestService.getService("").fetchTickets()
                call.enqueue(object : Callback<HistoryTicketsRes> {
                    override fun onFailure(call: Call<HistoryTicketsRes>?, t: Throwable?) {
                        ticketsObservable.postValue(Resource.error(t.toString(), null))
                    }
                    override fun onResponse(call: Call<HistoryTicketsRes>?, response: Response<HistoryTicketsRes>?) {
                        if (response != null) {
                            if (response.isSuccessful) {
                                if (response.body()?.success !!) {
                                    if(response.body()?.status_code == 1){
                                        response.body()?.let { ticketsObservable.postValue(Resource.success(it))  }
                                    }else{
                                        response.body()?.errors?.let {  ticketsObservable.postValue(Resource.error(response.body()?.message + "\n" + it.joinToString { "\n" }, null)) }
                                    }
                                } else {
                                    response.body()?.errors?.let { ticketsObservable.postValue(Resource.error( response.body()?.message + "\n" + it.joinToString { "\n" }, null)) }
                                }
                            } else {
                                ticketsObservable.postValue(Resource.error( response.toString(), null))
                            }
                        } else {
                            ticketsObservable.postValue(Resource.error("Error Logging In", null))
                        }
                    }
                })
            }
        } else {
            ticketsObservable.postValue(Resource.error(context.getString(R.string.no_connection), null))
        }
    }
}