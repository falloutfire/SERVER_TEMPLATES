package com.kleknersrevice.templates.Service

import com.kleknersrevice.templates.Entity.User

interface AppUserService {
    fun addUser(user: User)
    fun editUser(user: User)
    fun findOneUser(id: Long): User
    fun findAllUser(): List<User>
    fun deleteUser(id: Long)
}