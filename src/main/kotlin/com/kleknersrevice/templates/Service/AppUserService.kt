package com.kleknersrevice.templates.Service

import com.kleknersrevice.templates.Entity.User

interface AppUserService {
    fun findOneUser(id: Long): User
    fun findAllUser(): List<User>
    fun deleteUser(id: Long)
    fun saveUser(user: User)
}