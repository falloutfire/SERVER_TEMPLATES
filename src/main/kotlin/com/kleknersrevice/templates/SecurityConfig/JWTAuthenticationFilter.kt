package com.kleknersrevice.templates.SecurityConfig

import TokenAuthenticationService
import com.kleknersrevice.templates.Service.Impl.UserDetailServiceImpl
import io.jsonwebtoken.io.IOException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.GenericFilterBean
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest


class JWTAuthenticationFilter(private val userDetailServiceImpl: UserDetailServiceImpl) : GenericFilterBean() {

    @Throws(IOException::class, ServletException::class)
    override fun doFilter(
        request: ServletRequest,
        response: ServletResponse,
        filterChain: FilterChain
    ) {
        val authentication = TokenAuthenticationService
            .getAuthentication(request as HttpServletRequest, userDetailServiceImpl)

        SecurityContextHolder.getContext().authentication = authentication
        filterChain.doFilter(request, response)
    }
}
