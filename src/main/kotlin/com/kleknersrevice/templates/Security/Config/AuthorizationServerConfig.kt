package com.kleknersrevice.templates.Security.Config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer
import org.springframework.security.oauth2.provider.token.TokenStore
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore

/**
 * Authorization Server Configuration
 * отвечает за генерацию токенов авторизации
 * Присутствует конфигурация JWT token store, вместе кодом протокола OAUTH2 для
 * настройки client_id, client-secret и grant_type.
 */
@Configuration
@EnableAuthorizationServer
open class AuthorizationServerConfig(var authenticationManager: AuthenticationManager) :
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

    override fun configure(endpoints: AuthorizationServerEndpointsConfigurer) {
        endpoints.authenticationManager(authenticationManager).accessTokenConverter(accessTokenConverter())
    }

    override fun configure(clients: ClientDetailsServiceConfigurer) {
        clients
            .inMemory()
            .withClient(CLIENT_ID)
            .secret(CLIENT_SECRET)
            .authorizedGrantTypes(GRANT_TYPE_PASSWORD, AUTHORIZATION_CODE, REFRESH_TOKEN, IMPLICIT)
            .scopes(SCOPE_READ, SCOPE_WRITE, TRUST)
    }

    @Bean
    open fun accessTokenConverter(): JwtAccessTokenConverter? {
        val converter = JwtAccessTokenConverter()
        converter.setSigningKey("as466gf")
        return converter
    }

    @Bean
    open fun tokenStore(): TokenStore = JwtTokenStore(accessTokenConverter())

}