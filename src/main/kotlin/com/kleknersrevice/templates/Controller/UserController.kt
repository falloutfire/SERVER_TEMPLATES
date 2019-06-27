package com.kleknersrevice.templates.Controller

import com.kleknersrevice.templates.Controller.ResponseValues.Companion.ROLE_ADMIN
import com.kleknersrevice.templates.Controller.ResponseValues.Companion.ROLE_USER
import com.kleknersrevice.templates.Entity.UserDto
import com.kleknersrevice.templates.Service.AppUserService
import com.kleknersrevice.templates.Service.AuthenticationFacadeService
import com.kleknersrevice.templates.Service.RoleService
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.security.access.annotation.Secured
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/users/")
class UserController(
    private val userService: AppUserService,
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
        return userService.findAllUser().run { ApiResponse(HttpStatus.OK, this) }
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
        return ApiResponse(HttpStatus.OK, userService.saveUser(user.toUser()))
    }

    @Secured(ROLE_ADMIN)
    @PutMapping
    fun save(@RequestBody user: UserDto): ApiResponse {
        log.info(
            String.format(
                "received request to create user %s",
                authenticationFacadeService.getAuthentication().principal
            )
        )
        return ApiResponse(HttpStatus.OK, userService.saveUser(user.toUser()))
    }

    @Secured(ROLE_ADMIN, ROLE_USER)
    @GetMapping(value = ["{id}"])
    fun getUser(@PathVariable id: Long): ApiResponse {
        log.info(
            String.format(
                "received request to update user %s",
                authenticationFacadeService.getAuthentication().principal
            )
        )
        return ApiResponse(HttpStatus.OK, userService.findOneUser(id))
    }

    @Secured(ROLE_ADMIN)
    @DeleteMapping(value = ["{id}"])
    fun delete(@PathVariable(value = "id") id: Long?): List<UserDto> {
        log.info(
            String.format(
                "received request to delete user %s",
                authenticationFacadeService.getAuthentication().principal
            )
        )
        userService.deleteUser(id!!)
        return userService.findAllUser()
    }
}


@RestController
@RequestMapping("/roles")
class RoleController(
    private val roleService: RoleService,
    private val authenticationFacadeService: AuthenticationFacadeService
) {

    companion object {
        private val log = LoggerFactory.getLogger(UserController::class.java)
    }

    @Secured(ROLE_ADMIN, ROLE_USER)
    @GetMapping
    fun listRole(): ApiResponse {
        log.info(
            String.format(
                "received request to list user %s",
                authenticationFacadeService.getAuthentication().principal
            )
        )
        return roleService.findAllRole().run { ApiResponse(HttpStatus.OK, this) }
    }
}
