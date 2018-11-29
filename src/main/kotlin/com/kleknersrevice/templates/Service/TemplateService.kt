package com.kleknersrevice.templates.Service

import com.kleknersrevice.templates.Entity.*
import java.util.*

interface TemplateService {
    fun addTemplate(template: Template)
    fun deleteTemplate(id: Long)
    fun updateTemplate(template: Template)
    fun allTemplate(): List<Template>
    fun getTemplateById(id: Long): Optional<Template>
    fun getAllByDevice(device: Device): List<Template>
    fun getAllByLuminophore(luminophore: Luminophore): List<Template>
    fun getAllByFilm(film: Film): List<Template>
    fun getAllByColorScheme(colorScheme: ColorScheme): List<Template>
    fun getAllByAllParameters(device: Device, film: Film, luminophore: Luminophore): List<Template>
}