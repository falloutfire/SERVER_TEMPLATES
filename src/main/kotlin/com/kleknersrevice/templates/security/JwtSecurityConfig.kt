package com.kleknersrevice.templates.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.ProviderManager
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
@Configuration
open class JwtSecurityConfig : WebSecurityConfigurerAdapter() {


    @Autowired
    private lateinit var authenticationProvider: JwtAuthenticationProvider
    @Autowired
    private lateinit var entryPoint: JwtAuthenticationEntryPoint

    @Bean
    public override fun authenticationManager(): AuthenticationManager {
        return ProviderManager(listOf<AuthenticationProvider>(authenticationProvider))
    }

    @Bean
    open fun authenticationTokenFilter(): JwtAuthenticationTokenFilter {
        val filter = JwtAuthenticationTokenFilter()
        filter.setAuthenticationManager(authenticationManager())
        filter.setAuthenticationSuccessHandler(JwtSuccessHandler())
        return filter
    }


    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {

        http.csrf().disable().authorizeRequests()
            .antMatchers("bd_template/**/**").hasRole("admin")
            .antMatchers("bd_template/**/").hasRole("admin")
            .antMatchers(HttpMethod.GET, "bd_template/**/").hasAnyRole( "user", "admin")
            .antMatchers(HttpMethod.POST, "bd_template/**/").hasRole("admin")
            .antMatchers(HttpMethod.PUT, "bd_template/**/").hasRole("admin")
            .antMatchers(HttpMethod.DELETE, "bd_template/**/**").hasRole("admin")
            .antMatchers("/").permitAll()
            .antMatchers(HttpMethod.POST, "/login").permitAll()
            .anyRequest().authenticated()
            .and()
            .exceptionHandling().authenticationEntryPoint(entryPoint)
            .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

        http.addFilterBefore(authenticationTokenFilter(), UsernamePasswordAuthenticationFilter::class.java)
        http.headers().cacheControl()

    }
}
