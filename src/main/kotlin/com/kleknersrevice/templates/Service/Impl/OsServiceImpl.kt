package com.kleknersrevice.templates.Service.Impl

import com.kleknersrevice.templates.Entity.OS
import com.kleknersrevice.templates.Repository.OSRepository
import com.kleknersrevice.templates.Service.OsService
import org.springframework.stereotype.Service
import java.util.*

@Service
class OsServiceImpl(private val osRepository: OSRepository) : OsService {

    override fun getOs(os: OS): Optional<OS> {
        return osRepository.findOs(os.name, os.version)
    }

    override fun addOs(os: OS) {
        osRepository.saveAndFlush(os)
    }

    override fun deleteOs(id: Long) {
        osRepository.findById(id).let {
            if (it.isPresent) osRepository.deleteById(id)
        }
    }

    override fun updateOs(os: OS) {
        osRepository.saveAndFlush(os)
    }

    override fun allOs(): List<OS> {
        return osRepository.findAll()
    }

    override fun getOsById(id: Long): Optional<OS> {
        return osRepository.findById(id)
    }
}