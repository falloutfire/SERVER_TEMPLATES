package com.kleknersrevice.templates.Security.Config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter


/**
 * Resources Server Configuration
 * предоставляет доступ к ресурсам, но толькое если клиент авторизован
 * Нет настроек защиты конечных точек, так как используется защита на
 * уровне методов с аннотациями @Secured
 */
/*
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
}*/*/

@Configuration
@EnableResourceServer
class ResourceServerConfig : ResourceServerConfigurerAdapter() {

    @Autowired
    private val tokenServices: ResourceServerTokenServices? = null

    @Autowired
    private val accessTokenConverter: JwtAccessTokenConverter? = null

    @Throws(Exception::class)
    override fun configure(resources: ResourceServerSecurityConfigurer?) {
        resources!!.tokenServices(tokenServices)
    }

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http
            .requestMatchers()
            .and()
            .authorizeRequests()
            .antMatchers("/actuator/**", "/api-docs/**", "/oauth/*").permitAll()
            .antMatchers("/jwttest/**").authenticated()
    }

}
