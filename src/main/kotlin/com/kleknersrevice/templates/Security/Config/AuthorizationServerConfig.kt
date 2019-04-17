package com.kleknersrevice.templates.Security.Config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer
import org.springframework.security.oauth2.provider.token.TokenStore
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter


/**
 * Authorization Server Configuration
 * отвечает за генерацию токенов авторизации
 * Присутствует конфигурация JWT token store, вместе кодом протокола OAUTH2 для
 * настройки client_id, client-secret и grant_type.
 */
@Configuration
@EnableAuthorizationServer
class AuthorizationServerConfig : AuthorizationServerConfigurerAdapter() {

    @Autowired
    private val tokenStore: TokenStore? = null

    @Autowired
    private val accessTokenConverter: JwtAccessTokenConverter? = null

    @Autowired
    private val authenticationManager: AuthenticationManager? = null

    @Qualifier("userService")
    @Autowired
    private val userDetailsService: UserDetailsService? = null

    @Autowired
    private val jdbcTemplate: JdbcTemplate? = null

    @Throws(Exception::class)
    override fun configure(configurer: ClientDetailsServiceConfigurer?) {
        configurer!!
            .jdbc(jdbcTemplate!!.dataSource)
    }


    @Throws(Exception::class)
    override fun configure(endpoints: AuthorizationServerEndpointsConfigurer?) {
        endpoints!!.tokenStore(tokenStore)
            .reuseRefreshTokens(false)
            .accessTokenConverter(accessTokenConverter)
            .authenticationManager(authenticationManager)
            .userDetailsService(userDetailsService)
    }


    @Throws(Exception::class)
    override fun configure(oauthServer: AuthorizationServerSecurityConfigurer?) {
        oauthServer!!.tokenKeyAccess("hasAuthority('ROLE_TRUSTED_CLIENT')")
            .checkTokenAccess("hasAuthority('ROLE_TRUSTED_CLIENT')")
    }


}

