package com.kleknersrevice.templates.Entity

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import javax.persistence.*

@Entity
@Table(name = "app_user")
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,

    @Column(name = "username")
    var userName: String? = null,

    @Column(name = "password")
    var userPassword: String? = null,

    @Column(name = "first_name")
    var firstName: String? = null,

    @Column(name = "email")
    var email: String? = null,

    @Column(name = "last_name")
    var lastName: String? = null,

    /**
     * Roles are being eagerly loaded here because
     * they are a fairly small collection of items for this example.
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_role",
        joinColumns = [JoinColumn(name = "user_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "role_id", referencedColumnName = "id")]
    )
    var roles: List<Role>? = null
) : UserDetails {

    override fun getAuthorities(): Collection<GrantedAuthority> {
        return ArrayList<GrantedAuthority>().also {
            it.addAll(this.roles!!.map { role -> SimpleGrantedAuthority(role.roleName) })
        }
    }

    override fun isEnabled(): Boolean {
        return true
    }

    override fun getUsername(): String {
        return this.userName!!
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun getPassword(): String {
        return this.userPassword!!
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }
}

class UserDto(
    var id: Long? = null,
    var userName: String? = null,
    var firstName: String? = null,
    var email: String? = null,
    var lastName: String? = null,
    var roles: List<Role>? = null,
    var password: String? = null
) {

    fun toUser(): User {
        return User(
            id,
            userName,
            password,
            firstName,
            email,
            lastName,
            roles
        )
    }

    companion object {
        fun toUserDto(user: User): UserDto {
            return UserDto(
                user.id,
                user.username,
                user.firstName,
                user.email,
                user.lastName,
                user.roles,
                user.password
            )
        }
    }
}