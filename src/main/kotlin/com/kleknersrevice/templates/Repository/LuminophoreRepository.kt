package com.kleknersrevice.templates.Repository

import com.kleknersrevice.templates.Entity.Luminophore
import org.springframework.data.jpa.repository.JpaRepository

interface LuminophoreRepository : JpaRepository<Luminophore, Long> {
}