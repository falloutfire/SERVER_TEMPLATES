package com.kleknersrevice.templates.Repository

import com.kleknersrevice.templates.Entity.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserRepository : JpaRepository<User, Long> {
    fun findByEmail(email: String): Optional<User>
    fun findByUserName(username: String): Optional<User>
}