package com.kleknersrevice.templates.Repository

import com.kleknersrevice.templates.Entity.ChemicalType
import com.kleknersrevice.templates.Entity.Film
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface FilmRepository : JpaRepository<Film, Long> {


    fun findFilmByChemicalTypeAndLightPermeabilityAndThicknessAndName(
        chemicalType: ChemicalType,
        lightPermeability: Int,
        thickness: Double,
        name: String
    ): Optional<Film>
    fun getAllFilmByChemicalType(chemicalType: ChemicalType): List<Film>
    /*@Query("select f from Film f where f.chemicalType = :chemicalType and f.name = :name and f.color = :color and f.thickness = :thickness and f.lightPermeability = :lightPermeability")
    fun findFilm(@Param("name") name: String, @Param("color") color: String, @Param("chemicalType") chemicalType: ChemicalType): Optional<Film>
*/
}
