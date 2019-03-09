package com.kleknersrevice.templates.Security.Config

import com.kleknersrevice.templates.Entity.Users
import com.kleknersrevice.templates.Service.Impl.TokenNotFoundException
import com.kleknersrevice.templates.Service.TokenBlackListService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.oauth2.common.OAuth2AccessToken
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer
import org.springframework.security.oauth2.provider.OAuth2Authentication
import org.springframework.security.oauth2.provider.TokenRequest
import org.springframework.security.oauth2.provider.token.DefaultTokenServices
import org.springframework.security.oauth2.provider.token.TokenStore
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore
import java.text.SimpleDateFormat
import java.util.*
import javax.annotation.Resource
import javax.security.sasl.AuthenticationException


/**
 * Authorization Server Configuration
 * отвечает за генерацию токенов авторизации
 * Присутствует конфигурация JWT token store, вместе кодом протокола OAUTH2 для
 * настройки client_id, client-secret и grant_type.
 */
@Configuration
@EnableAuthorizationServer
class AuthorizationServerConfig(
    private var authenticationManager: AuthenticationManager,
    private val blackListService: TokenBlackListService
) :
    AuthorizationServerConfigurerAdapter() {

    private val CLIENT_ID = "mobile_app_client"
    private val CLIENT_SECRET = "$2a$04$1VGGg98BkCSvSLs4RDSyUu8MrYf0jkY3dgCLAy8GHJe6QA4VAM/X2"
    private val GRANT_TYPE_PASSWORD = "password"
    private val AUTHORIZATION_CODE = "authorization_code"
    private val REFRESH_TOKEN = "refresh_token"
    private val IMPLICIT = "implicit"
    private val SCOPE_READ = "read"
    private val SCOPE_WRITE = "write"
    private val TRUST = "trust"
    private val ACCESS_TOKEN_VALIDITY_SECONDS = 1 * 60
    private val REFRESH_TOKEN_VALIDITY_SECONDS = 6 * 60

    override fun configure(endpoints: AuthorizationServerEndpointsConfigurer) {
        endpoints.authenticationManager(authenticationManager).accessTokenConverter(accessTokenConverter())
            .tokenStore(tokenStore()).tokenServices(tokenServices())
    }

    override fun configure(clients: ClientDetailsServiceConfigurer) {
        clients
            .inMemory()
            .withClient(CLIENT_ID)
            .secret(CLIENT_SECRET)
            .authorizedGrantTypes(GRANT_TYPE_PASSWORD, AUTHORIZATION_CODE, REFRESH_TOKEN, IMPLICIT)
            .scopes(SCOPE_READ, SCOPE_WRITE, TRUST)
            .accessTokenValiditySeconds(ACCESS_TOKEN_VALIDITY_SECONDS)
            .refreshTokenValiditySeconds(REFRESH_TOKEN_VALIDITY_SECONDS)
    }

    @Bean
    fun accessTokenConverter(): JwtAccessTokenConverter? {
        val converter = JwtAccessTokenConverter()
        converter.setSigningKey("as466gf")
        return converter
    }

    @Bean
    fun tokenStore(): TokenStore = JwtTokenStore(accessTokenConverter())

    @Bean
    @Primary
    fun tokenServices(): DefaultTokenServices {
        val tokenService = CustomTokenService(this!!.blackListService!!)
        //val tokenService = DefaultTokenServices()
        tokenService.setTokenStore(tokenStore())
        tokenService.setSupportRefreshToken(true)
        tokenService.setTokenEnhancer(accessTokenConverter())
        return tokenService
    }

    open class CustomTokenService(private val blackListService: TokenBlackListService) : DefaultTokenServices() {
        var logger = LoggerFactory.getLogger(CustomTokenService::class.java)

        @Throws(AuthenticationException::class)
        override fun createAccessToken(authentication: OAuth2Authentication): OAuth2AccessToken {
            val token = super.createAccessToken(authentication)
            val account = authentication.principal as Users
            val jti = token.additionalInformation["jti"] as String

            /*val services = blackListService as ConsumerTokenServices

            blackListService.getAllTokensByUserId(account.id!!).forEach {
                if (it.accessToken != null) {
                    services.revokeToken(it.accessToken)
                }
            }*/

            blackListService.addToEnableList(
                account.id!!,
                jti,
                token.expiration.time
            )
            return token
        }


        @Throws(AuthenticationException::class)
        override fun refreshAccessToken(refreshTokenValue: String, tokenRequest: TokenRequest): OAuth2AccessToken? {
            logger.info("refresh token:$refreshTokenValue")
            val jti = tokenRequest.requestParameters["jti"]
            try {
                if (jti != null)
                    if (blackListService.isBlackListed(jti)!!) return null

                val token = super.refreshAccessToken(refreshTokenValue, tokenRequest)
                blackListService.addToBlackList(jti!!)
                return token
            } catch (e: TokenNotFoundException) {
                e.printStackTrace()
                return null
            }

        }
    }

}


/*@Configuration
@EnableScheduling
class ScheduledTasks {

    @Resource(name = "tokenServices")
    lateinit var tokenServices: DefaultTokenServices

    @Scheduled(fixedRate = 6000)
    fun reportCurrentTime() {

        //val services = tokenServices as DefaultTokenServices
        //tokenServices.
        println("The time is now " + dateFormat.format(Date()))
    }

    companion object {

        private val dateFormat = SimpleDateFormat("HH:mm:ss")
    }
}*/
