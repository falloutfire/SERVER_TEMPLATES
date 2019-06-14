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

class SignatureFormat(
    val description: String,
    val date: Date,
    var listDetails: String
) {

    companion object {
        fun format(sign: Signature): SignatureFormat {
            val list = sign.listDetails.map { "${it.firstAngle}.${it.secondAngle}" }
            return SignatureFormat(
                sign.description,
                sign.date,
                list.joinToString(prefix = "<", postfix = ">", separator = ":")
            )
        }
    }
}
