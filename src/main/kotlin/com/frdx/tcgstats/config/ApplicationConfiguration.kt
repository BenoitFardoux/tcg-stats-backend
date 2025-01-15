package com.frdx.tcgstats.config

import com.frdx.tcgstats.joueur.serverside.adapter.mysql.repository.MariaDbJoueurRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder


@Configuration
class ApplicationConfiguration(val mariaDbJoueurRepository: MariaDbJoueurRepository) {
    @Bean
    fun userDetailService(): UserDetailsService {
        return UserDetailsService { email -> mariaDbJoueurRepository.getJoueurDocumentByCourriel(email)  }
    }
    @Bean
    fun passwordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }
    @Bean
    fun authenticationManager(config : AuthenticationConfiguration): AuthenticationManager {
        return config.authenticationManager
    }

    @Bean
    fun authenticationProvider(): AuthenticationProvider {
        val authProvider = DaoAuthenticationProvider()
        authProvider.setUserDetailsService(userDetailService())
        authProvider.setPasswordEncoder(passwordEncoder())

        return authProvider
    }
}