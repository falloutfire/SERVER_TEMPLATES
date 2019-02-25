package com.kleknersrevice.templates.Service.Impl

import com.kleknersrevice.templates.Entity.RoleType
import com.kleknersrevice.templates.Entity.UserDto
import com.kleknersrevice.templates.Entity.Users
import com.kleknersrevice.templates.Repository.RoleRepository
import com.kleknersrevice.templates.Repository.UserRepository
import com.kleknersrevice.templates.Service.UserService
import org.slf4j.LoggerFactory
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*
import java.util.stream.Collectors


@Transactional
@Service(value = "userService")
open class UserServiceImpl(
    private val bCryptPasswordEncoder: BCryptPasswordEncoder,
    private val userRepository: UserRepository,
    private val roleRepository: RoleRepository
) : UserDetailsService, UserService {

    private val log = LoggerFactory.getLogger(UserServiceImpl::class.java)

    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByUsername(username).let {
            if (!it.isPresent) {
                log.error("Invalid username or password.")
                throw UsernameNotFoundException("Invalid username or password.")
            } else {
                it.get()
            }
        }
        val grantedAuthority: Set<GrantedAuthority> = getAuthorities(user)
        return org.springframework.security.core.userdetails.User(user.username, user.password, grantedAuthority);
    }

    private fun getAuthorities(user: Users): Set<GrantedAuthority> {
        val roleByUser = user.roles
        return roleByUser!!.stream()
            .map { role -> SimpleGrantedAuthority("ROLE_" + role.name.toString().toUpperCase()) }
            .collect(Collectors.toSet<GrantedAuthority>())
    }

    override fun save(userDto: UserDto): UserDto {
        userRepository.findByUsername(userDto.username!!).let {
            if (it.isPresent && userDto.id != it.get().id) {
                log.error("Duplicate username $it")
                throw RuntimeException("Duplicate username.")
            }
        }

        userRepository.findByEmail(userDto.email!!).let {
            if (it.isPresent && userDto.id != it.get().id) {
                log.error("Duplicate email $it")
                throw RuntimeException("Duplicate email.")
            }
        }

        val user = Users()
        user.username = userDto.username
        user.password = bCryptPasswordEncoder.encode(userDto.password)
        user.email = userDto.email
        val roleTypes = ArrayList<RoleType>()
        userDto.role!!.map { role -> roleTypes.add(RoleType.valueOf(role)) }
        user.roles = roleRepository.find(userDto.role!!)
        userRepository.save(user)
        return userDto
    }

    override fun findAll(): List<UserDto> = userRepository.findAll().map { it.toUsersDto() }

    override fun findOne(id: Long): Users = userRepository.findById(id).get()

    override fun delete(id: Long) = userRepository.deleteById(id)
}