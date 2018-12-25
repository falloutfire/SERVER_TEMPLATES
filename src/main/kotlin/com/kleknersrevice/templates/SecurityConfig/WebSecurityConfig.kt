package com.kleknersrevice.templates.SecurityConfig

import com.kleknersrevice.templates.Entity.Role
import com.kleknersrevice.templates.Service.Impl.UserDetailServiceImpl
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.password.NoOpPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter


@Configuration
@EnableWebSecurity
open class WebSecurityConfig(private val userDetailServiceImpl: UserDetailServiceImpl) :
    WebSecurityConfigurerAdapter() {

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http.csrf().disable().authorizeRequests()
            .antMatchers(HttpMethod.GET, "/").permitAll()
            .antMatchers(HttpMethod.POST, "/login").permitAll()
            .antMatchers(HttpMethod.POST, "/**").hasRole(Role.ADMIN.authority)
            .antMatchers(HttpMethod.GET, "/os/").hasRole(Role.ADMIN.authority)
            .anyRequest().authenticated()
            .and()
            // We filter the api/login requests
            .addFilterBefore(
                JWTLoginFilter("/login", authenticationManager()),
                UsernamePasswordAuthenticationFilter::class.java
            )
            // And filter other requests to check the presence of JWT in header
            .addFilterBefore(
                JWTAuthenticationFilter(userDetailServiceImpl),
                UsernamePasswordAuthenticationFilter::class.java
            )
    }


    @Throws(Exception::class)
    override fun configure(auth: AuthenticationManagerBuilder?) {
        auth?.authenticationProvider(authenticationProvider())
    }

    private fun authenticationProvider(): DaoAuthenticationProvider {
        val authProvider = DaoAuthenticationProvider()
        authProvider.setPasswordEncoder(encoderFun())
        authProvider.setUserDetailsService(userDetailServiceImpl)
        return authProvider
    }

    @Bean
    open fun encoderFun(): PasswordEncoder {
        //return PasswordEncoderFactories.createDelegatingPasswordEncoder()
        return NoOpPasswordEncoder.getInstance()
    }
}