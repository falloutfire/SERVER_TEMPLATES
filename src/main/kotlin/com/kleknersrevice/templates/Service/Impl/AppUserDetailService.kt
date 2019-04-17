package com.kleknersrevice.templates.Service.Impl

import com.kleknersrevice.templates.Entity.Role
import com.kleknersrevice.templates.Entity.User
import com.kleknersrevice.templates.Repository.RoleRepository
import com.kleknersrevice.templates.Repository.UserRepository
import com.kleknersrevice.templates.Service.AppUserService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import java.util.*

@Component
@Service("userService")
class AppUserDetailsService : UserDetailsService, AppUserService {

    @Autowired
    private val userRepository: UserRepository? = null

    @Autowired
    private val roleRepository: RoleRepository? = null

    @Autowired
    private val passwordEncoder: BCryptPasswordEncoder? = null

    override fun addUser(user: User) {
        val userWithDuplicateUsername = userRepository!!.findByUsername(user.username!!)
        if (userWithDuplicateUsername.isPresent && user.id !== userWithDuplicateUsername.get().id) {
            log.error(String.format("Duplicate username ", user.username))
            throw RuntimeException("Duplicate username.")
        }
        /*val userWithDuplicateEmail = userRepository.findByEmail(userDto.email!!)
        if (userWithDuplicateEmail.isPresent && userDto.id !== userWithDuplicateEmail.get().id) {
            log.error(String.format("Duplicate email ", userDto.email))
            throw RuntimeException("Duplicate email.")
        }*/
        val userSave = User()
        userSave.username = user.username
        userSave.password = passwordEncoder!!.encode(user.password!!)
        val roleTypes = ArrayList<Role>()
        user.roles!!.stream().map { role -> roleTypes.add(role) }
        userSave.roles = user.roles//roleRepository!!.find(user.roles!!)
        userRepository.save(userSave)
        //return userSave
    }

    override fun deleteUser(id: Long) {
        userRepository?.deleteById(id)
    }

    override fun editUser(user: User) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun findOneUser(id: Long): User {
        return userRepository!!.findById(id).get()
    }

    override fun findAllUser(): List<User> {
        return userRepository!!.findAll()
    }

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(s: String): UserDetails {
        val user = userRepository?.findByUsername(s)
            ?: throw UsernameNotFoundException(String.format("The username %s doesn't exist", s))

        val authorities = ArrayList<GrantedAuthority>()
        user.get().roles?.forEach { role -> authorities.add(SimpleGrantedAuthority(role.roleName)) }

        return org.springframework.security.core.userdetails.User(user.get().username, user.get().password, authorities)
    }

    companion object {

        private val log = LoggerFactory.getLogger(AppUserDetailsService::class.java)
    }
}