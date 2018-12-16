package com.kleknersrevice.templates.Repository

import com.kleknersrevice.templates.Entity.Luminophore
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

interface LuminophoreRepository : JpaRepository<Luminophore, Long> {

    @Query("select l from Luminophore l where l.name = :name and l.color = :color and l.size = :size")
    fun findLuminophore(@Param("name") name: String, @Param("color") color: String, @Param("size") size: Double): Optional<Luminophore>
}