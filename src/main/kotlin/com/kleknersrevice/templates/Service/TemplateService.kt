package com.kleknersrevice.templates.Service

import com.kleknersrevice.templates.Entity.Device
import com.kleknersrevice.templates.Entity.Film
import com.kleknersrevice.templates.Entity.Luminophore
import com.kleknersrevice.templates.Entity.Template
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
    fun getAllByAllParameters(device: Device, film: Film, luminophore: Luminophore): List<Template>
    fun findTemplate(template: Template): Optional<Template>
}