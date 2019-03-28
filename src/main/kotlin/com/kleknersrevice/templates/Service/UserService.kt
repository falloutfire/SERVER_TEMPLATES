package com.kleknersrevice.templates.Service

import com.kleknersrevice.templates.Entity.UserDto
import com.kleknersrevice.templates.Entity.Users

interface UserService {
    fun save(userDto: UserDto): UserDto
    fun findAll(): List<UserDto>
    fun findOne(id: Long): Users
    fun delete(id: Long)
    fun findByUserName(username: String): Users?
}