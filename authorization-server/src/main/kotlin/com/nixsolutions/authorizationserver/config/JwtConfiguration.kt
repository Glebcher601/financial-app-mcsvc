package com.nixsolutions.authorizationserver.config

import com.nixsolutions.authorizationserver.security.jwt.JwtAuthenticationEntryPoint
import com.nixsolutions.authorizationserver.security.jwt.JwtAuthenticationFilter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.BeanIds
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.NoOpPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
class JwtConfiguration : WebSecurityConfigurerAdapter() {

  @Autowired
  @Qualifier("customUserDetailsService")
  private lateinit var customUserDetailsService: UserDetailsService

  @Autowired
  private lateinit var unauthorizedHandler: JwtAuthenticationEntryPoint

  @Autowired
  private lateinit var jwtFilter: JwtAuthenticationFilter;

  override fun configure(auth: AuthenticationManagerBuilder) {
    auth.userDetailsService(customUserDetailsService)
        .passwordEncoder(passwordEncoder())
  }

  @Bean(BeanIds.AUTHENTICATION_MANAGER)
  override fun authenticationManagerBean(): AuthenticationManager {
    return super.authenticationManagerBean()
  }

  @Throws(Exception::class)
  override fun configure(http: HttpSecurity) {
    http.cors()
        .and()
        .csrf()
        .disable()
        .exceptionHandling()
        .authenticationEntryPoint(unauthorizedHandler)
        .and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .authorizeRequests()
        .antMatchers("/",
            "/favicon.ico",
            "/**/*.png",
            "/**/*.gif",
            "/**/*.svg",
            "/**/*.jpg",
            "/**/*.html",
            "/**/*.css",
            "/**/*.js")
        .permitAll()
        .antMatchers("/api/v0/auth/**")
        .permitAll()
        .anyRequest()
        .authenticated()

    http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter::class.java)
  }

  @Bean
  fun passwordEncoder(): PasswordEncoder {
    return NoOpPasswordEncoder.getInstance()
  }
}