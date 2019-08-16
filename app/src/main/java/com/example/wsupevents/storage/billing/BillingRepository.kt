package com.example.wsupevents.storage.billing

import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MediatorLiveData
import com.example.wsupevents.R
import com.example.wsupevents.models.billing.BillingAddress
import com.example.wsupevents.models.billing.BillingAddressRes
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

class BillingRepository (application: Application) {
    private val context: Context = application.applicationContext
    val billingAddressesObservable = MediatorLiveData<Resource<BillingAddressRes>>()
    fun saveBillingAddress (billingAddress: BillingAddress) {
        if (NetworkUtils.isConnected(context)) {
            billingAddressesObservable.postValue(Resource.loading(null))
            GlobalScope.launch(context = Dispatchers.Main) {
                val call = RequestService.getService("Bearer " + PrefrenceManager(context).getUserSession()?.token).saveBillingAddress(billingAddress)
                call.enqueue(object : Callback<BillingAddressRes> {
                    override fun onFailure(call: Call<BillingAddressRes>?, t: Throwable?) {
                        Log.d("response", t.toString())
                        billingAddressesObservable.postValue(Resource.error(t.toString(), null))
                    }
                    override fun onResponse(call: Call<BillingAddressRes>?, response: Response<BillingAddressRes>?) {
                        Log.d("response", response.toString())
                        if (response != null) {
                            if (response.isSuccessful) {
                                if (response.body()?.success !!) {
                                    if(response.body()?.status_code == 1){
                                        response.body()?.let { billingAddressesObservable.postValue(Resource.success(it))  }
                                    }else{
                                        response.body()?.errors?.let {  billingAddressesObservable.postValue(Resource.error(response.body()?.message + "\n" + it.joinToString { "\n" }, null)) }
                                    }
                                } else {
                                    response.body()?.errors?.let { billingAddressesObservable.postValue(Resource.error( response.body()?.message + "\n" + it.joinToString { "\n" }, null)) }
                                }
                            } else {
                                billingAddressesObservable.postValue(Resource.error( response.toString(), null))
                            }
                        } else {
                            billingAddressesObservable.postValue(Resource.error("Error Logging In", null))
                        }
                    }
                })
            }
        } else {
            billingAddressesObservable.postValue(Resource.error(context.getString(R.string.no_connection), null))
        }
    }
    fun getBillingAddresses() {
        if (NetworkUtils.isConnected(context)) {
            billingAddressesObservable.postValue(Resource.loading(null))
            GlobalScope.launch(context = Dispatchers.Main) {
                val call = RequestService.getService("Bearer " + PrefrenceManager(context).getUserSession()?.token).getBillingAddresses()
                call.enqueue(object : Callback<BillingAddressRes> {
                    override fun onFailure(call: Call<BillingAddressRes>?, t: Throwable?) {
                        billingAddressesObservable.postValue(Resource.error(t.toString(), null))
                    }
                    override fun onResponse(call: Call<BillingAddressRes>?, response: Response<BillingAddressRes>?) {
                        if (response != null) {
                            if (response.isSuccessful) {
                                if (response.body()?.success !!) {
                                    if(response.body()?.status_code == 1){
                                        response.body()?.let { billingAddressesObservable.postValue(Resource.success(it))  }
                                    }else{
                                        response.body()?.errors?.let {  billingAddressesObservable.postValue(Resource.error(response.body()?.message + "\n" + it.joinToString { "\n" }, null)) }
                                    }
                                } else {
                                    response.body()?.errors?.let { billingAddressesObservable.postValue(Resource.error( response.body()?.message + "\n" + it.joinToString { "\n" }, null)) }
                                }
                            } else {
                                billingAddressesObservable.postValue(Resource.error( response.toString(), null))
                            }
                        } else {
                            billingAddressesObservable.postValue(Resource.error("Error Logging In", null))
                        }
                    }
                })
            }
        } else {
            billingAddressesObservable.postValue(Resource.error(context.getString(R.string.no_connection), null))
        }
    }
}