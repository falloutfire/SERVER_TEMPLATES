package com.kleknersrevice.templates.SecurityConfig

import TokenAuthenticationService
import com.fasterxml.jackson.databind.ObjectMapper
import com.kleknersrevice.templates.Entity.Role
import com.kleknersrevice.templates.Entity.User
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


class JWTLoginFilter(url: String, authManager: AuthenticationManager) :
    AbstractAuthenticationProcessingFilter(AntPathRequestMatcher(url)) {

    init {
        authenticationManager = authManager
    }

    @Throws(AuthenticationException::class, IOException::class, ServletException::class)
    override fun attemptAuthentication(
        req: HttpServletRequest, res: HttpServletResponse
    ): Authentication {
        val creds = ObjectMapper()
            .readValue(req.inputStream, User::class.java)
        println(creds.username + " " + creds.password)
        println(Role.ADMIN.authority)
        val back = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                creds.username,
                creds.password,
                hashSetOf(creds.authorities)
            )
        )
        return back
    }

    @Throws(IOException::class, ServletException::class)
    override fun successfulAuthentication(
        req: HttpServletRequest,
        res: HttpServletResponse, chain: FilterChain?,
        auth: Authentication
    ) {
        TokenAuthenticationService
            .addAuthentication(res, auth.name)
    }
}