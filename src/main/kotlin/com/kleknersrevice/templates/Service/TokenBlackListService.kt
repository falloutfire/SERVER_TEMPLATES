package com.kleknersrevice.templates.Service

import com.kleknersrevice.templates.Entity.TokenBlackList

interface TokenBlackListService {
    fun isBlackListed(jti: String): Boolean?
    fun addToEnableList(userId: Long, jti: String, expired: Long, accessToken: String, refreshToken: String)
    fun addToBlackList(jti: String)
    fun setBlackListByUserId(userId: Long)
    fun getAllTokensByUserId(userId: Long): List<TokenBlackList>
}