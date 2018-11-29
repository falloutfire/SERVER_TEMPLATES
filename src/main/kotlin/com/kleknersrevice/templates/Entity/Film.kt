package com.kleknersrevice.templates.Entity

import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import javax.persistence.*

@Entity
@Table(name = "Film")
class Film(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long,
    @Column(name = "Film_Name")
    var name: String,
    @Column(name = "Color")
    var color: String,
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "Chemical_Type_ID", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    var chemicalType: ChemicalType
)