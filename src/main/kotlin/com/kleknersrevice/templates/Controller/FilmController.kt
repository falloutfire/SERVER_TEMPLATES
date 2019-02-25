package com.kleknersrevice.templates.Controller

import com.kleknersrevice.templates.Entity.ChemicalType
import com.kleknersrevice.templates.Entity.Film
import com.kleknersrevice.templates.Service.ChemicalTypeService
import com.kleknersrevice.templates.Service.FilmService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.annotation.Secured
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("bd_template/film/")
class FilmController(private val filmService: FilmService, private val chemicalTypeService: ChemicalTypeService) {

    //@Secured("ROLE_ADMIN")
    @PostMapping("")
    fun addFilm(@RequestBody film: Film): ResponseEntity<*> {
        chemicalTypeService.findChemicalType(film.chemicalType).let {
            if (it.isPresent) {
                filmService.findFilm(film).let { findFilm ->
                    return if (!findFilm.isPresent) {
                        filmService.addFilm(film)
                        ResponseEntity(messageJson(201, "Film $film created"), HttpStatus.CREATED)
                    } else {
                        ResponseEntity(messageJson(200, "Film $film already exist"), HttpStatus.OK)
                    }
                }
            } else {
                return ResponseEntity(messageJson(404, "Chemical Type $it not found"), HttpStatus.NOT_FOUND)
            }
        }
    }

    //@Secured("ROLE_ADMIN")
    @DeleteMapping("{id}")
    fun deleteFilm(@PathVariable(value = "id") id: Long): ResponseEntity<HttpStatus> {
        return filmService.getFilmById(id).let {
            if (it.isPresent) {
                filmService.deleteFilm(id)
                ResponseEntity(HttpStatus.FOUND)
            } else ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    //@Secured("ROLE_ADMIN")
    @PutMapping("")
    fun updateFilm(@RequestBody film: Film): ResponseEntity<*> {
        return filmService.getFilmById(film.id).let {
            if (it.isPresent) {
                filmService.updateFilm(film)
                ResponseEntity(messageJson(200, "Film $film updated"), HttpStatus.OK)
            } else {
                ResponseEntity(messageJson(404, "Film $film not found"), HttpStatus.NOT_FOUND)
            }
        }
    }

    //@Secured("ROLE_ADMIN")
    @GetMapping("")
    fun allFilm(): ResponseEntity<*> {
        return filmService.allFilm().let {
            if (!it.isEmpty()) ResponseEntity(it, HttpStatus.FOUND) else ResponseEntity(
                messageJson(404),
                HttpStatus.NOT_FOUND
            )
        }
    }

    //@Secured("ROLE_ADMIN")
    @GetMapping("{id}")
    fun getFilmById(@PathVariable(value = "id") id: Long): ResponseEntity<*> {
        return filmService.getFilmById(id).let {
            if (it.isPresent) ResponseEntity(it, HttpStatus.FOUND) else ResponseEntity(
                messageJson(
                    404,
                    "Film $it not found"
                ), HttpStatus.NOT_FOUND
            )
        }
    }

    //@Secured("ROLE_ADMIN")
    @GetMapping("find_by_chemical_type")
    fun getAllByChemicalType(@RequestBody chemicalType: ChemicalType): ResponseEntity<*> {
        return chemicalTypeService.getChemicalTypeById(chemicalType.id).let {
            if (it.isPresent) ResponseEntity(
                filmService.getAllByChemicalType(chemicalType),
                HttpStatus.OK
            ) else ResponseEntity(
                messageJson(
                    404,
                    "Chemical Type $it not found"
                ), HttpStatus.NOT_FOUND
            )
        }
    }

}