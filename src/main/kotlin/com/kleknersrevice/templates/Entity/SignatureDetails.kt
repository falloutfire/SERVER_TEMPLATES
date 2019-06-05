package com.kleknersrevice.templates.Entity

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
@Table(name = "Signature_Details")
class SignatureDetails(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long,
    @Column(name = "First_Angle")
    var firstAngle: Double,
    @Column(name = "Second_Angle")
    var secondAngle: Double,
    @ManyToOne
    @JoinColumn
    @JsonIgnore
    var signature: Signature?
)
