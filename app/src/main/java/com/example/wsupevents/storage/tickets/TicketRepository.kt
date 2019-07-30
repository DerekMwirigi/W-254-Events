package com.example.wsupevents.storage.tickets

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.wsupevents.R
import com.example.wsupevents.models.tickets.BuyTicketReq
import com.example.wsupevents.models.tickets.BuyTicketRes
import com.example.wsupevents.models.tickets.HistoryTicketsRes
import com.example.wsupevents.models.xit.Resource
import com.example.wsupevents.storage.data.PrefrenceManager
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
    val buyTicketObservable = MutableLiveData<Resource<BuyTicketRes>>()
    private val context: Context = application.applicationContext
    fun fetchTickets() {
        if (NetworkUtils.isConnected(context)) {
            ticketsObservable.postValue(Resource.loading(null))
            GlobalScope.launch(context = Dispatchers.Main) {
                val call = RequestService.getService("Bearer " + PrefrenceManager(context).getUserSession()?.token).fetchTickets()
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

    fun buyTicket(buyTicketReq: BuyTicketReq) {
        if (NetworkUtils.isConnected(context)) {
            buyTicketObservable.postValue(Resource.loading(null))
            GlobalScope.launch(context = Dispatchers.Main) {
                val call = RequestService.getService("Bearer " + PrefrenceManager(context).getUserSession()?.token).createTicket(buyTicketReq)
                call.enqueue(object : Callback<BuyTicketRes> {
                    override fun onFailure(call: Call<BuyTicketRes>?, t: Throwable?) {
                        Log.d("response", t.toString())
                        buyTicketObservable.postValue(Resource.error(t.toString(), null))
                    }
                    override fun onResponse(call: Call<BuyTicketRes>?, response: Response<BuyTicketRes>?) {
                        Log.d("response", response.toString())
                        if (response != null) {
                            if (response.isSuccessful) {
                                if (response.body()?.success !!) {
                                    if(response.body()?.status_code == 1){
                                        response.body()?.let { buyTicketObservable.postValue(Resource.success(it))  }
                                    }else{
                                        response.body()?.errors?.let {  buyTicketObservable.postValue(Resource.error(response.body()?.message + "\n" + it.joinToString { "\n" }, null)) }
                                    }
                                } else {
                                    response.body()?.errors?.let { buyTicketObservable.postValue(Resource.error( response.body()?.message + "\n" + it.joinToString { "\n" }, null)) }
                                }
                            } else {
                                buyTicketObservable.postValue(Resource.error( response.toString(), null))
                            }
                        } else {
                            buyTicketObservable.postValue(Resource.error("Error Logging In", null))
                        }
                    }
                })
            }
        } else {
            buyTicketObservable.postValue(Resource.error(context.getString(R.string.no_connection), null))
        }
    }
}