package com.kleknersrevice.templates.security

import com.kleknersrevice.templates.security.Model.JwtAuthenticationToken
import com.kleknersrevice.templates.security.Model.JwtUserDetails
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component

@Component
class JwtAuthenticationProvider : AbstractUserDetailsAuthenticationProvider() {

    @Autowired
    private lateinit var validator: JwtValidator

    @Throws(AuthenticationException::class)
    override fun additionalAuthenticationChecks(
        userDetails: UserDetails,
        usernamePasswordAuthenticationToken: UsernamePasswordAuthenticationToken
    ) {

    }

    @Throws(AuthenticationException::class)
    override fun retrieveUser(
        username: String,
        usernamePasswordAuthenticationToken: UsernamePasswordAuthenticationToken
    ): UserDetails {

        val jwtAuthenticationToken = usernamePasswordAuthenticationToken as JwtAuthenticationToken
        val token = jwtAuthenticationToken.token

        val jwtUser = validator.validate(token) ?: throw RuntimeException("JWT Token is incorrect")

        val grantedAuthorities = AuthorityUtils
            .commaSeparatedStringToAuthorityList(jwtUser.role)
        println(jwtUser.role)
        return JwtUserDetails(jwtUser,
            token,
            grantedAuthorities
        )
    }

    override fun supports(aClass: Class<*>): Boolean {
        return JwtAuthenticationToken::class.java.isAssignableFrom(aClass)
    }
}
