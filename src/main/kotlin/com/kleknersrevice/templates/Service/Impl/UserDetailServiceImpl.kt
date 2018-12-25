package com.kleknersrevice.templates.Service.Impl

import com.kleknersrevice.templates.Entity.UserDaoPrincipal
import com.kleknersrevice.templates.Repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service


@Service
class UserDetailServiceImpl(private val userRepository: UserRepository) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails? {
        val user = userRepository.findByUsername(username)
        return UserDaoPrincipal(user!!)
    }

}