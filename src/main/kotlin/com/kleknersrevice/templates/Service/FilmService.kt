package com.kleknersrevice.templates.Service

import com.kleknersrevice.templates.Entity.ChemicalType
import com.kleknersrevice.templates.Entity.Film
import java.util.*

interface FilmService {
    fun addFilm(film: Film)
    fun deleteFilm(id: Long)
    fun updateFilm(film: Film)
    fun allFilm(): List<Film>
    fun getFilmById(id: Long): Optional<Film>
    fun getAllByChemicalType(chemicalType: ChemicalType): List<Film>
    fun findFilm(film: Film): Optional<Film>
}