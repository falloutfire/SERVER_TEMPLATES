package com.kleknersrevice.templates.Repository

import com.kleknersrevice.templates.Entity.Users
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserRepository : JpaRepository<Users, Long> {
    fun findByUsername(username: String): Optional<Users>
    fun findByEmail(email: String): Optional<Users>
}