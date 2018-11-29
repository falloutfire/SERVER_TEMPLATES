package com.kleknersrevice.templates.Repository

import com.kleknersrevice.templates.Entity.*
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface TemplateRepository : JpaRepository<Template, Long> {

    fun getAllTemplateByDevice(/*@Param("os")*/ device: Device): List<Template>
    fun getAllTemplateByLuminophore(/*@Param("os")*/ luminophore: Luminophore): List<Template>
    fun getAllTemplateByFilm(/*@Param("os")*/ film: Film): List<Template>
    fun getAllTemplateByColorScheme(/*@Param("os")*/ colorScheme: ColorScheme): List<Template>
    @Query("select t from Template t where t.device = :device and t.film = :film and t.luminophore = :luminophore")
    fun getAllTemplateByAllParameters(@Param("device") device: Device,
                                      @Param("film") film: Film,
                                      @Param("luminophore") luminophore: Luminophore
    ): List<Template>
}