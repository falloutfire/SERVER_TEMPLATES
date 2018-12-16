package com.kleknersrevice.templates.Controller

import com.kleknersrevice.templates.Entity.*
import com.kleknersrevice.templates.Service.DeviceService
import com.kleknersrevice.templates.Service.FilmService
import com.kleknersrevice.templates.Service.LuminophoreService
import com.kleknersrevice.templates.Service.TemplateService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/template/")
class TemplateController(
    private val templateService: TemplateService,
    private val deviceService: DeviceService,
    private val filmService: FilmService,
    private val luminophoreService: LuminophoreService
) {

    @PostMapping("")
    fun addTemplate(@RequestBody template: Template): ResponseEntity<*> {
        deviceService.findDevice(template.device).let {
            if (!it.isPresent) return ResponseEntity(
                messageJson(404, "Device $it not found"),
                HttpStatus.NOT_FOUND
            )
        }
        filmService.findFilm(template.film)
            .let {
                if (!it.isPresent) return ResponseEntity(
                    messageJson(404, "Film $it not found"),
                    HttpStatus.NOT_FOUND
                )
            }
        luminophoreService.findLuminophore(template.luminophore)
            .let {
                if (!it.isPresent) return ResponseEntity(
                    messageJson(404, "Luminophore $it not found"),
                    HttpStatus.NOT_FOUND
                )
            }
        templateService.findTemplate(template).let {
            if (!it.isPresent) {
                templateService.addTemplate(template)
                return ResponseEntity(
                    messageJson(
                        201,
                        "Template $it created"
                    ), HttpStatus.OK
                )
            } else {
                return ResponseEntity(
                    messageJson(
                        200,
                        "Template $it already exist"
                    ), HttpStatus.OK
                )
            }
        }
    }

    @DeleteMapping("")
    fun deleteTemplate(@PathVariable(value = "id") id: Long): ResponseEntity<*> {
        return templateService.getTemplateById(id).let {
            if (it.isPresent) {
                templateService.deleteTemplate(id)
                ResponseEntity(messageJson(200, "Template $it deleted"), HttpStatus.OK)
            } else ResponseEntity(messageJson(404, "Template $it not found"), HttpStatus.NOT_FOUND)
        }
    }

    @PutMapping("")
    fun updateTemplate(@RequestBody template: Template): ResponseEntity<*> {
        return templateService.getTemplateById(template.id)
            .let {
                if (it.isPresent) {
                    templateService.updateTemplate(template)
                    ResponseEntity(messageJson(200, "Template $it updated"), HttpStatus.OK)
                } else {
                    ResponseEntity(messageJson(404, "Template $it not found"), HttpStatus.NOT_FOUND)
                }
            }
    }

    @GetMapping("")
    fun allTemplate(): ResponseEntity<*> {
        return templateService.allTemplate().let {
            if (!it.isEmpty()) ResponseEntity(it, HttpStatus.FOUND) else ResponseEntity(it, HttpStatus.NOT_FOUND)
        }
    }

    @GetMapping("{id}")
    fun getTemplateById(@PathVariable(value = "id") id: Long): ResponseEntity<*> {
        return templateService.getTemplateById(id).let {
            if (it.isPresent) ResponseEntity(it, HttpStatus.FOUND) else ResponseEntity(
                messageJson(
                    404,
                    "Template $it not found"
                ), HttpStatus.NOT_FOUND
            )
        }
    }

    @GetMapping("find_by_device")
    fun getAllByDevice(@RequestBody device: Device): ResponseEntity<*> {
        return deviceService.getDeviceById(device.id).let {
            if (it.isPresent) ResponseEntity(
                templateService.getAllByDevice(device),
                HttpStatus.OK
            ) else ResponseEntity(
                messageJson(
                    404,
                    "Device $it not found"
                ), HttpStatus.NOT_FOUND
            )
        }
    }

    @GetMapping("find_by_luminophore")
    fun getAllByLuminophore(@RequestBody luminophore: Luminophore): ResponseEntity<*> {
        return luminophoreService.getLuminophoreById(luminophore.id).let {
            if (it.isPresent) ResponseEntity(
                templateService.getAllByLuminophore(luminophore),
                HttpStatus.OK
            ) else ResponseEntity(
                messageJson(
                    404,
                    "Luminophore $it not found"
                ), HttpStatus.NOT_FOUND
            )
        }
    }

    @GetMapping("find_by_film")
    fun getAllByFilm(@RequestBody film: Film): ResponseEntity<*> {
        return filmService.getFilmById(film.id).let {
            if (it.isPresent) ResponseEntity(
                templateService.getAllByFilm(film),
                HttpStatus.OK
            ) else ResponseEntity(
                messageJson(
                    404,
                    "Film $it not found"
                ), HttpStatus.NOT_FOUND
            )
        }
    }

    @GetMapping("find_by_color/{colorScheme}")
    fun getAllByColorScheme(@PathVariable(value = "colorScheme") colorScheme: ColorScheme): ResponseEntity<*> {
        return ResponseEntity(templateService.getAllByColorScheme(colorScheme), HttpStatus.OK)
    }

    @GetMapping("find_by_all")
    fun getAllByAllParameters(@RequestBody templateContext: TemplateContext): ResponseEntity<*> {
        deviceService.findDevice(templateContext.device).let {
            if (!it.isPresent) return ResponseEntity(
                messageJson(404, "Device $it not found"),
                HttpStatus.NOT_FOUND
            )
        }
        filmService.findFilm(templateContext.film)
            .let {
                if (!it.isPresent) return ResponseEntity(
                    messageJson(404, "Film $it not found"),
                    HttpStatus.NOT_FOUND
                )
            }
        luminophoreService.findLuminophore(templateContext.luminophore)
            .let {
                if (!it.isPresent) return ResponseEntity(
                    messageJson(404, "Luminophore $it not found"),
                    HttpStatus.NOT_FOUND
                )
            }
        return ResponseEntity(
            templateService.getAllByAllParameters(
                templateContext.device,
                templateContext.film,
                templateContext.luminophore
            ), HttpStatus.OK
        )
    }
}