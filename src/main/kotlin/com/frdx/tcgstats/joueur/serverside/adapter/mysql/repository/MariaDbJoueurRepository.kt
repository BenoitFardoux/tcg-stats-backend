package com.frdx.tcgstats.joueur.serverside.adapter.mysql.repository

import com.frdx.tcgstats.joueur.serverside.dto.JoueurDocument
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID


@Repository
interface MariaDbJoueurRepository :  JpaRepository<JoueurDocument, UUID> {
    fun getJoueurDocumentById(id: UUID): JoueurDocument?
    fun getJoueurDocumentByPseudo(pseudo: String): JoueurDocument?
    fun getJoueurDocumentByCourriel(courriel: String): JoueurDocument?
}