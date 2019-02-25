package com.kleknersrevice.templates.Controller

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

    val log = LoggerFactory.getLogger(UserController::class.java)

    @Secured(ROLE_ADMIN)
    @GetMapping
    fun listUser(): ApiResponse {
        log.info(
            "received request to list user ${authenticationFacadeService.getAuthentication().principal}"
        )
        return ApiResponse(HttpStatus.OK, SUCCESS, userService.findAll())
    }

    @Secured(ROLE_ADMIN)
    @PostMapping
    fun create(@RequestBody userDto: UserDto): ApiResponse {
        log.info(
            "received request to create user ${authenticationFacadeService.getAuthentication().principal}"
        )
        return ApiResponse(HttpStatus.OK, SUCCESS, userService.save(userDto))
    }

    @Secured(ROLE_ADMIN)
    @DeleteMapping(value = ["/{id}"])
    fun delete(@PathVariable id: Long): ApiResponse {
        log.info(
            "received request to delete user ${authenticationFacadeService.getAuthentication().principal}"
        )
        return ApiResponse(HttpStatus.OK, SUCCESS, userService.delete(id))
    }

    @Secured(ROLE_ADMIN, ROLE_USER)
    @GetMapping(value = ["/{id}"])
    fun update(@PathVariable id: Long): ApiResponse {
        log.info(
            "received request to update user ${authenticationFacadeService.getAuthentication().principal}"
        )
        return ApiResponse(HttpStatus.OK, SUCCESS, userService.findOne(id))
    }

    companion object {
        const val ROLE_ADMIN: String = "ROLE_ADMIN"
        const val SUCCESS = "success"
        const val ROLE_USER = "ROLE_USER"
    }
}