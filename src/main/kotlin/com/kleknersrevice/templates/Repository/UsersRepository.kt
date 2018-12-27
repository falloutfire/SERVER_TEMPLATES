package com.kleknersrevice.templates.Repository

import com.kleknersrevice.templates.security.Model.JwtUser
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

interface UsersRepository : JpaRepository<JwtUser, Long> {

    @Query("select u from JwtUser u where u.username = :username")
    fun findByUserName(@Param("username") username: String): Optional<JwtUser>
}