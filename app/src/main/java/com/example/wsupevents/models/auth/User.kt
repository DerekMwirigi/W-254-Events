package com.example.wsupevents.models.auth

class User(
    var id: Int?,
    var token: String?,
    var firebaseToken: String?,
    var firstName: String?,
    var lastName: String?,
    var email: String?,
    var mobile: Int?,
    var password: String?
)