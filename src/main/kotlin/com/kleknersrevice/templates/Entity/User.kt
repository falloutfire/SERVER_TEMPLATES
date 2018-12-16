package com.kleknersrevice.templates.Entity

import javax.persistence.*

@Entity
@Table(name = "Users")
class User() {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long = 0
    @Column(name = "Username")
    var username: String = ""
    @Column(name = "Password")
    var password: String = ""
    @Column(name = "Authorities")
    var authorities: Role = Role.USER
    @Column(name = "Enabled")
    var enabled: Boolean = true
    @Column(name = "CredentialsNonExpired")
    var credentialsNonExpired: Boolean = true
    @Column(name = "AccountNonExpired")
    var accountNonExpired: Boolean = true
    @Column(name = "AccountNonLocked")
    var accountNonLocked: Boolean = true

}