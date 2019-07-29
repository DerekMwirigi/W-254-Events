package com.example.wsupevents.models.tickets

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Ticket {
    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("code")
    @Expose
    var code: String? = null

    @SerializedName("buyerId")
    @Expose
    var buyerId: Int? = null

    @SerializedName("eventId")
    @Expose
    var eventId: Int? = null

    @SerializedName("noTickets")
    @Expose
    var noTickets: Int? = null

    @SerializedName("paymentStatus")
    @Expose
    var paymentStatus: String? = null

    @SerializedName("createdOn")
    @Expose
    var createdOn: String? = null

    @SerializedName("label")
    @Expose
    var label: String? = null

    @SerializedName("eventDate")
    @Expose
    var eventDate: String? = null

    @SerializedName("image")
    @Expose
    var image: String? = null
}