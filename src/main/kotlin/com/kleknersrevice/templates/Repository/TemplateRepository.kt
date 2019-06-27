package com.kleknersrevice.templates.Repository

import com.kleknersrevice.templates.Entity.*
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface TemplateRepository : JpaRepository<Template, Long> {

    fun getAllTemplateByDevice(device: Device): List<Template>
    fun getAllTemplateByLuminophore(luminophore: Luminophore): List<Template>
    fun getAllTemplateByFilm(film: Film): List<Template>
    fun getAllTemplateByColorScheme(colorScheme: ColorScheme): List<Template>
    fun findAllByDeviceAndFilmAndLuminophore(device: Device, film: Film, luminophore: Luminophore): List<Template>
    fun findTemplateByDeviceAndFilmAndLuminophoreAndColorSchemeAndNameAndCircularityAndLowerColorBoundAndPointsAndRadiusAndTriangles(
        device: Device,
        film: Film,
        luminophore: Luminophore,
        colorScheme: ColorScheme,
        name: String,
        circularity: Double,
        lowerColorBound: Int,
        points: Int,
        radius: Double,
        triangles: Int
    ): Optional<Template>
}
