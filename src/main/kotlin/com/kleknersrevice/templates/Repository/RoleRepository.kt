package com.kleknersrevice.templates.Repository

import com.kleknersrevice.templates.Entity.Role
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface RoleRepository : JpaRepository<Role, Long> {

    @Query(value = "SELECT * FROM Role where name IN (:roles)", nativeQuery = true)
    fun find(@Param("roles") roles: Iterable<String>): List<Role>
}