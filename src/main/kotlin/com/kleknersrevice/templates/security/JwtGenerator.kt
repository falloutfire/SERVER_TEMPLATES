package com.kleknersrevice.templates.security

import com.kleknersrevice.templates.Repository.UsersRepository
import com.kleknersrevice.templates.security.Model.JwtUser
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.stereotype.Component

@Component
class JwtGenerator(private var usersRepository: UsersRepository){

    private val SECRET_CODE = "jQbaJ4PePrMjbV6TzKdUMhQsaBJZWTuVrGllfO9pjDUhQ78X0ixwWBFOcr6myYbsa24q14KgErjUTF4CmoiAq59IKTlOoMlMnkpc"

    fun generate(jwtUser: JwtUser): String? {

        usersRepository.findByUserName(jwtUser.username).let {
            if(it.isPresent) {
                if (it.get().password == jwtUser.password && it.get().role == jwtUser.role){
                    val claims = Jwts.claims()
                        .setSubject(jwtUser.username)
                    claims["userPassword"] = jwtUser.password
                    claims["role"] = jwtUser.role
                    return Jwts.builder()
                        .setClaims(claims)
                        .signWith(SignatureAlgorithm.HS512, SECRET_CODE)
                        .compact()
                }
            }
        }
        return null
    }
}