package com.example.wsupevents.models.tickets

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class TicketTypeModel :Serializable {
    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("label")
    @Expose
    var label: String? = null

    @SerializedName("price")
    @Expose
    var price: Double? = null

    @SerializedName("count")
    @Expose
    var count: Int = 0

    @SerializedName("clossesOn")
    @Expose
    var clossesOn: String? = null

    @SerializedName("maxBuy")
    @Expose
    var maxBuy: Int? = null

    constructor(id: Int?, label: String?, price: Double?, count: Int, clossesOn: String?, maxBuy: Int?) {
        this.id = id
        this.label = label
        this.price = price
        this.count = count
        this.clossesOn = clossesOn
        this.maxBuy = maxBuy
    }
}