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
/*
@Configuration
@EnableAuthorizationServer
class AuthorizationServerConfig(
    private var authenticationManager: AuthenticationManager
) :
    AuthorizationServerConfigurerAdapter() {

    @Value("\${spring.datasource.url}")
    private var datasourceUrl: String? = null

    @Value("\${spring.datasource.driver-class-name}")
    private var dbDriverClassName: String? = null

    @Value("\${spring.datasource.username}")
    private var dbUsername: String? = null

    @Value("\${spring.datasource.password}")
    private var dbPassword: String? = null

    private val CLIENT_ID = "mobile_app_client"
    private val CLIENT_SECRET = "$2a$04$2R.wNqw3EYqh0tqobaknAO00ZNUDPqMn7Y0kWSjXqtQm8EW012wk."
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
        endpoints.authenticationManager(authenticationManager)
            */
/*.accessTokenConverter(accessTokenConverter())*//*

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
    fun tokenStore(): TokenStore =
    //JwtTokenStore(accessTokenConverter())
        JdbcTokenStore(dataSource())


    private fun dataSource(): DataSource? {
        val dataSource = DriverManagerDataSource()
        dataSource.setDriverClassName(dbDriverClassName!!)
        dataSource.url = datasourceUrl
        dataSource.username = dbUsername
        dataSource.password = dbPassword
        return dataSource
    }

    @Bean
    @Primary
    fun tokenServices(): DefaultTokenServices {
        //val tokenService = CustomTokenService(this.blackListService)
        val tokenService = DefaultTokenServices()
        tokenService.setTokenStore(tokenStore())
        tokenService.setSupportRefreshToken(true)
        //tokenService.setTokenEnhancer(accessTokenConverter())
        return tokenService
    }

}*/

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

