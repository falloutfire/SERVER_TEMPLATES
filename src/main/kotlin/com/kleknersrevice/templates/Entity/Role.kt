package com.kleknersrevice.templates.Entity

import java.io.Serializable
import javax.persistence.*

@Entity
@Table(name = "Roles")
data class Roles(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    var id: Long,

    @Enumerated(EnumType.STRING)
    @Column(name = "NAME")
    var name: RoleType,
    @Column(name = "DESCRIPTION")
    var description: String,
    @Column(name = "CREATED_ON")
    var createdOn: Long,
    @Column(name = "MODIFIED_ON")
    var modifiedOn: Long
) : Serializable

enum class RoleType {

    ADMIN, USER_CREATE, USER_UPDATE, USER
}