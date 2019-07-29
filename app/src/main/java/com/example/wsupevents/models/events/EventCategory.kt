package com.example.wsupevents.models.events

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class EventCategory {
    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("category")
    @Expose
    var category: String? = null

    @SerializedName("image")
    @Expose
    var image: String? = null
}