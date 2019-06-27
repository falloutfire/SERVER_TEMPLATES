package com.kleknersrevice.templates.Controller

import com.kleknersrevice.templates.Controller.ResponseValues.Companion.CREATED
import com.kleknersrevice.templates.Controller.ResponseValues.Companion.EXIST
import com.kleknersrevice.templates.Controller.ResponseValues.Companion.NOT_FOUND
import com.kleknersrevice.templates.Controller.ResponseValues.Companion.ROLE_ADMIN
import com.kleknersrevice.templates.Controller.ResponseValues.Companion.ROLE_USER
import com.kleknersrevice.templates.Controller.ResponseValues.Companion.UPDATED
import com.kleknersrevice.templates.Entity.*
import com.kleknersrevice.templates.Service.*
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.security.access.annotation.Secured
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("bd_template/template")
class TemplateController(
    private val templateService: TemplateService,
    private val deviceService: DeviceService,
    private val filmService: FilmService,
    private val luminophoreService: LuminophoreService,
    private val authenticationFacadeService: AuthenticationFacadeService
) {

    private val log = LoggerFactory.getLogger(TemplateController::class.java)

    @Secured(ROLE_ADMIN)
    @PostMapping("")
    fun addTemplate(@RequestBody template: Template): ApiResponse {
        log.info(
            String.format(
                "received request to save Template %s",
                authenticationFacadeService.getAuthentication().principal
            )
        )

        /*deviceService.findDevice(template.device).run {
            if (!isPresent) return ApiResponse(HttpStatus.NOT_FOUND, "Device $NOT_FOUND")
        }
        filmService.findFilm(template.film)
            .run {
                if (!isPresent) return ApiResponse(HttpStatus.NOT_FOUND, "Film $NOT_FOUND")
            }
        luminophoreService.findLuminophore(template.luminophore)
            .run {
                if (!isPresent) return ApiResponse(HttpStatus.NOT_FOUND, "Luminophore $NOT_FOUND")
            }*/
        templateService.findTemplate(template).run {
            return if (!isPresent) {
                templateService.addTemplate(template)
                ApiResponse(HttpStatus.OK, "Template $CREATED")
            } else {
                ApiResponse(HttpStatus.OK, "Template $EXIST")
            }
        }
    }

    @Secured(ROLE_ADMIN)
    @DeleteMapping("/{id}")
    fun deleteTemplate(@PathVariable(value = "id") id: Long): ApiResponse {
        log.info(
            String.format(
                "received request to delete Template %s",
                authenticationFacadeService.getAuthentication().principal
            )
        )
        return templateService.getTemplateById(id).run {
            if (isPresent) {
                templateService.deleteTemplate(id)
                ApiResponse(HttpStatus.OK, "Template ${ResponseValues.DELETED}")
            } else ApiResponse(HttpStatus.NOT_FOUND, "Template $NOT_FOUND")
        }
    }

    @Secured(ROLE_ADMIN)
    @PutMapping("")
    fun updateTemplate(@RequestBody template: Template): ApiResponse {
        log.info(
            String.format(
                "received request to update Template %s",
                authenticationFacadeService.getAuthentication().principal
            )
        )
        return templateService.getTemplateById(template.id)
            .run {
                if (isPresent) {
                    templateService.updateTemplate(template)
                    ApiResponse(HttpStatus.OK, "Template $UPDATED")
                } else {
                    ApiResponse(HttpStatus.NOT_FOUND, "Template $NOT_FOUND")
                }
            }
    }

    @Secured(ROLE_ADMIN, ROLE_USER)
    @GetMapping("")
    fun allTemplate(): ApiResponse {
        log.info(
            String.format(
                "received request to get all Template %s",
                authenticationFacadeService.getAuthentication().principal
            )
        )
        return templateService.allTemplate().run {
            ApiResponse(HttpStatus.OK, this)
        }
    }

    @Secured(ROLE_ADMIN, ROLE_USER)
    @GetMapping("/{id}")
    fun getTemplateById(@PathVariable(value = "id") id: Long): ApiResponse {
        log.info(
            String.format(
                "received request to get by id Template %s",
                authenticationFacadeService.getAuthentication().principal
            )
        )
        return templateService.getTemplateById(id).run {
            if (isPresent) ApiResponse(HttpStatus.OK, this) else
                ApiResponse(HttpStatus.NOT_FOUND, "Template $NOT_FOUND")
        }
    }

    @Secured(ROLE_ADMIN, ROLE_USER)
    @GetMapping("/find_by_device")
    fun getAllByDevice(@RequestBody device: Device): ApiResponse {
        log.info(
            String.format(
                "received request to get by device Template %s",
                authenticationFacadeService.getAuthentication().principal
            )
        )
        return deviceService.getDeviceById(device.id).run {
            if (isPresent)
                ApiResponse(HttpStatus.OK, this)
            else ApiResponse(HttpStatus.NOT_FOUND, "Template $NOT_FOUND")
        }
    }

    @Secured(ROLE_ADMIN, ROLE_USER)
    @GetMapping("/find_by_luminophore")
    fun getAllByLuminophore(@RequestBody luminophore: Luminophore): ApiResponse {
        log.info(
            String.format(
                "received request to get by Luminophore Template %s",
                authenticationFacadeService.getAuthentication().principal
            )
        )
        return luminophoreService.getLuminophoreById(luminophore.id).run {
            if (isPresent) ApiResponse(HttpStatus.OK, this)
            else ApiResponse(HttpStatus.NOT_FOUND, "Template $NOT_FOUND")
        }
    }

    @Secured(ROLE_ADMIN, ROLE_USER)
    @GetMapping("/find_by_film")
    fun getAllByFilm(@RequestBody film: Film): ApiResponse {
        log.info(
            String.format(
                "received request to get by Film Template %s",
                authenticationFacadeService.getAuthentication().principal
            )
        )
        return filmService.getFilmById(film.id).run {
            if (isPresent) ApiResponse(HttpStatus.OK, this)
            else ApiResponse(HttpStatus.NOT_FOUND, "Template $NOT_FOUND")
        }
    }

    @Secured(ROLE_ADMIN, ROLE_USER)
    @GetMapping("/find_by_all")
    fun getAllByAllParameters(@RequestBody templateContext: TemplateContext): ApiResponse {
        log.info(
            String.format(
                "received request to get by TemplateContext Template %s",
                authenticationFacadeService.getAuthentication().principal
            )
        )
        deviceService.findDevice(templateContext.device).run {
            if (!isPresent) return ApiResponse(HttpStatus.NOT_FOUND, "Device $NOT_FOUND")
        }
        filmService.findFilm(templateContext.film)
            .run {
                if (!isPresent) return ApiResponse(HttpStatus.NOT_FOUND, "Film $NOT_FOUND")
            }
        luminophoreService.findLuminophore(templateContext.luminophore)
            .run {
                if (!isPresent) return ApiResponse(HttpStatus.NOT_FOUND, "Luminophore $NOT_FOUND")
            }
        return ApiResponse()
    }
}
