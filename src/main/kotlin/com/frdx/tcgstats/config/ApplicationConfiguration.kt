package com.frdx.tcgstats.config

import com.frdx.tcgstats.joueur.serverside.adapter.mysql.repository.MariaDbJoueurRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
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
}