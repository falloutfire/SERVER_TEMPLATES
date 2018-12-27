package com.kleknersrevice.templates.security.Model

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class JwtUserDetails(
    var user: JwtUser, var token: String,
    grantedAuthorities: List<GrantedAuthority>
) : UserDetails {
    private val authorities: Collection<GrantedAuthority>

    init {
        this.authorities = grantedAuthorities
    }

    override fun getAuthorities(): Collection<GrantedAuthority> {
        return authorities
    }

    override fun getPassword(): String? {
        return user.password /*null*/
    }

    override fun getUsername(): String {
        return /*userName*/ user.username
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }

}
