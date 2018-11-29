package com.kleknersrevice.templates.Controller

import com.kleknersrevice.templates.Entity.Luminophore
import com.kleknersrevice.templates.Service.LuminophoreService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/luminophore/")
class LuminophoreController(private val luminophoreService: LuminophoreService) {

    @PostMapping("")
    fun addLuminophore(@RequestBody luminophore: Luminophore): ResponseEntity<HttpStatus> {
        luminophoreService.addLuminophore(luminophore)
        return ResponseEntity(HttpStatus.CREATED)
    }

    @DeleteMapping("{id}")
    fun deleteLuminophore(@PathVariable(value = "id") id: Long): ResponseEntity<HttpStatus> {
        return luminophoreService.getLuminophoreById(id).let {
            if (it.isPresent) {
                luminophoreService.deleteLuminophore(id)
                ResponseEntity(HttpStatus.OK)
            } else ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @PutMapping("")
    fun updateLuminophore(@RequestBody luminophore: Luminophore): ResponseEntity<HttpStatus> {
        luminophoreService.updateLuminophore(luminophore)
        return ResponseEntity(HttpStatus.OK)
    }

    @GetMapping("")
    fun allLuminophore(): ResponseEntity<*> {
        return luminophoreService.allLuminophore().let {
            if (it.isEmpty()) ResponseEntity(it, HttpStatus.FOUND) else ResponseEntity(it, HttpStatus.NOT_FOUND)
        }
    }

    @GetMapping("{id}")
    fun getLuminophoreById(@PathVariable(value = "id") id: Long): ResponseEntity<*> {
        return luminophoreService.getLuminophoreById(id).let {
            if (it.isPresent) ResponseEntity(it, HttpStatus.FOUND) else ResponseEntity(it, HttpStatus.NOT_FOUND)
        }
    }
}