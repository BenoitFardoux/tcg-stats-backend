package com.frdx.tcgstats.joueur.serverside.adapter.mysql.repository

import com.frdx.tcgstats.joueur.serverside.dto.RoleDocument
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface MariaDbRoleRepository : JpaRepository<RoleDocument, UUID> {
    fun findByNom(name: String): Optional<RoleDocument>
    fun existsByNom(name: String): Boolean
}