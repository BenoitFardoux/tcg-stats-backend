package com.frdx.tcgstats.joueur.serverside.adapter.repository

import com.frdx.tcgstats.joueur.domain.model.Jeu
import com.frdx.tcgstats.joueur.domain.model.Joueur
import com.frdx.tcgstats.joueur.domain.port.serverside.PourAssocierUnJeuAUnJoueurServerSidePort
import com.frdx.tcgstats.joueur.serverside.adapter.mysql.repository.MariaDbJoueurRepository
import com.frdx.tcgstats.joueur.serverside.mapper.JeuxMapper.toJeuxDocument
import com.frdx.tcgstats.joueur.serverside.mapper.JoueurMapper.toJoueur
import com.frdx.tcgstats.joueur.serverside.mapper.JoueurMapper.toJoueurDocument
import org.springframework.stereotype.Repository


@Repository
class AssocierUnJeuAUnJoueurRepository(
    val mariaDbJoueurRepository: MariaDbJoueurRepository
) : PourAssocierUnJeuAUnJoueurServerSidePort {
    override fun invoke(joueur : Joueur, jeu : Jeu) : Joueur {
        val joueurDocument =joueur.toJoueurDocument()
        val jeuDocument = jeu.toJeuxDocument()

        joueurDocument.jeux.add(jeuDocument)
        val joueurEnregistre = mariaDbJoueurRepository.saveAndFlush(joueurDocument)
        return joueurEnregistre.toJoueur()
    }
}