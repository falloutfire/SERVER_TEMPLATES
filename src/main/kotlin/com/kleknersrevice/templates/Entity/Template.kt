package com.kleknersrevice.templates.Entity

import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import javax.persistence.*

@Entity
@Table(name = "Template")
class Template(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long,
    @Enumerated(EnumType.STRING)
    var colorScheme: ColorScheme,
    @Column(name = "X")
    var xColor: Int,
    @Column(name = "Y")
    var yColor: Int,
    @Column(name = "Z")
    var zColor: Int,
    @Column(name = "Delta_X")
    var xDelta: Int,
    @Column(name = "Delta_Y")
    var yDelta: Int,
    @Column(name = "Delta_Z")
    var zDelta: Int,
    @Column(name = "Rounding")
    var rounding: Int,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "Device_ID", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    var device: Device,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "Film_ID", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    var film: Film,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "Luminophore_ID", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    var luminophore: Luminophore
)