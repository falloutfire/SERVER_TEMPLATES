package com.kleknersrevice.templates.Repository

import com.kleknersrevice.templates.Entity.Role
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface RoleRepository : JpaRepository<Role, Long> {
    fun findRoleByRoleName(roleName: String): Optional<Role>
    fun findRoleById(id: Long): Optional<Role>
}