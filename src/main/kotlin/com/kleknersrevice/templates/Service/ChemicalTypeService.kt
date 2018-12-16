package com.kleknersrevice.templates.Service

import com.kleknersrevice.templates.Entity.ChemicalType
import java.util.*

interface ChemicalTypeService {
    fun addChemicalType(chemicalType: ChemicalType)
    fun deleteChemicalType(id: Long)
    fun updateChemicalType(chemicalType: ChemicalType)
    fun allChemicalType(): List<ChemicalType>
    fun getChemicalTypeById(id: Long): Optional<ChemicalType>
    fun findChemicalType(chemicalType: ChemicalType): Optional<ChemicalType>
}