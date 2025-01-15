package com.frdx.tcgstats.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Lazy
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer


@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
class SecurityConfiguration(
    private val authenticationProvider: AuthenticationProvider,
    @Lazy private val jwtAuthenticationFilter: JwtAuthenticationFilter  // Lazy injection here
) {
    companion object {
        val PUBLIC_GET_REQUEST_MANAGER: Array<String> = arrayOf(
            "/api/**", "/**", "/swagger-ui/**","/actuator",
            "/v3/api-docs/**",
        )

        val PUBLIC_POST_REQUEST_MANAGER: Array<String> = arrayOf(
            "/auth/login","/auth/signup"
        )
    }

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http.csrf { it.disable() }
            .cors { it.configurationSource(corsConfigurationSource()) }

            .authorizeHttpRequests {

                it.requestMatchers(HttpMethod.GET, *PUBLIC_GET_REQUEST_MANAGER).permitAll()
                it.requestMatchers(HttpMethod.POST, *PUBLIC_POST_REQUEST_MANAGER).permitAll().anyRequest().authenticated()
            }
            .sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)  // Lazy-filter injection here

        return http.build()
    }

    @Bean
    fun corsConfigurer(): WebMvcConfigurer {
        return object : WebMvcConfigurer {
            override fun addCorsMappings(registry: CorsRegistry) {
                registry.addMapping("/**").allowedOrigins("*").allowedMethods("GET", "POST", "PUT", "DELETE")
            }
        }
    }

    private fun corsConfigurationSource(): UrlBasedCorsConfigurationSource {
        val source = UrlBasedCorsConfigurationSource()
        val config = CorsConfiguration()
        config.allowedOriginPatterns = listOf("*")
        config.allowedHeaders = listOf("*")
        config.allowedMethods = listOf("GET", "PATCH", "POST", "PUT", "DELETE", "OPTIONS")
        source.registerCorsConfiguration("/**", config)
        return source
    }
}
