package com.kleknersrevice.templates.Controller

import com.kleknersrevice.templates.Entity.ChemicalType
import com.kleknersrevice.templates.Entity.Film
import com.kleknersrevice.templates.Service.ChemicalTypeService
import com.kleknersrevice.templates.Service.FilmService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/film/")
class FilmController(private val filmService: FilmService, private val chemicalTypeService: ChemicalTypeService) {

    @PostMapping("")
    fun addFilm(@RequestBody film: Film): ResponseEntity<HttpStatus> {
        filmService.addFilm(film)
        return ResponseEntity(HttpStatus.CREATED)
    }

    @DeleteMapping("{id}")
    fun deleteFilm(@PathVariable(value = "id") id: Long): ResponseEntity<HttpStatus> {
        return filmService.getFilmById(id).let {
            if (it.isPresent) {
                filmService.deleteFilm(id)
                ResponseEntity(HttpStatus.FOUND)
            } else ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @PutMapping("")
    fun updateFilm(@RequestBody film: Film): ResponseEntity<HttpStatus> {
        filmService.updateFilm(film)
        return ResponseEntity(HttpStatus.OK)
    }

    @GetMapping("")
    fun allFilm(): ResponseEntity<List<Film>> {
        return filmService.allFilm().let {
            if (!it.isEmpty()) ResponseEntity(it, HttpStatus.FOUND) else ResponseEntity(it, HttpStatus.NOT_FOUND)
        }
    }

    @GetMapping("{id}")
    fun getFilmById(@PathVariable(value = "id") id: Long): ResponseEntity<*> {
        return filmService.getFilmById(id).let {
            if (it.isPresent) ResponseEntity(it, HttpStatus.FOUND) else ResponseEntity(it, HttpStatus.NOT_FOUND)
        }
    }

    @GetMapping("find_by_chemical_type")
    fun getAllByChemicalType(@RequestBody chemicalType: ChemicalType): ResponseEntity<List<Film>> {
        return chemicalTypeService.getChemicalTypeById(chemicalType.id).let {
            if (it.isPresent) ResponseEntity(
                filmService.getAllByChemicalType(chemicalType),
                HttpStatus.OK
            ) else ResponseEntity(null, HttpStatus.NOT_FOUND)
        }
    }

}