package com.frdx.tcgstats.joueur.serverside.adapter.repository

import com.frdx.tcgstats.joueur.domain.model.Joueur
import com.frdx.tcgstats.joueur.domain.port.serverside.PourCreerUnJoueurServerSidePort
import com.frdx.tcgstats.joueur.serverside.adapter.mysql.repository.MariaDbJoueurRepository
import com.frdx.tcgstats.joueur.serverside.mapper.JoueurMapper.toJoueur
import com.frdx.tcgstats.joueur.serverside.mapper.JoueurMapper.toJoueurDocument
import org.springframework.stereotype.Repository
import java.lang.RuntimeException


@Repository
class CreerJoueurRepository(
    val mariaDbJoueurRepository: MariaDbJoueurRepository
) : PourCreerUnJoueurServerSidePort {
    override fun invoke(joueur: Joueur): Joueur {
        mariaDbJoueurRepository.getJoueurDocumentByCourriel(joueur.courriel)?.let { throw RuntimeException("Joueur document not found") }

        return mariaDbJoueurRepository.saveAndFlush(joueur.toJoueurDocument()).toJoueur()
    }

}