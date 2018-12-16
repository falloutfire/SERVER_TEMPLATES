package com.kleknersrevice.templates.Repository

import com.kleknersrevice.templates.Entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {
    fun findByUsername(username: String): User?
}