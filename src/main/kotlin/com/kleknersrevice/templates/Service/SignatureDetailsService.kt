package com.kleknersrevice.templates.Service

import com.kleknersrevice.templates.Entity.SignatureDetails

interface SignatureDetailsService {

    fun addSignatureDetails(signatureDetails: List<SignatureDetails>)
    fun findIdSignatureByAngles(signatureDetails: List<SignatureDetails>): Long?
}
