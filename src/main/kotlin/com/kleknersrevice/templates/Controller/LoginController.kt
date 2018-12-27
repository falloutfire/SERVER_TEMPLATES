package com.kleknersrevice.templates.Controller

import com.kleknersrevice.templates.security.JwtGenerator
import com.kleknersrevice.templates.security.Model.JwtUser
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("/login")
class LoginController(private val jwtGenerator: JwtGenerator) {

    @PostMapping
    fun generate(@RequestBody jwtUser: JwtUser): ResponseEntity<*> {
        jwtGenerator.generate(jwtUser)?.let {
            return ResponseEntity.ok(it)
        }
        return ResponseEntity("", HttpStatus.FORBIDDEN)

        //return jwtGenerator.generate(jwtUser)

    }
}