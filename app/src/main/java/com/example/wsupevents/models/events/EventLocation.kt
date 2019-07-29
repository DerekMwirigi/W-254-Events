package com.example.wsupevents.models.events

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class EventLocation {
    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("lat")
    @Expose
    var lat: String? = null

    @SerializedName("lng")
    @Expose
    var lng: String? = null
}