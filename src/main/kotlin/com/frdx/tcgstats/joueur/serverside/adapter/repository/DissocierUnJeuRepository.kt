package com.frdx.tcgstats.joueur.serverside.adapter.repository

import com.frdx.tcgstats.joueur.domain.model.Jeu
import com.frdx.tcgstats.joueur.domain.model.Joueur
import com.frdx.tcgstats.joueur.domain.port.serverside.PourDissocierUnJeuDunUtilisateurServerSidePort
import com.frdx.tcgstats.joueur.serverside.adapter.mysql.repository.MariaDbJoueurRepository
import com.frdx.tcgstats.joueur.serverside.mapper.JoueurMapper.toJoueur
import com.frdx.tcgstats.joueur.serverside.mapper.JoueurMapper.toJoueurDocument
import org.springframework.stereotype.Repository


@Repository
class DissocierUnJeuRepository(val mariaDbJoueurRepository: MariaDbJoueurRepository) : PourDissocierUnJeuDunUtilisateurServerSidePort {
    override fun invoke(joueur: Joueur, jeu: Jeu): Joueur {
        (joueur.jeux as MutableList).remove(jeu)
        val joueurDocument  = mariaDbJoueurRepository.saveAndFlush(joueur.toJoueurDocument())
        return joueurDocument.toJoueur()
    }
}