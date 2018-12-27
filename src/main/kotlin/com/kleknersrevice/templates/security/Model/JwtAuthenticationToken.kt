package com.kleknersrevice.templates.security.Model

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken

class JwtAuthenticationToken(var token: String) : UsernamePasswordAuthenticationToken(null, null) {

    override fun getCredentials(): Any? {
        return null
    }

    override fun getPrincipal(): Any? {
        return null
    }
}
