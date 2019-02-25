package com.kleknersrevice.templates.Service.Impl

import com.kleknersrevice.templates.Service.AuthenticationFacadeService
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class AuthenticationFacadeServiceImpl : AuthenticationFacadeService {

    override fun getAuthentication(): Authentication = SecurityContextHolder.getContext().authentication
}