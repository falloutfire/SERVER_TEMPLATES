package com.kleknersrevice.templates.Entity

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import javax.persistence.*

@Entity
@Table(name = "Users")
class Users : UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    var id: Long? = null
    @Column(name = "USERNAME", unique = true)
    private var username: String? = ""
    @Column(name = "PASSWORD")
    private var password: String? = ""
    @Column(name = "EMAIL")
    var email: String? = null
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "Users_ROLES", joinColumns = [JoinColumn(name = "USER_ID")],
        inverseJoinColumns = [JoinColumn(name = "ROLE_ID")]
    )
    var roles: Set<Roles>? = null
    private val accountNonExpired: Boolean = true
    private val accountNonLocked: Boolean = true
    private val credentialsNonExpired: Boolean = true
    private val enabled: Boolean = true

    override fun isAccountNonExpired(): Boolean {
        return accountNonExpired
    }

    override fun isAccountNonLocked(): Boolean {
        return accountNonLocked
    }

    override fun isCredentialsNonExpired(): Boolean {
        return credentialsNonExpired
    }

    override fun isEnabled(): Boolean {
        return enabled
    }

    fun grantAuthority(authority: Roles) {
        roles!!.map { authority }
        /*roles!!.add(authority)*/
    }

    override fun getAuthorities(): List<GrantedAuthority> {
        val authorities = ArrayList<GrantedAuthority>()
        roles!!.forEach { role -> authorities.add(SimpleGrantedAuthority("ROLE_" + role.name.toString().toUpperCase())) }
        return authorities
    }

    override fun getUsername(): String {
        return this.username!!
    }

    override fun getPassword(): String {
        return this.password!!
    }

    fun setUsername(username: String?) {
        this.username = username
    }

    fun setPassword(password: String?) {
        this.password = password
    }

    fun toUsersDto(): UserDto {
        val userDto = UserDto()
        userDto.id = this.id
        userDto.email = this.email
        userDto.username = this.username
        userDto.role = this.roles!!.map { role -> role.name.toString() }
        return userDto
    }

}


data class UserDto(
    var id: Long? = null,
    var username: String? = null,
    var password: String? = null,
    var email: String? = null,
    var role: List<String>? = null
) {

    override fun toString(): String {
        return "UserDto{" +
                "id=" + id +
                ", username='" + username + '\'' +
                '}'
    }
}