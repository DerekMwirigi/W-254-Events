package com.example.wsupevents.models.auth

import androidx.room.Entity
import androidx.room.Index
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(
    indices = [(Index("id"))],
    primaryKeys = ["id"]
)
class User(
    @field:SerializedName("id")
    @Expose
    var id: Int?,

    @field:SerializedName("token")
    @Expose
    var token: String?,

    @field:SerializedName("firebaseToken")
    @Expose
    var firebaseToken: String?,

    @field:SerializedName("firstName")
    @Expose
    var firstName: String?,

    @field:SerializedName("lastName")
    @Expose
    var lastName: String?,

    @field:SerializedName("email")
    @Expose
    var email: String?,

    @field:SerializedName("mobile")
    @Expose
    var mobile: String?,

    @field:SerializedName("image")
    @Expose
    var image: String?,

    @field:SerializedName("password")
    @Expose
    var password: String?
)