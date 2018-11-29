package com.kleknersrevice.templates.Repository

import com.kleknersrevice.templates.Entity.ChemicalType
import com.kleknersrevice.templates.Entity.Film
import org.springframework.data.jpa.repository.JpaRepository

interface FilmRepository : JpaRepository<Film, Long> {

    //@Query("select f from Film f where f.chemicalType = :chemicalType")
    fun getAllFilmByChemicalType(/*@Param("chemicalType")*/ chemicalType: ChemicalType): List<Film>
}