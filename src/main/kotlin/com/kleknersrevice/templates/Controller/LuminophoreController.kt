package com.kleknersrevice.templates.Controller

import com.kleknersrevice.templates.Entity.Luminophore
import com.kleknersrevice.templates.Service.LuminophoreService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.annotation.Secured
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("bd_template/luminophore/")
class LuminophoreController(private val luminophoreService: LuminophoreService) {

    //@Secured("ROLE_ADMIN")
    @PostMapping("")
    fun addLuminophore(@RequestBody luminophore: Luminophore): ResponseEntity<*> {
        luminophoreService.findLuminophore(luminophore).let {
            if (!it.isPresent) {
                luminophoreService.addLuminophore(luminophore)
                return ResponseEntity(
                    messageJson(
                        201,
                        "Luminophore $it created"
                    ), HttpStatus.CREATED
                )
            } else {
                return ResponseEntity(
                    messageJson(
                        200,
                        "Luminophore $it already exist"
                    ), HttpStatus.OK
                )
            }
        }
    }

    //@Secured("ROLE_ADMIN")
    @DeleteMapping("{id}")
    fun deleteLuminophore(@PathVariable(value = "id") id: Long): ResponseEntity<*> {
        return luminophoreService.getLuminophoreById(id).let {
            if (it.isPresent) {
                luminophoreService.deleteLuminophore(id)
                ResponseEntity(messageJson(200, "Luminophore $it deleted"), HttpStatus.OK)
            } else ResponseEntity(messageJson(404, "Luminophore $it not found"), HttpStatus.NOT_FOUND)
        }
    }

    //@Secured("ROLE_ADMIN")
    @PutMapping("")
    fun updateLuminophore(@RequestBody luminophore: Luminophore): ResponseEntity<*> {
        return luminophoreService.getLuminophoreById(luminophore.id)
            .let {
                if (it.isPresent) {
                    luminophoreService.updateLuminophore(luminophore)
                    ResponseEntity(messageJson(200, "Luminophore $it updated"), HttpStatus.OK)
                } else {
                    ResponseEntity(messageJson(404, "Luminophore $it not found"), HttpStatus.NOT_FOUND)
                }
            }
    }

    //@Secured("ROLE_ADMIN")
    @GetMapping("")
    fun allLuminophore(): ResponseEntity<*> {
        return luminophoreService.allLuminophore().let {
            if (!it.isEmpty()) ResponseEntity(it, HttpStatus.FOUND) else ResponseEntity(it, HttpStatus.NOT_FOUND)
        }
    }

    //@Secured("ROLE_ADMIN")
    @GetMapping("{id}")
    fun getLuminophoreById(@PathVariable(value = "id") id: Long): ResponseEntity<*> {
        return luminophoreService.getLuminophoreById(id).let {
            if (it.isPresent) ResponseEntity(it, HttpStatus.FOUND) else ResponseEntity(
                messageJson(
                    404,
                    "Luminophore $it not found"
                ), HttpStatus.NOT_FOUND
            )
        }
    }
}