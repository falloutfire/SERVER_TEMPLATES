package com.kleknersrevice.templates.Entity

import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "Signature")
class Signature(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long,
    @Column(name = "Description")
    val description: String,
    @Column(name = "Date")
    val date: Date,
    @OneToMany(mappedBy = "signature", fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    var listDetails: List<SignatureDetails>
)
