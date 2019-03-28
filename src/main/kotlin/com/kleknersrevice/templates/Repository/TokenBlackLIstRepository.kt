package com.kleknersrevice.templates.Repository

import com.kleknersrevice.templates.Entity.TokenBlackList
import org.springframework.data.repository.Repository
import java.util.*


interface TokenBlackListRepository : Repository<TokenBlackList, Long> {
    fun findByJti(jti: String): Optional<TokenBlackList>
    fun queryAllByUserIdAndIsBlackListedFalse(userId: Long?): List<TokenBlackList>
    fun save(tokenBlackList: TokenBlackList)
    fun deleteAllByUserIdAndExpiresBefore(userId: Long?, date: Long?): List<TokenBlackList>
    fun queryAllByUserId(userId: Long?): List<TokenBlackList>
}