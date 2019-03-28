package com.kleknersrevice.templates.Entity

import javax.persistence.Entity
import javax.persistence.Id

@Entity
class TokenBlackList() {

    @Id
    var jti: String? = null
    var userId: Long? = null
    var expires: Long? = null
    var isBlackListed: Boolean? = null
    var accessToken: String? = null
    var refreshToken: String? = null

    constructor(userId: Long?, jti: String, expires: Long?, accessToken: String?, refreshToken: String?) : this() {
        this.jti = jti
        this.userId = userId
        this.expires = expires
        this.accessToken = accessToken
        this.refreshToken = refreshToken
    }
}