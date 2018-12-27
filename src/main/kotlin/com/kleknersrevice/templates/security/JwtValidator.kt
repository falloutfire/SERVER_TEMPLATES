package com.kleknersrevice.templates.security

import com.kleknersrevice.templates.Repository.UsersRepository
import com.kleknersrevice.templates.security.Model.JwtUser
import io.jsonwebtoken.Jwts
import org.springframework.stereotype.Component

@Component
class JwtValidator(private val usersRepository: UsersRepository) {

    private val SECRET_CODE = "jQbaJ4PePrMjbV6TzKdUMhQsaBJZWTuVrGllfO9pjDUhQ78X0ixwWBFOcr6myYbsa24q14KgErjUTF4CmoiAq59IKTlOoMlMnkpc"

    fun validate(token: String): JwtUser? {

        var jwtUser: JwtUser? = null
        try {
            val body = Jwts.parser()
                .setSigningKey(SECRET_CODE)
                .parseClaimsJws(token)
                .body

            jwtUser = JwtUser()

            usersRepository.findByUserName(body.subject).let {
                if(it.isPresent) {
                    if (it.get().password == (body["userPassword"] as String) && it.get().role == (body["role"] as String)){
                        jwtUser.username = body.subject
                        jwtUser.password = /*java.lang.Long.parseLong(*/body["userPassword"] as String/*)*/
                        jwtUser.role = body["role"] as String
                    }
                }
            }

        } catch (e: Exception) {
            println(e)
        }

        return jwtUser
    }
}