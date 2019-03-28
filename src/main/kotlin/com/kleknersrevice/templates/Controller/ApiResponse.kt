package com.kleknersrevice.templates.Controller

import org.springframework.http.HttpStatus

class ApiResponse(
    /* var status: HttpStatus,
     var message: String*/
) {

    lateinit var status: HttpStatus
    lateinit var message: String
    var result: Any? = null

    constructor(
        status: HttpStatus,
        message: String
    ) : this() {
        this.status = status
        this.message = message
    }

    constructor(
        status: HttpStatus,
        message: String, result: Any
    ) : this(status, message) {
        this.result = result
    }

}

class ResponseValues {

    companion object {
        const val SUCCESS = "SUCCESS"
        const val ERROR = "ERROR"
        const val CREATED = "CREATED"
        const val EXIST = "EXIST"
        const val UPDATED = "UPDATED"
        const val DELETED = "DELETED"
        const val NOT_FOUND = "NOT FOUND"
        const val ROLE_ADMIN = "ROLE_ADMIN"
        const val ROLE_USER = "ROLE_USER"
    }
}

