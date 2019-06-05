package com.kleknersrevice.templates.Controller

import org.springframework.http.HttpStatus

class ApiResponse() {

    lateinit var status: HttpStatus
    var result: Any? = null

    constructor(
        status: HttpStatus
    ) : this() {
        this.status = status
    }

    constructor(
        status: HttpStatus,
        result: Any
    ) : this(status) {
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

