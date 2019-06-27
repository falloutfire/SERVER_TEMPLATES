package com.kleknersrevice.templates.Repository

import com.kleknersrevice.templates.Entity.Device
import com.kleknersrevice.templates.Entity.Film
import com.kleknersrevice.templates.Entity.Luminophore
import com.kleknersrevice.templates.Entity.Template
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface TemplateRepository : JpaRepository<Template, Long> {

    fun getAllTemplateByDevice(device: Device): List<Template>
    fun getAllTemplateByLuminophore(luminophore: Luminophore): List<Template>
    fun getAllTemplateByFilm(film: Film): List<Template>
    fun findAllByDeviceAndFilmAndLuminophore(device: Device?, film: Film?, luminophore: Luminophore?): List<Template>
    fun findTemplateByDeviceAndFilmAndLuminophoreAndNameAndCircularityAndLowerColorBoundAndPointsAndRadiusAndTriangles(
        device: Device?,
        film: Film?,
        luminophore: Luminophore?,
        name: String?,
        circularity: Double?,
        lowerColorBound: Int?,
        points: Int?,
        radius: Double?,
        triangles: Int?
    ): Optional<Template>
}
