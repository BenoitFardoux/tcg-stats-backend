package com.frdx.tcgstats.jeux.serverside.adapter.mysql.repository

import com.frdx.tcgstats.jeux.serverside.dto.JeuxDocument
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*


@Repository
interface MariaDbJeuxRepository : JpaRepository<JeuxDocument,UUID> {
    fun existsByNom(nom: String): Boolean
}