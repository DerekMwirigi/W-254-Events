package com.example.wsupevents.models.tickets

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class TicketCart (@SerializedName("label") @Expose var label: String? = null, @SerializedName("eventId") @Expose var eventId: Int? = null, @SerializedName("ticketTypeModels") @Expose var ticketTypeModels: List<TicketTypeModel>? = null) : Serializable