package com.kleknersrevice.templates.Controller

import com.kleknersrevice.templates.Controller.ResponseValues.Companion.CREATED
import com.kleknersrevice.templates.Controller.ResponseValues.Companion.DELETED
import com.kleknersrevice.templates.Controller.ResponseValues.Companion.EXIST
import com.kleknersrevice.templates.Controller.ResponseValues.Companion.NOT_FOUND
import com.kleknersrevice.templates.Controller.ResponseValues.Companion.ROLE_ADMIN
import com.kleknersrevice.templates.Controller.ResponseValues.Companion.ROLE_USER
import com.kleknersrevice.templates.Controller.ResponseValues.Companion.UPDATED
import com.kleknersrevice.templates.Entity.Luminophore
import com.kleknersrevice.templates.Service.AuthenticationFacadeService
import com.kleknersrevice.templates.Service.LuminophoreService
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.security.access.annotation.Secured
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("bd_template/luminophore")
class LuminophoreController(
    private val luminophoreService: LuminophoreService,
    private val authenticationFacadeService: AuthenticationFacadeService
) {

    private val log = LoggerFactory.getLogger(LuminophoreController::class.java)

    @Secured(ROLE_ADMIN)
    @PostMapping("")
    fun addLuminophore(@RequestBody luminophore: Luminophore): ApiResponse {
        log.info(
            String.format(
                "received request to save Luminophore %s",
                authenticationFacadeService.getAuthentication().principal
            )
        )
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
        log.info(
            String.format(
                "received request to delete Luminophore %s",
                authenticationFacadeService.getAuthentication().principal
            )
        )
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
        log.info(
            String.format(
                "received request to update Luminophore %s",
                authenticationFacadeService.getAuthentication().principal
            )
        )
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
        log.info(
            String.format(
                "received request to get all Luminophore %s",
                authenticationFacadeService.getAuthentication().principal
            )
        )
        return luminophoreService.allLuminophore().run {
            ApiResponse(HttpStatus.OK, this)
        }
    }

    @Secured(ROLE_ADMIN, ROLE_USER)
    @GetMapping("/{id}")
    fun getLuminophoreById(@PathVariable(value = "id") id: Long): ApiResponse {
        log.info(
            String.format(
                "received request to get by id Luminophore %s",
                authenticationFacadeService.getAuthentication().principal
            )
        )
        return luminophoreService.getLuminophoreById(id).run {
            if (isPresent) ApiResponse(HttpStatus.OK, this) else ApiResponse(
                HttpStatus.NOT_FOUND,
                "Luminophore $NOT_FOUND"
            )
        }
    }
}
