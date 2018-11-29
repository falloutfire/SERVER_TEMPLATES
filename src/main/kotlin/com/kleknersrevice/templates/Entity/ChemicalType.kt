package com.kleknersrevice.templates.Entity

import javax.persistence.*

@Entity
@Table(name = "Chemical_Type")
class ChemicalType(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long,
    @Column(name = "Type")
    var type: String
)