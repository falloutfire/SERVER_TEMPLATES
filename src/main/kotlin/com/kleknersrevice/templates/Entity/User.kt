package com.kleknersrevice.templates.Entity

import javax.persistence.*

@Entity
@Table(name = "app_user")
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,

    @Column(name = "username")
    var username: String? = null,

    @Column(name = "password")
    var password: String? = null,

    @Column(name = "first_name")
    var firstName: String? = null,

    @Column(name = "last_name")
    var lastName: String? = null,

    @Column(name = "email")
    var email: String? = null,

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
)

class UserDto(
    var id: Long? = null,
    var username: String? = null,
    var firstName: String? = null,
    var lastName: String? = null,
    var email: String? = null,
    var roles: List<Role>? = null
) {

    companion object {
        fun toUserDto(user: User): UserDto {
            return UserDto(user.id, user.username, user.firstName, user.lastName, user.email, user.roles)
        }
    }
}