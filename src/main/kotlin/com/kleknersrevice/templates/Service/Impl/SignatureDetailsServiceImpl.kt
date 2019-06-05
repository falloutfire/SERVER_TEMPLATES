package com.kleknersrevice.templates.Service.Impl

import com.kleknersrevice.templates.Entity.SignatureDetails
import com.kleknersrevice.templates.Repository.SignatureDetailsRepository
import com.kleknersrevice.templates.Service.SignatureDetailsService
import org.springframework.stereotype.Service

@Service
class SignatureDetailsServiceImpl(private val signatureDetailsRepository: SignatureDetailsRepository) :
    SignatureDetailsService {

    override fun findIdSignatureByAngles(signatureDetails: List<SignatureDetails>): Long? {
        val list: List<List<SignatureDetails>> = signatureDetails.map {
            signatureDetailsRepository.findSignatureDetailsByFirstAngleAndSecondAngle(
                it.firstAngle,
                it.secondAngle
            )
        }

        for (i in 0 until list[0].size) {
            var check = true
            for (a in 1 until list.size) {
                if (!findIdInList(list[a], list[0][i].signature!!.id)) {
                    check = false
                    break
                }
            }
            if (check) {
                return list[0][i].signature!!.id
            }

        }
        return null
    }

    fun findIdInList(list: List<SignatureDetails>, id: Long): Boolean {
        for (i in 0..list.size) {
            if (list[i].signature!!.id == id) {
                return true
            }
        }
        return false
    }

    override fun addSignatureDetails(signatureDetails: List<SignatureDetails>) {
        signatureDetails.forEach {
            signatureDetailsRepository.save(it)
        }
    }
}
