package com.kleknersrevice.templates.Entity

import javax.persistence.*

@Entity
@Table(name = "app_role")
class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column(name = "role_name")
    var roleName: String? = null

    @Column(name = "description")
    var description: String? = null

    companion object {
        private val serialVersionUID = 1L
    }
}
