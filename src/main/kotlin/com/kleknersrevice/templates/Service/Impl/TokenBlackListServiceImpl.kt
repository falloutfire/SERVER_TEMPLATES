package com.kleknersrevice.templates.Service.Impl

import com.kleknersrevice.templates.Entity.TokenBlackList
import com.kleknersrevice.templates.Repository.TokenBlackListRepository
import com.kleknersrevice.templates.Service.TokenBlackListService
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service

@Service
class TokenBlackListServiceImpl(private val tokenBlackListRepository: TokenBlackListRepository) :
    TokenBlackListService {


    override fun getAllTokensByUserId(userId: Long): List<TokenBlackList> {
        return tokenBlackListRepository.queryAllByUserId(userId)
    }

    @Async
    @Throws(TokenNotFoundException::class)
    override fun setBlackListByUserId(userId: Long) {
        tokenBlackListRepository.queryAllByUserId(userId).forEach {
            it.isBlackListed = true
            tokenBlackListRepository.save(it)
        }
    }


    @Throws(TokenNotFoundException::class)
    override fun isBlackListed(jti: String): Boolean? {
        return tokenBlackListRepository.findByJti(jti).let {
            if (it.isPresent) {
                it.get().isBlackListed
            } else {
                throw TokenNotFoundException(jti)
            }
        }
    }

    @Async
    override fun addToEnableList(userId: Long, jti: String, expired: Long, accessToken: String, refreshToken: String) {
        tokenBlackListRepository.queryAllByUserIdAndIsBlackListedFalse(userId).forEach { token ->
            token.isBlackListed = true
            tokenBlackListRepository.save(token)
        }

        val tokenBlackList = TokenBlackList(userId, jti, expired, accessToken, refreshToken)
        tokenBlackList.isBlackListed = false
        tokenBlackListRepository.save(tokenBlackList)

    }

    @Async
    @Throws(TokenNotFoundException::class)
    override fun addToBlackList(jti: String) {
        tokenBlackListRepository.findByJti(jti).let {
            if (it.isPresent) {
                it.get().isBlackListed = true
                tokenBlackListRepository.save(it.get())
            }
        }

    }

}

class TokenNotFoundException(jti: String) : Exception() {
    override var message: String = String.format("Token with jti[%s] not found.", jti)
}