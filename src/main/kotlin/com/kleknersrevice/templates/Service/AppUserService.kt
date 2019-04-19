package com.kleknersrevice.templates.Service

import com.kleknersrevice.templates.Entity.Role
import com.kleknersrevice.templates.Entity.User
import com.kleknersrevice.templates.Entity.UserDto

interface AppUserService {
    fun findOneUser(id: Long): UserDto
    fun findAllUser(): List<UserDto>
    fun deleteUser(id: Long)
    fun saveUser(user: User)
}

interface RoleService {
    fun findAllRole(): List<Role>
}