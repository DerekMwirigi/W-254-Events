package com.example.wsupevents.storage.auth

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.auth.models.VerifyUId
import com.example.auth.models.VerifyUSecret
import com.example.wsupevents.R
import com.example.wsupevents.models.auth.VerifyUIdRes
import com.example.wsupevents.models.auth.VerifyUSecretRes
import com.example.wsupevents.models.xit.Resource
import com.example.wsupevents.storage.data.PrefrenceManager
import com.example.wsupevents.storage.data.room.RoomDb
import com.example.wsupevents.storage.data.room.SessionDao
import com.example.wsupevents.utils.NetworkUtils
import com.example.wsupevents.utils.RequestService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthRepository (application: Application) {
    val verifyUIDObservable = MutableLiveData<Resource<VerifyUIdRes>>()
    val verifyUSecretObservable = MutableLiveData<Resource<VerifyUSecretRes>>()
    private val context: Context
//    private val db: RoomDb = RoomDb.getDatabase(application)!!
//    private val sessionDao: SessionDao
    init {
        //sessionDao = db.getSesssionDao()
        context = application.applicationContext
    }
    fun verifyUID (verifyUId: VerifyUId) {
        if (NetworkUtils.isConnected(context)) {
            verifyUIDObservable.postValue(Resource.loading(null))
            GlobalScope.launch(context = Dispatchers.Main) {
                val call = RequestService.getService("").verifyUID(verifyUId)
                call.enqueue(object : Callback<VerifyUIdRes> {
                    override fun onFailure(call: Call<VerifyUIdRes>?, t: Throwable?) {
                        verifyUIDObservable.postValue(Resource.error(t.toString(), null))
                    }
                    override fun onResponse(call: Call<VerifyUIdRes>?, response: Response<VerifyUIdRes>?) {
                        if (response != null) {
                            if (response.isSuccessful) {
                                if (response.body()?.success !!) {
                                    if(response.body()?.status_code == 1){
                                        response.body()?.let { verifyUIDObservable.postValue(Resource.success(it))  }
                                    }else{
                                        response.body()?.errors?.let {  verifyUIDObservable.postValue(Resource.error(response.body()?.message + "\n" + it.joinToString { "\n" }, null)) }
                                    }
                                } else {
                                    response.body()?.errors?.let { verifyUIDObservable.postValue(Resource.error( response.body()?.message + "\n" + it.joinToString { "\n" }, null)) }
                                }
                            } else {
                                verifyUIDObservable.postValue(Resource.error( response.toString(), null))
                            }
                        } else {
                            verifyUIDObservable.postValue(Resource.error("Error Logging In", null))
                        }
                    }
                })
            }
        } else {
            verifyUIDObservable.postValue(Resource.error(context.getString(R.string.no_connection), null))
        }
    }

    fun verifyUSecret (verifyUSecret: VerifyUSecret) : LiveData<Resource<VerifyUSecretRes>> {
        if (NetworkUtils.isConnected(context)) {
            verifyUSecretObservable.postValue(Resource.loading(null))
            GlobalScope.launch(context = Dispatchers.Main) {
                val call = RequestService.getService("").verifyUSecret(verifyUSecret)
                call.enqueue(object : Callback<VerifyUSecretRes> {
                    override fun onFailure(call: Call<VerifyUSecretRes>?, t: Throwable?) {
                        verifyUSecretObservable.postValue(Resource.error(t.toString(), null))
                    }
                    override fun onResponse(call: Call<VerifyUSecretRes>?, response: Response<VerifyUSecretRes>?) {
                        if (response != null) {
                            if (response.isSuccessful) {
                                if (response.body()?.success !!) {
                                    if(response.body()?.status_code == 1){
                                        PrefrenceManager(context!!).setUserSession(response.body()?.data)
                                        //sessionDao.deleteSession()
                                        //sessionDao.insertSession(response.body()?.data)
                                        response.body()?.let { verifyUSecretObservable.postValue(Resource.success(it))  }
                                    }else{
                                        response.body()?.errors?.let {  verifyUSecretObservable.postValue(Resource.error(response.body()?.message + "\n" + it.joinToString { "\n" }, null)) }
                                    }
                                } else {
                                    response.body()?.errors?.let { verifyUSecretObservable.postValue(Resource.error( response.body()?.message + "\n" + it.joinToString { "\n" }, null)) }
                                }
                            } else {
                                verifyUSecretObservable.postValue(Resource.error( response.toString(), null))
                            }
                        } else {
                            verifyUSecretObservable.postValue(Resource.error("Error Logging In", null))
                        }
                    }
                })
            }
        } else {
            verifyUSecretObservable.postValue(Resource.error(context.getString(R.string.no_connection), null))
        }
        return verifyUSecretObservable
    }
}