package com.example.wsupevents.storage.main

import android.app.Application
import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.wsupevents.R
import com.example.wsupevents.models.auth.User
import com.example.wsupevents.models.auth.VerifyUSecretRes
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

class HomeRepository (application: Application) {
    val updateFirebaseTokenObservable = MutableLiveData<Resource<User>>()
    private val context: Context = application.applicationContext

    fun updateFirebaseToken (firebaseToken: String) {
        if (NetworkUtils.isConnected(context)) {
            updateFirebaseTokenObservable.postValue(Resource.loading(null))
            GlobalScope.launch(context = Dispatchers.Main) {
                val call = RequestService.getService("Bearer " + PrefrenceManager(context).getUserSession()?.token).updateFirebaseToken(firebaseToken)
                call.enqueue(object : Callback<VerifyUSecretRes> {
                    override fun onFailure(call: Call<VerifyUSecretRes>?, t: Throwable?) {
                        updateFirebaseTokenObservable.postValue(Resource.error(t.toString(), null))
                    }
                    override fun onResponse(call: Call<VerifyUSecretRes>?, response: Response<VerifyUSecretRes>?) {
                        if (response != null) {
                            if (response.isSuccessful) {
                                if (response.body()?.success !!) {
                                    if(response.body()?.status_code == 1){
                                        response.body()?.let { updateFirebaseTokenObservable.postValue(Resource.success(it.data!!))  }
                                    }else{
                                        response.body()?.errors?.let {  updateFirebaseTokenObservable.postValue(Resource.error(response.body()?.message + "\n" + it.joinToString { "\n" }, null)) }
                                    }
                                } else {
                                    response.body()?.errors?.let { updateFirebaseTokenObservable.postValue(Resource.error( response.body()?.message + "\n" + it.joinToString { "\n" }, null)) }
                                }
                            } else {
                                updateFirebaseTokenObservable.postValue(Resource.error( response.toString(), null))
                            }
                        } else {
                            updateFirebaseTokenObservable.postValue(Resource.error("Error Logging In", null))
                        }
                    }
                })
            }
        } else {
            updateFirebaseTokenObservable.postValue(Resource.error(context.getString(R.string.no_connection), null))
        }
    }
}