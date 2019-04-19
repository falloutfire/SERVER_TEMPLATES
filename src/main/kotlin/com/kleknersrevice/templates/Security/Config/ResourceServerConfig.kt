package com.kleknersrevice.templates.Security.Config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter
import java.util.*


/**
 * Resources Server Configuration
 * предоставляет доступ к ресурсам, но толькое если клиент авторизован
 * Нет настроек защиты конечных точек, так как используется защита на
 * уровне методов с аннотациями @Secured
 */


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


    @Bean
    fun corsFilterRegistrationBean(): FilterRegistrationBean<*> {
        val source = UrlBasedCorsConfigurationSource()
        val config = CorsConfiguration()
        config.applyPermitDefaultValues()
        config.allowCredentials = true
        config.allowedOrigins = Arrays.asList("*")
        config.allowedHeaders = Arrays.asList("*")
        config.allowedMethods = Arrays.asList("*")
        config.exposedHeaders = Arrays.asList("content-length")
        config.maxAge = 3600L
        source.registerCorsConfiguration("/**", config)
        val bean = FilterRegistrationBean(CorsFilter(source))
        bean.order = Ordered.HIGHEST_PRECEDENCE
        return bean
    }

}
