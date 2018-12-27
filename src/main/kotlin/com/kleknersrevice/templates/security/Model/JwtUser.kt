package com.kleknersrevice.templates.security.Model

import javax.persistence.*

@Entity
@Table(name = "users")
class JwtUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0
    @Column(name = "username")
    var username: String = ""
    @Column(name = "password")
    var password: String = ""
    @Column(name = "role")
    var role: String = ""
}