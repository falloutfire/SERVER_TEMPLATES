package com.kleknersrevice.templates.Service.Impl

import com.kleknersrevice.templates.Entity.ChemicalType
import com.kleknersrevice.templates.Repository.ChemicalTypeRepository
import com.kleknersrevice.templates.Service.ChemicalTypeService
import org.springframework.stereotype.Service
import java.util.*

@Service
class ChemicalTypeServiceImpl(private val chemicalTypeRepository: ChemicalTypeRepository) : ChemicalTypeService {

    override fun addChemicalType(chemicalType: ChemicalType) {
        chemicalTypeRepository.saveAndFlush(chemicalType)
    }

    override fun deleteChemicalType(id: Long) {
        chemicalTypeRepository.findById(id).let {
            if (it.isPresent) {
                chemicalTypeRepository.deleteById(id)
            }
        }
    }

    override fun updateChemicalType(chemicalType: ChemicalType) {
        chemicalTypeRepository.saveAndFlush(chemicalType)
    }

    override fun allChemicalType(): List<ChemicalType> {
        return chemicalTypeRepository.findAll()
    }

    override fun getChemicalTypeById(id: Long): Optional<ChemicalType> {
        return chemicalTypeRepository.findById(id)
    }
}