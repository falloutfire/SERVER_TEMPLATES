package com.kleknersrevice.templates.Service.Impl

import com.kleknersrevice.templates.Entity.*
import com.kleknersrevice.templates.Repository.TemplateRepository
import com.kleknersrevice.templates.Service.TemplateService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional
class TemplateServiceImpl(private val templateRepository: TemplateRepository) : TemplateService {

    override fun findTemplate(template: Template): Optional<Template> {
        return templateRepository.findTemplate(
            template.device,
            template.film,
            template.luminophore,
            template.rounding,
            template.xcolor,
            template.ycolor,
            template.zcolor,
            template.xdelta,
            template.ydelta,
            template.zdelta,
            template.colorScheme
        )
    }

    override fun addTemplate(template: Template) {
        templateRepository.saveAndFlush(template)
    }

    override fun deleteTemplate(id: Long) {
        templateRepository.findById(id).let {
            if (it.isPresent) {
                templateRepository.deleteById(id)
            }
        }
    }

    override fun updateTemplate(template: Template) {
        templateRepository.saveAndFlush(template)
    }

    override fun allTemplate(): List<Template> {
        return templateRepository.findAll()
    }

    override fun getTemplateById(id: Long): Optional<Template> {
        return templateRepository.findById(id)
    }

    override fun getAllByDevice(device: Device): List<Template> {
        return templateRepository.getAllTemplateByDevice(device)
    }

    override fun getAllByLuminophore(luminophore: Luminophore): List<Template> {
        return templateRepository.getAllTemplateByLuminophore(luminophore)
    }

    override fun getAllByFilm(film: Film): List<Template> {
        return templateRepository.getAllTemplateByFilm(film)
    }

    override fun getAllByColorScheme(colorScheme: ColorScheme): List<Template> {
        return templateRepository.getAllTemplateByColorScheme(colorScheme)
    }

    override fun getAllByAllParameters(device: Device, film: Film, luminophore: Luminophore): List<Template> {
        return templateRepository.getAllTemplateByAllParameters(device, film, luminophore)
    }
}
