package com.kleknersrevice.templates.Repository

import com.kleknersrevice.templates.Entity.OS
import org.springframework.data.jpa.repository.JpaRepository

interface OSRepository : JpaRepository<OS, Long> {
}