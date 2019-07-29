package com.example.wsupevents.models.auth

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class VerifyPasswordRes {
    @SerializedName("success")
    @Expose
    var success: Boolean? = null

    @SerializedName("status_code")
    @Expose
    var status_code: Int? = null

    @SerializedName("message")
    @Expose
    var message: String? = null

    @SerializedName("errors")
    @Expose
    var errors: Array<String>? = null

    @SerializedName("data")
    @Expose
    var data: User? = null
}