package com.kleknersrevice.templates.Repository

import com.kleknersrevice.templates.Entity.Role
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param

interface RoleRepository: CrudRepository<Role, Long> {

    @Query(value = "SELECT * FROM Roles where name IN (:roles)", nativeQuery = true)
    fun find(@Param("roles") roles:List<String>): Set<Role>
}