package com.kleknersrevice.templates.Entity

import javax.persistence.*

@Entity
@Table(name = "Luminophore")
class Luminophore(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long,
    @Column(name = "Luminophore_Name")
    var name: String,
    @Column(name = "Color")
    var color: String,
    @Column(name = "Size")
    var size: Double,
    @Column(name = "Brightness")
    var brightness: Int,
    @Column(name = "Afterglow")
    var afterglow: Int,
    @Column(name = "Activation_Time")
    var activationTime: Int
)
