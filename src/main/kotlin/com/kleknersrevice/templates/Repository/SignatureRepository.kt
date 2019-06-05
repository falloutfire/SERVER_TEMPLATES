package com.kleknersrevice.templates.Repository

import com.kleknersrevice.templates.Entity.Signature
import com.kleknersrevice.templates.Entity.SignatureDetails
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface SignatureRepository : JpaRepository<Signature, Long> {

    fun findSignatureByListDetailsIn(listDetails: List<SignatureDetails>): Optional<Signature>
}
