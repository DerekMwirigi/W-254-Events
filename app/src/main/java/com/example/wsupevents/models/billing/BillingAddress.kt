package com.example.wsupevents.models.billing

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class BillingAddress : Serializable {
    @SerializedName("typeId")
    @Expose
    var typeId: Int? = null

    @SerializedName("accIcon")
    @Expose
    var accIcon: String? = null

    @SerializedName("accName")
    @Expose
    var accName: String? = null

    @SerializedName("accNo")
    @Expose
    var accNo: String? = null

    constructor(typeId: Int?, accName: String?, accNo: String?, accIcon: String?) {
        this.typeId = typeId
        this.accIcon = accIcon
        this.accName = accName
        this.accNo = accNo
    }
}