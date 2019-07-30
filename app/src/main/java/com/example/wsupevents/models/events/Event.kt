package com.example.wsupevents.models.events

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Event {
    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("label")
    @Expose
    var label: String? = null

    @SerializedName("description")
    @Expose
    var description: String? = null

    @SerializedName("eventDate")
    @Expose
    var eventDate: String? = null

    @SerializedName("ticketPrice")
    @Expose
    var ticketPrice: Double? = null

    @SerializedName("locationName")
    @Expose
    var locationName: String? = null

    @SerializedName("locationLat")
    @Expose
    var locationLat: String? = null

    @SerializedName("locationLng")
    @Expose
    var locationLng: String? = null

    @SerializedName("image")
    @Expose
    var image: String? = null

    @SerializedName("plannerName")
    @Expose
    var plannerName: String? = null

    @SerializedName("plannerEmail")
    @Expose
    var plannerEmail: String? = null

    @SerializedName("plannerMobile")
    @Expose
    var plannerMobile: String? = null

    @SerializedName("noTickets")
    @Expose
    var noTickets: Int? = null

    @SerializedName("soldTickets")
    @Expose
    var soldTickets: Int? = null
}