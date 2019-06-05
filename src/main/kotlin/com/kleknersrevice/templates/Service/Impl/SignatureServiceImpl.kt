package com.kleknersrevice.templates.Service.Impl

import com.kleknersrevice.templates.Entity.Signature
import com.kleknersrevice.templates.Entity.SignatureDetails
import com.kleknersrevice.templates.Repository.SignatureRepository
import com.kleknersrevice.templates.Service.SignatureDetailsService
import com.kleknersrevice.templates.Service.SignatureService
import org.springframework.stereotype.Service
import java.util.*

@Service
class SignatureServiceImpl(
    private val signatureRepository: SignatureRepository,
    private val signatureDetailsService: SignatureDetailsService
) : SignatureService {

    override fun findSignatureById(id: Long): Optional<Signature> {
        return signatureRepository.findById(id)
    }

    override fun findSignatureBySignatureDetails(listSignature: List<SignatureDetails>): Optional<Signature> {
        signatureDetailsService.findIdSignatureByAngles(listSignature)?.let {
            val sign = signatureRepository.findById(it).get()
            return if (sign.listDetails.size == listSignature.size) {
                Optional.of(sign)
            } else {
                Optional.empty()
            }
        }
        return Optional.empty()
    }

    override fun saveSignature(signature: Signature) {
        val sign = signatureRepository.save(signature)
        signature.listDetails.forEach { it.signature = sign }
        signatureDetailsService.addSignatureDetails(signature.listDetails)
    }

    override fun deleteSignatureById(id: Long) {
        signatureRepository.deleteById(id)
    }
}
