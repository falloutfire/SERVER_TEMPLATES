package com.kleknersrevice.templates.Repository

import com.kleknersrevice.templates.Entity.ChemicalType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

interface ChemicalTypeRepository : JpaRepository<ChemicalType, Long> {

    @Query("select c from ChemicalType c where c.type = :type")
    fun findChemicalType(@Param("type") type: String): Optional<ChemicalType>
}