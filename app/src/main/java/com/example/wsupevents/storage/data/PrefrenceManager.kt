package com.example.wsupevents.storage.data

import android.content.Context
import android.content.SharedPreferences
import android.telecom.Call
import android.util.Log
import com.example.wsupevents.models.auth.User
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class PrefrenceManager(internal var _context: Context) {
    internal var pref: SharedPreferences
    internal var editor: SharedPreferences.Editor
    internal var PRIVATE_MODE = 0

    companion object {
        private val LOGIN_STATUS = "LOGIN_STATUS"
        private val PROFILE = "events_user_profile"
        private val FIREBASE_TOKEN = "firebasetoken"
        private val PREF_NAME = "events_prefrences"
    }

    init {
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        editor = pref.edit()
    }

    fun clearUser() {
        editor.clear()
        editor.commit()
    }

    fun setLoginStatus(status: Int){
        editor.putInt(LOGIN_STATUS,status)
        editor.commit()
    }

    fun setFirebaseToken(token: String?){
        editor.putString(FIREBASE_TOKEN, token)
        editor.commit()
    }

    fun getFirebaseToken() : String?{
        return pref.getString(FIREBASE_TOKEN, "")
    }

    fun setUserSession(user: User?){
        editor.putString(PROFILE, Gson().toJson(user))
        editor.commit()
    }

    fun getUserSession () : User?{
        val listType = object : TypeToken<User>() {}.type
        return Gson().fromJson<User>(pref.getString(PROFILE, ""), listType)
    }

    fun getLoginStatus(): Int {
        return pref.getInt(LOGIN_STATUS,0)
    }
}