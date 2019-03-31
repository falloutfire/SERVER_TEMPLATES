package com.kleknersrevice.templates

import com.kleknersrevice.templates.Service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableAsync


@SpringBootApplication
@EnableAsync
class Application /*: CommandLineRunner*/ {

    @Autowired
    lateinit var userService: UserService

    /*override fun run(vararg args: String?) {


        if (userService.findByUserName("admin1") == null) {
            val userDto = UserDto()
            userDto.username = "admin1"
            userDto.role = listOf("ADMIN")
            userDto.email = "12"
            userDto.password = "admin"
            userService.save(userDto)
        }

    }*/
}


fun main(args: Array<String>) {
    runApplication<Application>(*args)
}