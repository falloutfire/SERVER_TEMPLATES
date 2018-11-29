package com.kleknersrevice.templates.Controller

import com.kleknersrevice.templates.Entity.OS
import com.kleknersrevice.templates.Service.OsService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/os/")
class OsController(private val osService: OsService) {

    @GetMapping("")
    fun allOs(): ResponseEntity<*> {
        return osService.allOs().let {
            if (it.isEmpty()) ResponseEntity(it, HttpStatus.FOUND) else ResponseEntity(it, HttpStatus.NOT_FOUND)
        }
    }

    @PostMapping("")
    fun addOs(@RequestBody os: OS): ResponseEntity<HttpStatus> {
        osService.addOs(os)
        return ResponseEntity(HttpStatus.CREATED)
    }

    @PutMapping("")
    fun updateOs(@RequestBody os: OS): ResponseEntity<HttpStatus> {
        osService.updateOs(os)
        return ResponseEntity(HttpStatus.OK)
    }

    @DeleteMapping("{id}")
    fun deleteOs(@PathVariable(value = "id") osId: Long): ResponseEntity<HttpStatus> {
        return osService.getOsById(osId).let {
            if (it.isPresent) {
                osService.deleteOs(osId)
                ResponseEntity(HttpStatus.OK)
            } else ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @GetMapping("{id}")
    fun getOsById(@PathVariable(value = "id") osId: Long): ResponseEntity<*> {
        return osService.getOsById(osId).let {
            if (it.isPresent) ResponseEntity(it, HttpStatus.FOUND) else ResponseEntity(it, HttpStatus.NOT_FOUND)
        }
    }

    @GetMapping("test")
    fun getOsById(): ResponseEntity<*> {
        return ResponseEntity("hello", HttpStatus.OK)
    }
}