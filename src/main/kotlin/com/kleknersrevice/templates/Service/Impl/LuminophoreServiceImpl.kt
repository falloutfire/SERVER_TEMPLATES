package com.kleknersrevice.templates.Service.Impl

import com.kleknersrevice.templates.Entity.Luminophore
import com.kleknersrevice.templates.Repository.LuminophoreRepository
import com.kleknersrevice.templates.Service.LuminophoreService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional
class LuminophoreServiceImpl(private val luminophoreRepository: LuminophoreRepository) : LuminophoreService {

    override fun findLuminophore(luminophore: Luminophore): Optional<Luminophore> {
        return luminophoreRepository.findLuminophore(luminophore.name, luminophore.color, luminophore.size)
    }

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