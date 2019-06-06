package com.kleknersrevice.templates.Service

import com.kleknersrevice.templates.Entity.Signature
import com.kleknersrevice.templates.Entity.SignatureDetails
import java.util.*

interface SignatureService {
    fun findSignatureBySignatureDetails(listSignature: List<SignatureDetails>): Optional<Signature>
    fun saveSignature(signature: Signature)
    fun deleteSignatureById(id: Long)
    fun findSignatureById(id: Long): Optional<Signature>
    fun findAllSignatures(): List<Signature>
}
