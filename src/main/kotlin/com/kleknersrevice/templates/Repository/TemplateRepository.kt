package com.kleknersrevice.templates.Repository

import com.kleknersrevice.templates.Entity.*
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface TemplateRepository : JpaRepository<Template, Long> {

    fun getAllTemplateByDevice(device: Device): List<Template>
    fun getAllTemplateByLuminophore(luminophore: Luminophore): List<Template>
    fun getAllTemplateByFilm(film: Film): List<Template>
    fun getAllTemplateByColorScheme(colorScheme: ColorScheme): List<Template>
    /*@Query("select t from Template t where t.device = :device and t.film = :film and t.luminophore = :luminophore")
    fun getAllTemplateByAllParameters(
        @Param("device") device: Device,
        @Param("film") film: Film,
        @Param("luminophore") luminophore: Luminophore
    ): List<Template>*/

    fun findAllByDeviceAndFilmAndLuminophore(device: Device, film: Film, luminophore: Luminophore): List<Template>

    /*@Query(
        "select t from Template t where t.device = :device and t.film = :film and t.luminophore = :luminophore " +
                "and t.rounding = :rounding and t.xcolor = :xcolor and t.ycolor = :ycolor and t.zcolor = :zcolor " +
                "and t.xdelta = :xdelta and t.ydelta = :ydelta and t.zdelta = :zdelta and t.colorScheme = :colorScheme"
    )
    fun findTemplate(
        @Param("device") device: Device,
        @Param("film") film: Film,
        @Param("luminophore") luminophore: Luminophore,
        @Param("rounding") rounding: String,
        @Param("xcolor") xcolor: String,
        @Param("ycolor") ycolor: String,
        @Param("zcolor") zcolor: String,
        @Param("xdelta") xdelta: String,
        @Param("ydelta") ydelta: String,
        @Param("zdelta") zdelta: String,
        @Param("colorScheme") colorScheme: ColorScheme
    ): Optional<Template>*/

    fun findTemplateByDeviceAndFilmAndLuminophoreAndColorSchemeAndColorAndName(
        device: Device,
        film: Film,
        luminophore: Luminophore,
        colorScheme: ColorScheme,
        color: String,
        name: String
    ): Optional<Template>
}
