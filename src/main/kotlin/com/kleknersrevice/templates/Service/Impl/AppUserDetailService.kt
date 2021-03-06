package com.kleknersrevice.templates.Service.Impl

import com.kleknersrevice.templates.Entity.Role
import com.kleknersrevice.templates.Entity.User
import com.kleknersrevice.templates.Entity.UserDto
import com.kleknersrevice.templates.Repository.RoleRepository
import com.kleknersrevice.templates.Repository.UserRepository
import com.kleknersrevice.templates.Service.AppUserService
import com.kleknersrevice.templates.Service.RoleService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service

@Component
@Service("userService")
class AppUserDetailsService : UserDetailsService, AppUserService {

    @Autowired
    private val userRepository: UserRepository? = null

    @Autowired
    private val roleRepository: RoleRepository? = null

    @Autowired
    private val passwordEncoder: BCryptPasswordEncoder? = null

    override fun saveUser(user: User) {
        val userWithDuplicateUsername = userRepository!!.findByUserName(user.username)
        if (userWithDuplicateUsername.isPresent && user.id != userWithDuplicateUsername.get().id) {
            log.error(String.format("Duplicate username ", user.username))
            throw RuntimeException("Duplicate username.")
        }
        val userWithDuplicateEmail = userRepository.findByEmail(user.email!!)
        if (userWithDuplicateEmail.isPresent && user.id != userWithDuplicateEmail.get().id) {
            log.error(String.format("Duplicate email ", user.email))
            throw RuntimeException("Duplicate email.")
        }
        user.userPassword = passwordEncoder!!.encode(user.password)
        val roleTypes = ArrayList<Role>()
        user.roles!!.stream().map { role ->
            roleRepository?.findRoleById(role.id!!).let {
                if (it!!.isPresent) {
                    roleTypes.add(role)
                }
            }
        }
        userRepository.save(user)
    }

    override fun deleteUser(id: Long) {
        userRepository?.deleteById(id)
    }

    override fun findOneUser(id: Long): UserDto {
        return UserDto.toUserDto(userRepository!!.findById(id).get())
    }

    override fun findAllUser(): List<UserDto> {
        return userRepository!!.findAll().map {
            UserDto.toUserDto(it)
        }
    }

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(s: String): UserDetails {
        val user = userRepository?.findByUserName(s)
            ?: throw UsernameNotFoundException(String.format("The username %s doesn't exist", s))
        return user.get()
    }

    companion object {

        private val log = LoggerFactory.getLogger(AppUserDetailsService::class.java)
    }
}


@Component
@Service
class RoleServiceImpl : RoleService {
    @Autowired
    private val roleRepository: RoleRepository? = null

    override fun findAllRole(): List<Role> {
        return roleRepository?.findAll() as List<Role>
    }

}