package com.kleknersrevice.templates.Controller

import com.kleknersrevice.templates.Entity.UserDto
import com.kleknersrevice.templates.Service.AuthenticationFacadeService
import com.kleknersrevice.templates.Service.UserService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.security.access.annotation.Secured
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/users")
class UserController(private val userService: UserService, private val authenticationFacadeService: AuthenticationFacadeService) {

    /*@Autowired
    private val userService: UserService? = null

    @Autowired
    private val authenticationFacadeService: AuthenticationFacadeService? = null*/

    private val log = LoggerFactory.getLogger(UserController::class.java)

    //@Secured("ROLE_ADMIN")
    @GetMapping
    fun listUser(): ApiResponse {
        log.info(
            String.format(
                "received request to list user %s",
                authenticationFacadeService!!.getAuthentication().principal
            )
        )
        return ApiResponse(HttpStatus.OK, "success", userService!!.findAll())
    }

    //@Secured("ROLE_ADMIN")
    @PostMapping
    fun create(@RequestBody user: UserDto): ApiResponse {
        log.info(
            String.format(
                "received request to create user %s",
                authenticationFacadeService!!.getAuthentication().principal
            )
        )
        return ApiResponse(HttpStatus.OK, "success", userService!!.save(user))
    }

    //@Secured("ROLE_ADMIN")
    @GetMapping(value = ["/{id}"])
    fun getUser(@PathVariable id: Long): ApiResponse {
        log.info(
            String.format(
                "received request to update user %s",
                authenticationFacadeService!!.getAuthentication().principal
            )
        )
        return ApiResponse(HttpStatus.OK, "success", userService!!.findOne(id))
    }

    //@Secured("ROLE_ADMIN")
    @DeleteMapping(value = ["/{id}"])
    fun delete(@PathVariable(value = "id") id: Long?) {
        log.info(
            String.format(
                "received request to delete user %s",
                authenticationFacadeService!!.getAuthentication().principal
            )
        )
        userService!!.delete(id!!)
    }

   /* companion object {

        const val SUCCESS = "success"
        const val ROLE_ADMIN = "ROLE_ADMIN"
        const val ROLE_USER = "ROLE_USER"
    }*/


}