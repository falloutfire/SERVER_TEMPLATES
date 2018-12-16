package com.kleknersrevice.templates.Repository

import com.kleknersrevice.templates.Entity.ChemicalType
import com.kleknersrevice.templates.Entity.Film
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

interface FilmRepository : JpaRepository<Film, Long> {

    fun getAllFilmByChemicalType(chemicalType: ChemicalType): List<Film>
    @Query("select f from Film f where f.chemicalType = :chemicalType and f.name = :name and f.color = :color")
    fun findFilm(@Param("name") name: String, @Param("color") color: String, @Param("chemicalType") chemicalType: ChemicalType): Optional<Film>
}