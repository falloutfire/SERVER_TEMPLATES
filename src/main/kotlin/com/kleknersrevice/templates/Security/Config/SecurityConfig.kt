package com.kleknersrevice.templates.Security.Config

import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken
import org.springframework.security.oauth2.common.OAuth2AccessToken
import org.springframework.security.oauth2.provider.token.DefaultTokenServices
import org.springframework.security.oauth2.provider.token.TokenStore
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter
import javax.sql.DataSource

/**
 * SecurityConfig
 */

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class SecurityConfig : WebSecurityConfigurerAdapter() {

    @Value("\${security.signing-key}")
    private val signingKey: String? = null

    @Value("\${security.encoding-strength}")
    private val encodingStrength: Int? = null

    @Value("\${security.security-realm}")
    private val securityRealm: String? = null

    @Autowired
    private val jdbcTemplate: JdbcTemplate? = null

    @Bean
    @Throws(Exception::class)
    override fun authenticationManager(): AuthenticationManager {
        return super.authenticationManager()
    }

    @Bean
    fun passwordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .httpBasic()
            .realmName(securityRealm)
            .and()
            .csrf()
            .disable()

    }

    @Bean
    fun accessTokenConverter(): JwtAccessTokenConverter {
        val converter = JwtAccessTokenConverter()
        converter.setSigningKey(signingKey!!)
        return converter
    }


    @Bean
    fun tokenStore(): TokenStore {
        //return new JdbcTokenStore(jdbcTemplate.getDataSource());
        return CustomJdbcTokenStore(jdbcTemplate!!.dataSource!!)
    }

    @Bean
    @Primary
    //Making this primary to avoid any accidental duplication with another token service instance of the same name
    fun tokenServices(): DefaultTokenServices {
        val defaultTokenServices = DefaultTokenServices()
        defaultTokenServices.setTokenStore(tokenStore())
        defaultTokenServices.setSupportRefreshToken(true)
        return defaultTokenServices
    }

    @Bean
    fun corsFilter(): CorsFilter {
        val source = UrlBasedCorsConfigurationSource()
        val config = CorsConfiguration()
        config.allowCredentials = true
        config.addAllowedOrigin("*")
        config.addAllowedHeader("*")
        config.addAllowedMethod("*")
        source.registerCorsConfiguration("/**", config)
        return CorsFilter(source)
    }


}


class CustomJdbcTokenStore(dataSource: DataSource) : JdbcTokenStore(dataSource) {

    override fun readAccessToken(tokenValue: String): OAuth2AccessToken? {
        var accessToken: OAuth2AccessToken? = null

        try {
            accessToken = DefaultOAuth2AccessToken(tokenValue)
        } catch (e: EmptyResultDataAccessException) {
            if (LOG.isInfoEnabled) {
                LOG.info("Failed to find access token for token $tokenValue")
            }
        } catch (e: IllegalArgumentException) {
            LOG.warn("Failed to deserialize access token for $tokenValue", e)
            removeAccessToken(tokenValue)
        }

        return accessToken
    }

    companion object {
        private val LOG = LogFactory.getLog(JdbcTokenStore::class.java)
    }
}

