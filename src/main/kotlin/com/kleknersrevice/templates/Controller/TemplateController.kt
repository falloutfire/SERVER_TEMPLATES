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
    fun addTemplate(@RequestBody template: Template): ResponseEntity<HttpStatus> {
        templateService.addTemplate(template)
        return ResponseEntity(HttpStatus.CREATED)
    }

    @DeleteMapping("")
    fun deleteTemplate(@PathVariable(value = "id") id: Long): ResponseEntity<HttpStatus> {
        return templateService.getTemplateById(id).let {
            if (it.isPresent) {
                templateService.deleteTemplate(id)
                ResponseEntity(HttpStatus.OK)
            } else ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @PutMapping("")
    fun updateTemplate(@RequestBody template: Template): ResponseEntity<HttpStatus> {
        templateService.updateTemplate(template)
        return ResponseEntity(HttpStatus.OK)
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
            if (it.isPresent) ResponseEntity(it, HttpStatus.FOUND) else ResponseEntity(it, HttpStatus.NOT_FOUND)
        }
    }

    @PostMapping("find_by_device")
    fun getAllByDevice(@RequestBody device: Device): ResponseEntity<List<Template>> {
        return deviceService.getDeviceById(device.id).let {
            if (it.isPresent) ResponseEntity(
                templateService.getAllByDevice(device),
                HttpStatus.OK
            ) else ResponseEntity(null, HttpStatus.NOT_FOUND)
        }
    }

    @PostMapping("find_by_luminophore")
    fun getAllByLuminophore(@RequestBody luminophore: Luminophore): ResponseEntity<List<Template>> {
        return luminophoreService.getLuminophoreById(luminophore.id).let {
            if (it.isPresent) ResponseEntity(
                templateService.getAllByLuminophore(luminophore),
                HttpStatus.OK
            ) else ResponseEntity(null, HttpStatus.NOT_FOUND)
        }
    }

    @PostMapping("find_by_film")
    fun getAllByFilm(@RequestBody film: Film): ResponseEntity<List<Template>> {
        return filmService.getFilmById(film.id).let {
            if (it.isPresent) ResponseEntity(
                templateService.getAllByFilm(film),
                HttpStatus.OK
            ) else ResponseEntity(null, HttpStatus.NOT_FOUND)
        }
    }

    @PostMapping("find_by_color")
    fun getAllByColorScheme(@RequestBody colorScheme: ColorScheme): ResponseEntity<List<Template>> {
        return ResponseEntity(templateService.getAllByColorScheme(colorScheme), HttpStatus.OK)
    }

    @PostMapping("find_by_all")
    fun getAllByAllParameters(@RequestBody templateContext: TemplateContext): ResponseEntity<List<Template>> {
        return ResponseEntity(
            templateService.getAllByAllParameters(
                templateContext.device,
                templateContext.film,
                templateContext.luminophore
            ), HttpStatus.OK
        )
    }
}