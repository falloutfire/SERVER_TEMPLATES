package com.kleknersrevice.templates.Controller

import com.kleknersrevice.templates.Controller.ResponseValues.Companion.CREATED
import com.kleknersrevice.templates.Controller.ResponseValues.Companion.DELETED
import com.kleknersrevice.templates.Controller.ResponseValues.Companion.EXIST
import com.kleknersrevice.templates.Controller.ResponseValues.Companion.NOT_FOUND
import com.kleknersrevice.templates.Controller.ResponseValues.Companion.ROLE_ADMIN
import com.kleknersrevice.templates.Controller.ResponseValues.Companion.ROLE_USER
import com.kleknersrevice.templates.Controller.ResponseValues.Companion.SUCCESS
import com.kleknersrevice.templates.Controller.ResponseValues.Companion.UPDATED
import com.kleknersrevice.templates.Entity.Luminophore
import com.kleknersrevice.templates.Service.LuminophoreService
import org.springframework.http.HttpStatus
import org.springframework.security.access.annotation.Secured
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("bd_template/luminophore")
class LuminophoreController(private val luminophoreService: LuminophoreService) {

    @Secured(ROLE_ADMIN)
    @PostMapping("")
    fun addLuminophore(@RequestBody luminophore: Luminophore): ApiResponse {
        luminophoreService.findLuminophore(luminophore).let {
            return if (!it.isPresent) {
                luminophoreService.addLuminophore(luminophore)
                ApiResponse(HttpStatus.CREATED, "Luminophore $CREATED")
            } else {
                ApiResponse(HttpStatus.OK, "Luminophore $EXIST")
            }
        }
    }

    @Secured(ROLE_ADMIN)
    @DeleteMapping("/{id}")
    fun deleteLuminophore(@PathVariable(value = "id") id: Long): ApiResponse {
        return luminophoreService.getLuminophoreById(id).let {
            if (it.isPresent) {
                luminophoreService.deleteLuminophore(id)
                ApiResponse(HttpStatus.OK, "Luminophore $DELETED")
            } else ApiResponse(HttpStatus.NOT_FOUND, "Luminophore $NOT_FOUND")
        }
    }

    @Secured(ROLE_ADMIN)
    @PutMapping("")
    fun updateLuminophore(@RequestBody luminophore: Luminophore): ApiResponse {
        return luminophoreService.getLuminophoreById(luminophore.id)
            .let {
                if (it.isPresent) {
                    luminophoreService.updateLuminophore(luminophore)
                    ApiResponse(HttpStatus.OK, "Luminophore $UPDATED")
                } else {
                    ApiResponse(HttpStatus.NOT_FOUND, "Luminophore $NOT_FOUND")
                }
            }
    }

    @Secured(ROLE_ADMIN, ROLE_USER)
    @GetMapping("")
    fun allLuminophore(): ApiResponse {
        return luminophoreService.allLuminophore().run {
            ApiResponse(HttpStatus.OK, SUCCESS, this)
        }
    }

    @Secured(ROLE_ADMIN, ROLE_USER)
    @GetMapping("/{id}")
    fun getLuminophoreById(@PathVariable(value = "id") id: Long): ApiResponse {
        return luminophoreService.getLuminophoreById(id).run {
            if (isPresent) ApiResponse(HttpStatus.OK, SUCCESS, this) else ApiResponse(
                HttpStatus.NOT_FOUND,
                "Luminophore $NOT_FOUND"
            )
        }
    }
}