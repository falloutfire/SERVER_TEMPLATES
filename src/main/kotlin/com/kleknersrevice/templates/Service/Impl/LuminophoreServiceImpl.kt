package com.kleknersrevice.templates.Service.Impl

import com.kleknersrevice.templates.Entity.Luminophore
import com.kleknersrevice.templates.Repository.LuminophoreRepository
import com.kleknersrevice.templates.Service.LuminophoreService
import org.springframework.stereotype.Service
import java.util.*

@Service
class LuminophoreServiceImpl(private val luminophoreRepository: LuminophoreRepository) : LuminophoreService {

    override fun addLuminophore(luminophore: Luminophore) {
        luminophoreRepository.saveAndFlush(luminophore)
    }

    override fun deleteLuminophore(id: Long) {
        luminophoreRepository.findById(id).let {
            if (it.isPresent) {
                luminophoreRepository.deleteById(id)
            }
        }
    }

    override fun updateLuminophore(luminophore: Luminophore) {
        luminophoreRepository.saveAndFlush(luminophore)
    }

    override fun allLuminophore(): List<Luminophore> {
        return luminophoreRepository.findAll()
    }

    override fun getLuminophoreById(id: Long): Optional<Luminophore> {
        return luminophoreRepository.findById(id)
    }
}