package com.kleknersrevice.templates.Repository

import com.kleknersrevice.templates.Entity.OS
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

interface OSRepository : JpaRepository<OS, Long> {

    @Query("select os from OS os where os.name = :name and os.version = :version")
    fun findOs(@Param("name") name: String, @Param("version") version: Double): Optional<OS>
}