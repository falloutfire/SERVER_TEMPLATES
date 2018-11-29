package com.kleknersrevice.templates.Repository

import com.kleknersrevice.templates.Entity.ChemicalType
import org.springframework.data.jpa.repository.JpaRepository

interface ChemicalTypeRepository : JpaRepository<ChemicalType, Long> {
}