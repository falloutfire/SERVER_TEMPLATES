package com.kleknersrevice.templates.Entity

import javax.persistence.*

@Entity
@Table(name = "Device")
class Device(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long,
    @Column(name = "Device_Name")
    var name: String,
    /*@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "OS_ID", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    var os: OS,*/
    @Column(name = "Resolution")
    var resolution: String,
    @Column(name = "mp")
    var mp: Long,
    @Column(name = "Focus")
    var focus: Double,
    @Column(name = "Optic_Stabilization")
    var stabilization: Boolean
)
