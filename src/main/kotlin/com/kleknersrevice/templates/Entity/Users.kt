package com.kleknersrevice.templates.Entity

import java.util.stream.Collectors
import javax.persistence.*


@Entity
@Table(name = "Users")
data class Users(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    var id: Long? = null,
    @Column(name = "USERNAME")
    var username: String? = null,
    @Column(name = "PASSWORD")
    var password: String? = null,
    @Column(name = "EMAIL")
    var email: String? = null,
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "User_ROLES", joinColumns = [JoinColumn(name = "USER_ID")],
        inverseJoinColumns = [JoinColumn(name = "ROLE_ID")]
    )
    var roles: Set<Role>? = null
) {

    fun toUsersDto(): UserDto {
        val userDto = UserDto()
        userDto.id = this.id
        userDto.email = this.email
        userDto.username = this.username
        userDto.role = this.roles!!.map { role -> role.name.toString()}
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