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
    var colorScheme: ColorScheme? = null,
    @Column(name = "name")
    var name: String? = null,
    @Column(name = "Lower_Color_Bound")
    var lowerColorBound: Int? = null,
    @Column(name = "Points")
    var points: Int? = null,
    @Column(name = "Min_Radius")
    var minRadius: String? = null,
    @Column(name = "Triangles")
    var triangles: Int? = null,
    @Column(name = "Max_Radius")
    var maxRadius: String? = null,
    @Column(name = "Max_Circularity")
    var maxCircularity: String? = null,
    @Column(name = "Circularity")
    var circularity: Double? = null,
    @Column(name = "Radius")
    var radius: Double? = null,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "Device_ID", nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    var device: Device? = null,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "Film_ID", nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    var film: Film? = null,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "Luminophore_ID", nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    var luminophore: Luminophore? = null
)

class TemplateContext(var device: Device, var film: Film, var luminophore: Luminophore)
