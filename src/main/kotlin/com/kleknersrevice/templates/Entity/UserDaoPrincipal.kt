package com.kleknersrevice.templates.Entity

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.*

class UserDaoPrincipal(var user: User) : UserDetails {
    override fun getAuthorities(): List<GrantedAuthority> {
        println(user.authorities.authority)
        return Collections.singletonList(SimpleGrantedAuthority(user.authorities.authority))
    }

    override fun isEnabled(): Boolean {
        return user.enabled
    }

    override fun getUsername(): String {
        return user.username
    }

    override fun isCredentialsNonExpired(): Boolean {
        return user.credentialsNonExpired
    }

    override fun getPassword(): String {
        return user.password
    }

    override fun isAccountNonExpired(): Boolean {
        return user.accountNonExpired
    }

    override fun isAccountNonLocked(): Boolean {
        return user.accountNonLocked
    }
}