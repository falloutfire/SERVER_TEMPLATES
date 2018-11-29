package com.kleknersrevice.templates.Entity

import javax.persistence.*

@Entity
@Table(name = "OS")
class OS(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long,
    @Column(name = "OS_Name")
    var name: String,
    @Column(name = "Version")
    var version: Double
)