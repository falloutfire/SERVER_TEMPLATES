package com.kleknersrevice.templates.Repository

import com.kleknersrevice.templates.Entity.*
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

interface TemplateRepository : JpaRepository<Template, Long> {

    fun getAllTemplateByDevice(device: Device): List<Template>
    fun getAllTemplateByLuminophore(luminophore: Luminophore): List<Template>
    fun getAllTemplateByFilm(film: Film): List<Template>
    fun getAllTemplateByColorScheme(colorScheme: ColorScheme): List<Template>
    @Query("select t from Template t where t.device = :device and t.film = :film and t.luminophore = :luminophore")
    fun getAllTemplateByAllParameters(@Param("device") device: Device,
                                      @Param("film") film: Film,
                                      @Param("luminophore") luminophore: Luminophore
    ): List<Template>

    @Query(
        "select t from Template t where t.device = :device and t.film = :film and t.luminophore = :luminophore " +
                "and t.rounding = :rounding and t.xColor = :xColor and t.yColor = :yColor and t.zColor = :zColor " +
                "and t.xDelta = :xDelta and t.yDelta = :yDelta and t.zDelta = :zDelta and t.colorScheme = :colorScheme"
    )
    fun findTemplate(
        @Param("device") device: Device,
        @Param("film") film: Film,
        @Param("luminophore") luminophore: Luminophore,
        @Param("rounding") rounding: Int,
        @Param("xColor") xColor: Int,
        @Param("yColor") yColor: Int,
        @Param("zColor") zColor: Int,
        @Param("xDelta") xDelta: Int,
        @Param("yDelta") yDelta: Int,
        @Param("zDelta") zDelta: Int,
        @Param("colorScheme") colorScheme: ColorScheme
    ): Optional<Template>
}