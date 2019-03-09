package com.kleknersrevice.templates.Controller

import com.kleknersrevice.templates.Entity.OS
import com.kleknersrevice.templates.Service.OsService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.annotation.Secured
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("bd_template/os/")
class OsController(private val osService: OsService) {

    @Secured("ROLE_ADMIN")
    @GetMapping("")
    fun allOs(): ApiResponse {
        /*return osService.allOs().let {
            if (!it.isEmpty()) ResponseEntity(it, HttpStatus.FOUND) else ResponseEntity(
                messageJson(404),
                HttpStatus.NOT_FOUND
            )
        }*/
        return ApiResponse(HttpStatus.OK, "success", osService.allOs())
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("")
    fun addOs(@RequestBody os: OS): ResponseEntity<*> {
        return osService.getOs(os).let {
            if (!it.isPresent) {
                osService.addOs(os)
                ResponseEntity(messageJson(201, "OS $it created"), HttpStatus.CREATED)
            } else {
                ResponseEntity(messageJson(200, "OS $it already exist"), HttpStatus.OK)
            }
        }
    }

    @Secured("ROLE_ADMIN")
    @PutMapping("")
    fun updateOs(@RequestBody os: OS): ResponseEntity<*> {
        return osService.getOsById(os.id)
            .let {
                if (it.isPresent) {
                    osService.updateOs(os)
                    ResponseEntity(messageJson(200, "OS $os updated"), HttpStatus.OK)
                } else {
                    ResponseEntity(messageJson(404, "OS $os not found"), HttpStatus.NOT_FOUND)
                }
            }
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("{id}")
    fun deleteOs(@PathVariable(value = "id") osId: Long): ResponseEntity<*> {
        return osService.getOsById(osId).let {
            if (it.isPresent) {
                osService.deleteOs(osId)
                ResponseEntity(messageJson(201, "OS $it deleted"), HttpStatus.OK)
            } else ResponseEntity(messageJson(404, "OS $it not found"), HttpStatus.NOT_FOUND)
        }
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("{id}")
    fun getOsById(@PathVariable(value = "id") osId: Long): ResponseEntity<*> {
        return osService.getOsById(osId).let {
            if (it.isPresent) ResponseEntity(it, HttpStatus.FOUND) else ResponseEntity(
                messageJson(404),
                HttpStatus.NOT_FOUND
            )
        }
    }
}