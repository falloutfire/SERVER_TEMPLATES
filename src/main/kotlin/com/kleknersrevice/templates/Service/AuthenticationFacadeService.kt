package com.kleknersrevice.templates.Service

import org.springframework.security.core.Authentication

interface AuthenticationFacadeService {

    fun getAuthentication(): Authentication
}