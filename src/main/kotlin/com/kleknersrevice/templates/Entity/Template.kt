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
    @Column(name = "name")
    var name: String,
    /*@Column(name = "y")
    var ycolor: String,
    @Column(name = "z")
    var zcolor: String,*/
    @Column(name = "Color")
    var color: String,
    /*@Column(name = "Delta_X")
    var xdelta: String,
    @Column(name = "Delta_Y")
    var ydelta: String,
    @Column(name = "Delta_Z")
    var zdelta: String,
    @Column(name = "Circularity")
    var circularity: Double,
    @Column(name = "Radius")
    var radius: Double,*/

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
