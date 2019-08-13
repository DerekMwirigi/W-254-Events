package com.example.wsupevents.models.tickets

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class InitPayment (
    @SerializedName("ticketCart")
    @Expose var tickets: TicketCart? = null,

    @SerializedName("billingTypeId")
    @Expose var billingTypeId: Int? = null,

    @SerializedName("mpesaPhone")
    @Expose var mpesaPhone: String? = null,

    @SerializedName("amount")
    @Expose var amount: Double? = null
) : Serializable