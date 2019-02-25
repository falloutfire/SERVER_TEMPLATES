package com.kleknersrevice.templates.Security.Config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import javax.annotation.Resource


/**
 *
 */

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
open class SecurityConfig : WebSecurityConfigurerAdapter() {

    @Resource(name = "userService")
    lateinit var userDetailsService: UserDetailsService

    @Bean
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }

    @Bean
    open fun encoder(): BCryptPasswordEncoder = BCryptPasswordEncoder()

    override fun configure(http: HttpSecurity) {
        http
            .csrf().disable()
            .anonymous().disable()
            .authorizeRequests()
            .antMatchers("/api-docs/**").permitAll()
    }

    @Autowired
    fun globalUserDetails(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(userDetailsService)
            .passwordEncoder(encoder())
    }
}