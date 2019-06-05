package com.kleknersrevice.templates.Repository

import com.kleknersrevice.templates.Entity.SignatureDetails
import org.springframework.data.jpa.repository.JpaRepository

interface SignatureDetailsRepository : JpaRepository<SignatureDetails, Long> {
    fun findSignatureDetailsByFirstAngleAndSecondAngle(firstAngle: Double, secondAngle: Double): List<SignatureDetails>
}
