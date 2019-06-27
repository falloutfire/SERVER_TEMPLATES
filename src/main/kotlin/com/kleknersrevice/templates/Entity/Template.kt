package com.kleknersrevice.templates.Entity

import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import javax.persistence.*

@Entity
@Table(name = "Template")
class Template(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val Id: Long,
    @Enumerated(EnumType.STRING)
    var ColorScheme: ColorScheme,
    @Column(name = "name")
    var Name: String,
    @Column(name = "Lower_Color_Bound")
    var LowerColorBound: Int,
    @Column(name = "Points")
    var Points: Int,
    @Column(name = "Min_Radius")
    var MinRadius: String,
    @Column(name = "Triangles")
    var Triangles: Int,
    @Column(name = "Max_Radius")
    var MaxRadius: String,
    @Column(name = "Max_Circularity")
    var MaxCircularity: String,
    @Column(name = "Circularity")
    var Circularity: Double,
    @Column(name = "Radius")
    var Radius: Double,

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

class TemplateContext(var device: Device, var film: Film, var luminophore: Luminophore)
