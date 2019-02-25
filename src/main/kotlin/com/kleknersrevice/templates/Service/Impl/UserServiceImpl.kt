package com.kleknersrevice.templates.Service.Impl

import com.kleknersrevice.templates.Entity.RoleType
import java.util.ArrayList
import com.kleknersrevice.templates.Entity.UserDto
import com.kleknersrevice.templates.Entity.Users
import com.kleknersrevice.templates.Repository.RoleRepository
import com.kleknersrevice.templates.Repository.UserRepository
import java.util.stream.Collectors
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.beans.factory.annotation.Autowired
import com.kleknersrevice.templates.Service.UserService
import org.slf4j.LoggerFactory
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Transactional
@Service(value = "userService")
open class UserServiceImpl : UserDetailsService, UserService {

    @Autowired
    private val userDao: UserRepository? = null

    @Autowired
    private val roleDao: RoleRepository? = null

    @Autowired
    private val passwordEncoder: BCryptPasswordEncoder? = null

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(userId: String): UserDetails {
        val user = userDao!!.findByUsername(userId)
        if (!user.isPresent) {
            log.error("Invalid username or password.")
            throw UsernameNotFoundException("Invalid username or password.")
        }
        val grantedAuthorities = getAuthorities(user.get())


        return org.springframework.security.core.userdetails.User(
            user!!.get().username,
            user!!.get().password,
            grantedAuthorities
        )
    }

    private fun getAuthorities(user: Users): Set<GrantedAuthority> {
        val roleByUserId = user.roles
        return roleByUserId!!.stream()
            .map { role -> SimpleGrantedAuthority("ROLE_" + role.name.toString().toUpperCase()) }
            .collect(Collectors.toSet<GrantedAuthority>())
    }

    override fun findAll(): List<UserDto> {
        val users = ArrayList<UserDto>()
        userDao!!.findAll().iterator().forEachRemaining { user -> users.add(user.toUsersDto()) }
        return users
    }

    override fun findOne(id: Long): Users {
        return userDao!!.findById(id).get()
    }

    override fun delete(id: Long) {
        userDao!!.deleteById(id)
    }

    override fun save(userDto: UserDto): UserDto {
        val userWithDuplicateUsername = userDao!!.findByUsername(userDto.username!!)
        if (userWithDuplicateUsername.isPresent && userDto.id !== userWithDuplicateUsername!!.get().id) {
            log.error(String.format("Duplicate username %", userDto.username))
            throw RuntimeException("Duplicate username.")
        }
        val userWithDuplicateEmail = userDao!!.findByEmail(userDto.email!!)
        if (userWithDuplicateEmail.isPresent && userDto.id !== userWithDuplicateEmail!!.get().id) {
            log.error(String.format("Duplicate email %", userDto.email))
            throw RuntimeException("Duplicate email.")
        }
        val user = Users()
        user.email = (userDto.email)
        user.username = (userDto.username)
        user.password = (passwordEncoder!!.encode(userDto.password!!))
        val roleTypes = ArrayList<RoleType>()
        userDto.role!!.stream().map { role -> roleTypes.add(RoleType.valueOf(role)) }
        user.roles = (roleDao!!.find(userDto.role!!))
        userDao!!.save(user)
        return userDto
    }

    companion object {

        private val log = LoggerFactory.getLogger(UserServiceImpl::class.java)
    }
}