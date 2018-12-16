package com.kleknersrevice.templates.Service

import com.kleknersrevice.templates.Entity.Luminophore
import java.util.*

interface LuminophoreService {
    fun addLuminophore(luminophore: Luminophore)
    fun deleteLuminophore(id: Long)
    fun updateLuminophore(luminophore: Luminophore)
    fun allLuminophore(): List<Luminophore>
    fun getLuminophoreById(id: Long): Optional<Luminophore>
    fun findLuminophore(luminophore: Luminophore): Optional<Luminophore>
}