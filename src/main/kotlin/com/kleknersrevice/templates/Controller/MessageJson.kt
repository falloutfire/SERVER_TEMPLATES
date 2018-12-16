package com.kleknersrevice.templates.Controller

import com.google.gson.JsonObject

fun messageJson(code: Int, message: String? = null): String {
    val jsonInfo = JsonObject()
    jsonInfo.addProperty("code", code)
    if (message != null) jsonInfo.addProperty("message", message)
    return jsonInfo.toString()
}