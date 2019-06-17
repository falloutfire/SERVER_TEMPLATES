package com.kleknersrevice.templates.Controller

import com.kleknersrevice.templates.Controller.ResponseValues.Companion.CREATED
import com.kleknersrevice.templates.Controller.ResponseValues.Companion.DELETED
import com.kleknersrevice.templates.Controller.ResponseValues.Companion.EXIST
import com.kleknersrevice.templates.Controller.ResponseValues.Companion.NOT_FOUND
import com.kleknersrevice.templates.Controller.ResponseValues.Companion.ROLE_ADMIN
import com.kleknersrevice.templates.Controller.ResponseValues.Companion.ROLE_USER
import com.kleknersrevice.templates.Controller.ResponseValues.Companion.UPDATED
import com.kleknersrevice.templates.Entity.ChemicalType
import com.kleknersrevice.templates.Entity.Film
import com.kleknersrevice.templates.Service.AuthenticationFacadeService
import com.kleknersrevice.templates.Service.ChemicalTypeService
import com.kleknersrevice.templates.Service.FilmService
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.security.access.annotation.Secured
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("bd_template/film")
class FilmController(
    private val filmService: FilmService,
    private val chemicalTypeService: ChemicalTypeService,
    private val authenticationFacadeService: AuthenticationFacadeService
) {

    private val log = LoggerFactory.getLogger(FilmController::class.java)

    @Secured(ROLE_ADMIN)
    @PostMapping("")
    fun addFilm(@RequestBody film: Film): ApiResponse {
        log.info(
            String.format(
                "received request to save Film %s",
                authenticationFacadeService.getAuthentication().principal
            )
        )
        chemicalTypeService.findChemicalType(film.chemicalType).run {
            if (isPresent) {
                filmService.findFilm(film).let { findFilm ->
                    return if (!findFilm.isPresent) {
                        filmService.addFilm(film)
                        ApiResponse(HttpStatus.CREATED, "Film $CREATED")
                    } else {
                        ApiResponse(HttpStatus.CREATED, "Film $EXIST")
                    }
                }
            } else {
                return ApiResponse(HttpStatus.NOT_FOUND, "Film $NOT_FOUND")
            }
        }
    }

    @Secured(ROLE_ADMIN)
    @DeleteMapping("/{id}")
    fun deleteFilm(@PathVariable(value = "id") id: Long): ApiResponse {
        log.info(
            String.format(
                "received request to delete Film %s",
                authenticationFacadeService.getAuthentication().principal
            )
        )
        return filmService.getFilmById(id).let {
            if (it.isPresent) {
                filmService.deleteFilm(id)
                ApiResponse(HttpStatus.OK, "Film $DELETED")
            } else ApiResponse(HttpStatus.NOT_FOUND, "Film $NOT_FOUND")
        }
    }

    @Secured(ROLE_ADMIN)
    @PutMapping("")
    fun updateFilm(@RequestBody film: Film): ApiResponse {
        log.info(
            String.format(
                "received request to update Film %s",
                authenticationFacadeService.getAuthentication().principal
            )
        )
        return filmService.getFilmById(film.id).let {
            if (it.isPresent) {
                filmService.updateFilm(film)
                ApiResponse(HttpStatus.OK, "Film $UPDATED")
            } else {
                ApiResponse(HttpStatus.NOT_FOUND, "Film $NOT_FOUND")
            }
        }
    }

    @Secured(ROLE_ADMIN, ROLE_USER)
    @GetMapping("")
    fun allFilm(): ApiResponse {
        log.info(
            String.format(
                "received request to list Film %s",
                authenticationFacadeService.getAuthentication().principal
            )
        )
        return filmService.allFilm().run {
            ApiResponse(HttpStatus.OK, this)
        }
    }

    @Secured(ROLE_ADMIN, ROLE_USER)
    @GetMapping("/{id}")
    fun getFilmById(@PathVariable(value = "id") id: Long): ApiResponse {
        log.info(
            String.format(
                "received request to get by id Film %s",
                authenticationFacadeService.getAuthentication().principal
            )
        )
        return filmService.getFilmById(id).run {
            if (isPresent) ApiResponse(HttpStatus.OK, this) else ApiResponse(
                HttpStatus.NOT_FOUND,
                "Film $NOT_FOUND"
            )
        }
    }

    @Secured(ROLE_ADMIN, ROLE_USER)
    @GetMapping("/find_by_chemical_type")
    fun getAllByChemicalType(@RequestBody chemicalType: ChemicalType): ApiResponse {
        log.info(
            String.format(
                "received request to get all by Chemical Type %s",
                authenticationFacadeService.getAuthentication().principal
            )
        )
        return chemicalTypeService.getChemicalTypeById(chemicalType.id).run {
            if (isPresent) ApiResponse(HttpStatus.OK, this)
            else ApiResponse(
                HttpStatus.NOT_FOUND,
                "Film $NOT_FOUND"
            )
        }
    }

}
