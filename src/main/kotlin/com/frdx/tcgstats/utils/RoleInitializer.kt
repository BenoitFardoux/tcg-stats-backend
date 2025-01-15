package com.frdx.tcgstats.utils

import com.frdx.tcgstats.joueur.serverside.adapter.mysql.repository.MariaDbRoleRepository
import com.frdx.tcgstats.joueur.serverside.dto.RoleDocument
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component


@Component
class RoleInitializer(private val mariaDbRoleRepository: MariaDbRoleRepository) {
    @Bean
    fun initialize() : CommandLineRunner {
        return CommandLineRunner{
            val roles = listOf("ADMIN", "USER")

            roles.forEach { role ->
                if (mariaDbRoleRepository.existsByNom(role).not()){
                    mariaDbRoleRepository.saveAndFlush(RoleDocument(nom= role))
                    println("Role $role created")
                } else {
                    println("Role $role already exists")
                }
            }
        }


    }
}