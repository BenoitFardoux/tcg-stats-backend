package com.frdx.tcgstats.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer



@Configuration
@EnableWebSecurity
class SecurityConfiguration{
    companion object {
        val PUBLIC_GET_REQUEST_MANAGER: Array<String> = arrayOf(
            "/api/**", "**", "/swagger-ui/**",
            "/v3/api-docs/**",
        )

        val PUBLIC_POST_REQUEST_MANAGER: Array<String> = arrayOf(
            "**",
        )
    }

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http.csrf { it.disable() }
            .authorizeHttpRequests {
                it.requestMatchers(HttpMethod.GET, *PUBLIC_GET_REQUEST_MANAGER).permitAll()

                it.requestMatchers(HttpMethod.POST, *PUBLIC_POST_REQUEST_MANAGER).permitAll().anyRequest().authenticated()
            }
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

}