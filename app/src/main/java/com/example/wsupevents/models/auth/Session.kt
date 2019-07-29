package com.example.wsupevents.models.auth

class Session {
    var id: Int? = null
    var date: String? = null
    var status: String? = null
    var userId: Int? = null
    var user: User? = null

    constructor(id: Int?, date: String?, status: String?, userId: Int?, user: User?) {
        this.id = id
        this.date = date
        this.status = status
        this.userId = userId
        this.user = user
    }
}