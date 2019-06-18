package com.kleknersrevice.templates.Entity

import java.io.Serializable
import javax.persistence.*

@Entity
@Table(name = "app_role")
class Role : Serializable {
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
