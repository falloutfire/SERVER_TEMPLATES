package com.kleknersrevice.templates.Security.Config

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler


/**
 * Resources Server Configuration
 * предоставляет доступ к ресурсам, но толькое если клиент авторизован
 * Нет настроек защиты конечных точек, так как используется защита на
 * уровне методов с аннотациями @Secured
 */
@Configuration
@EnableResourceServer
open class ResourceServerConfig : ResourceServerConfigurerAdapter() {

    private val RESOURCE_ID = "resource_id"

    override fun configure(resources: ResourceServerSecurityConfigurer) {
        resources.resourceId(RESOURCE_ID).stateless(false)
    }

    override fun configure(http: HttpSecurity) {
        http.anonymous().disable()
            .authorizeRequests()
            .antMatchers("/admin/**").access("hasRole('ADMIN')")
            .and().exceptionHandling().accessDeniedHandler(OAuth2AccessDeniedHandler())
    }
}