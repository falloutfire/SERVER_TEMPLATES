package com.kleknersrevice.templates.Controller

import com.kleknersrevice.templates.Controller.ResponseValues.Companion.CREATED
import com.kleknersrevice.templates.Controller.ResponseValues.Companion.DELETED
import com.kleknersrevice.templates.Controller.ResponseValues.Companion.EXIST
import com.kleknersrevice.templates.Controller.ResponseValues.Companion.NOT_FOUND
import com.kleknersrevice.templates.Controller.ResponseValues.Companion.ROLE_ADMIN
import com.kleknersrevice.templates.Controller.ResponseValues.Companion.ROLE_USER
import com.kleknersrevice.templates.Controller.ResponseValues.Companion.SUCCESS
import com.kleknersrevice.templates.Controller.ResponseValues.Companion.UPDATED
import com.kleknersrevice.templates.Entity.OS
import com.kleknersrevice.templates.Service.AuthenticationFacadeService
import com.kleknersrevice.templates.Service.OsService
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.security.access.annotation.Secured
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("bd_template/os")
class OsController(
    private val osService: OsService,
    private val authenticationFacadeService: AuthenticationFacadeService
) {

    private val log = LoggerFactory.getLogger(OsController::class.java)

    @Secured(ROLE_ADMIN, ROLE_USER)
    @GetMapping("")
    fun allOs(): ApiResponse {
        log.info(
            String.format(
                "received request to list os %s",
                authenticationFacadeService.getAuthentication().principal
            )
        )
        return osService.allOs().run {
            ApiResponse(HttpStatus.OK, "SUCCESS", this)
        }
    }

    @Secured(ROLE_ADMIN)
    @PostMapping("")
    fun addOs(@RequestBody os: OS): ApiResponse {
        log.info(
            String.format(
                "received request to add os %s",
                authenticationFacadeService.getAuthentication().principal
            )
        )
        return osService.getOs(os).run {
            if (!isPresent) {
                osService.addOs(os)
                ApiResponse(HttpStatus.CREATED, "OS $CREATED")
            } else {
                ApiResponse(HttpStatus.OK, "OS $EXIST")
            }
        }
    }

    @Secured(ROLE_ADMIN)
    @PutMapping("")
    fun updateOs(@RequestBody os: OS): ApiResponse {
        log.info(
            String.format(
                "received request to update os %s",
                authenticationFacadeService.getAuthentication().principal
            )
        )
        return osService.getOsById(os.id)
            .let {
                if (it.isPresent) {
                    osService.updateOs(os)
                    ApiResponse(HttpStatus.OK, "OS $UPDATED")
                } else {
                    ApiResponse(HttpStatus.NOT_FOUND, "OS $NOT_FOUND")
                }
            }
    }

    @Secured(ROLE_ADMIN)
    @DeleteMapping("/{id}")
    fun deleteOs(@PathVariable(value = "id") osId: Long): ApiResponse {
        log.info(
            String.format(
                "received request to delete os %s",
                authenticationFacadeService.getAuthentication().principal
            )
        )
        return osService.getOsById(osId).run {
            if (isPresent) {
                osService.deleteOs(osId)
                ApiResponse(HttpStatus.OK, "OS $DELETED")
            } else ApiResponse(HttpStatus.NOT_FOUND, "OS $NOT_FOUND")
        }
    }

    @Secured(ROLE_ADMIN, ROLE_USER)
    @GetMapping("/{id}")
    fun getOsById(@PathVariable(value = "id") osId: Long): ApiResponse {
        log.info(
            String.format(
                "received request to get os by id %s",
                authenticationFacadeService.getAuthentication().principal
            )
        )
        return osService.getOsById(osId).run {
            if (isPresent) ApiResponse(HttpStatus.OK, SUCCESS, this) else ApiResponse(
                HttpStatus.NOT_FOUND,
                "OS $NOT_FOUND"
            )
        }
    }
}