package com.kleknersrevice.templates.Controller

import org.springframework.http.HttpStatus

class ApiResponse(
    var status: HttpStatus,
    var message: String,
    var result: Any
)