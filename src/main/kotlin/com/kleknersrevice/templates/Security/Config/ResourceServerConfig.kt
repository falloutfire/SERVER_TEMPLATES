package com.kleknersrevice.templates.Security.Config

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler
import org.springframework.security.oauth2.provider.token.DefaultTokenServices
import org.springframework.security.oauth2.provider.token.TokenStore


/**
 * Resources Server Configuration
 * предоставляет доступ к ресурсам, но толькое если клиент авторизован
 * Нет настроек защиты конечных точек, так как используется защита на
 * уровне методов с аннотациями @Secured
 */
@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
class ResourceServerConfig(private val tokenServices: DefaultTokenServices, private val tokenStore: TokenStore) :
    ResourceServerConfigurerAdapter() {

    private val RESOURCE_ID = "resource_id"

    override fun configure(resources: ResourceServerSecurityConfigurer) {
        resources.resourceId(RESOURCE_ID)
            .tokenServices(tokenServices)
            .tokenStore(tokenStore)//.stateless(false)
    }

    override fun configure(http: HttpSecurity) {
        http.anonymous().disable()
            .authorizeRequests()
            .antMatchers("/admin/**").access("hasRole('ADMIN')")
            .and().exceptionHandling().accessDeniedHandler(OAuth2AccessDeniedHandler())
    }
}