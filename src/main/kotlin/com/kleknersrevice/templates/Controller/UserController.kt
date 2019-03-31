package com.kleknersrevice.templates.Controller

import com.kleknersrevice.templates.Controller.ResponseValues.Companion.ROLE_ADMIN
import com.kleknersrevice.templates.Controller.ResponseValues.Companion.ROLE_USER
import com.kleknersrevice.templates.Controller.ResponseValues.Companion.SUCCESS
import com.kleknersrevice.templates.Entity.UserDto
import com.kleknersrevice.templates.Service.AuthenticationFacadeService
import com.kleknersrevice.templates.Service.UserService
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.security.access.annotation.Secured
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/users")
class UserController(
    private val userService: UserService,
    private val authenticationFacadeService: AuthenticationFacadeService
) {

    private val log = LoggerFactory.getLogger(UserController::class.java)

    @Secured(ROLE_ADMIN)
    @GetMapping
    fun listUser(): ApiResponse {
        log.info(
            String.format(
                "received request to list user %s",
                authenticationFacadeService.getAuthentication().principal
            )
        )
        return userService.findAll().run { ApiResponse(HttpStatus.OK, SUCCESS, this) }
    }

    @Secured(ROLE_ADMIN)
    @PostMapping
    fun create(@RequestBody user: UserDto): ApiResponse {
        log.info(
            String.format(
                "received request to create user %s",
                authenticationFacadeService.getAuthentication().principal
            )
        )
        return ApiResponse(HttpStatus.OK, SUCCESS, userService.save(user))
    }

    @Secured(ROLE_ADMIN, ROLE_USER)
    @GetMapping(value = ["/{id}"])
    fun getUser(@PathVariable id: Long): ApiResponse {
        log.info(
            String.format(
                "received request to update user %s",
                authenticationFacadeService.getAuthentication().principal
            )
        )
        return ApiResponse(HttpStatus.OK, SUCCESS, userService.findOne(id))
    }

    @Secured(ROLE_ADMIN)
    @DeleteMapping(value = ["/{id}"])
    fun delete(@PathVariable(value = "id") id: Long?) {
        log.info(
            String.format(
                "received request to delete user %s",
                authenticationFacadeService.getAuthentication().principal
            )
        )
        userService.delete(id!!)
    }

}
