package com.kleknersrevice.templates.Controller

import com.kleknersrevice.templates.Controller.ResponseValues.Companion.CREATED
import com.kleknersrevice.templates.Controller.ResponseValues.Companion.EXIST
import com.kleknersrevice.templates.Controller.ResponseValues.Companion.NOT_FOUND
import com.kleknersrevice.templates.Controller.ResponseValues.Companion.ROLE_ADMIN
import com.kleknersrevice.templates.Controller.ResponseValues.Companion.ROLE_USER
import com.kleknersrevice.templates.Controller.ResponseValues.Companion.UPDATED
import com.kleknersrevice.templates.Entity.*
import com.kleknersrevice.templates.Service.DeviceService
import com.kleknersrevice.templates.Service.FilmService
import com.kleknersrevice.templates.Service.LuminophoreService
import com.kleknersrevice.templates.Service.TemplateService
import org.springframework.http.HttpStatus
import org.springframework.security.access.annotation.Secured
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("bd_template/template")
class TemplateController(
    private val templateService: TemplateService,
    private val deviceService: DeviceService,
    private val filmService: FilmService,
    private val luminophoreService: LuminophoreService
) {

    @Secured(ROLE_ADMIN)
    @PostMapping("")
    fun addTemplate(@RequestBody template: Template): ApiResponse {
        deviceService.findDevice(template.device).run {
            if (!isPresent) return ApiResponse(HttpStatus.NOT_FOUND, "Device $NOT_FOUND")
        }
        filmService.findFilm(template.film)
            .run {
                if (!isPresent) return ApiResponse(HttpStatus.NOT_FOUND, "Film $NOT_FOUND")
            }
        luminophoreService.findLuminophore(template.luminophore)
            .run {
                if (!isPresent) return ApiResponse(HttpStatus.NOT_FOUND, "Luminophore $NOT_FOUND")
            }
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
        return templateService.allTemplate().run {
            ApiResponse(HttpStatus.OK, this)
        }
    }

    @Secured(ROLE_ADMIN, ROLE_USER)
    @GetMapping("/{id}")
    fun getTemplateById(@PathVariable(value = "id") id: Long): ApiResponse {
        return templateService.getTemplateById(id).run {
            if (isPresent) ApiResponse(HttpStatus.OK, this) else
                ApiResponse(HttpStatus.NOT_FOUND, "Template $NOT_FOUND")
        }
    }

    @Secured(ROLE_ADMIN, ROLE_USER)
    @GetMapping("/find_by_device")
    fun getAllByDevice(@RequestBody device: Device): ApiResponse {
        return deviceService.getDeviceById(device.id).run {
            if (isPresent)
                ApiResponse(HttpStatus.OK, this)
            else ApiResponse(HttpStatus.NOT_FOUND, "Template $NOT_FOUND")
        }
    }

    @Secured(ROLE_ADMIN, ROLE_USER)
    @GetMapping("/find_by_luminophore")
    fun getAllByLuminophore(@RequestBody luminophore: Luminophore): ApiResponse {
        return luminophoreService.getLuminophoreById(luminophore.id).run {
            if (isPresent) ApiResponse(HttpStatus.OK, this)
            else ApiResponse(HttpStatus.NOT_FOUND, "Template $NOT_FOUND")
        }
    }

    @Secured(ROLE_ADMIN, ROLE_USER)
    @GetMapping("/find_by_film")
    fun getAllByFilm(@RequestBody film: Film): ApiResponse {
        return filmService.getFilmById(film.id).run {
            if (isPresent) ApiResponse(HttpStatus.OK, this)
            else ApiResponse(HttpStatus.NOT_FOUND, "Template $NOT_FOUND")
        }
    }

    @Secured(ROLE_ADMIN, ROLE_USER)
    @GetMapping("/find_by_color/{colorScheme}")
    fun getAllByColorScheme(@PathVariable(value = "colorScheme") colorScheme: ColorScheme): ApiResponse {
        return ApiResponse(HttpStatus.OK, templateService.getAllByColorScheme(colorScheme))
    }

    @Secured(ROLE_ADMIN, ROLE_USER)
    @GetMapping("/find_by_all")
    fun getAllByAllParameters(@RequestBody templateContext: TemplateContext): ApiResponse {
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
