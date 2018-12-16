package com.kleknersrevice.templates.Service.Impl

import com.kleknersrevice.templates.Entity.ChemicalType
import com.kleknersrevice.templates.Entity.Film
import com.kleknersrevice.templates.Repository.FilmRepository
import com.kleknersrevice.templates.Service.FilmService
import org.springframework.stereotype.Service
import java.util.*

@Service
class FilmServiceImpl(private val filmRepository: FilmRepository) : FilmService {

    override fun findFilm(film: Film): Optional<Film> {
        return filmRepository.findFilm(film.name, film.color, film.chemicalType)
    }

    override fun addFilm(film: Film) {
        filmRepository.saveAndFlush(film)
    }

    override fun deleteFilm(id: Long) {
        filmRepository.findById(id).let {
            if (it.isPresent) {
                filmRepository.deleteById(id)
            }
        }
    }

    override fun updateFilm(film: Film) {
        filmRepository.saveAndFlush(film)
    }

    override fun allFilm(): List<Film> {
        return filmRepository.findAll()
    }

    override fun getFilmById(id: Long): Optional<Film> {
        return filmRepository.findById(id)
    }

    override fun getAllByChemicalType(chemicalType: ChemicalType): List<Film> {
        return filmRepository.getAllFilmByChemicalType(chemicalType)
    }
}