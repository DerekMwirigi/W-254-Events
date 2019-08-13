package com.example.wsupevents.models.tickets

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class TicketTypeModelCart(@SerializedName("count") @Expose var count: Int? = null, @SerializedName("clossesOn") @Expose var clossesOn: String? = null, @SerializedName(
    "price"
) @Expose var price: Double? = null, @SerializedName("id") @Expose var id: Int? = null, @SerializedName("label") @Expose var label: String? = null
)