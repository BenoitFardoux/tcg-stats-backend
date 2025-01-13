package com.frdx.tcgstats.joueur.serverside.adapter.repository

import com.frdx.tcgstats.joueur.domain.model.Joueur
import com.frdx.tcgstats.joueur.domain.port.serverside.PourRecupererJoueursServersidePort
import com.frdx.tcgstats.joueur.serverside.adapter.mysql.repository.MariaDbJoueurRepository
import com.frdx.tcgstats.joueur.serverside.mapper.JoueurMapper.toJoueur
import org.springframework.stereotype.Repository


@Repository
class RecupererJoueursRepository(val mariaDbJoueurRepository: MariaDbJoueurRepository) : PourRecupererJoueursServersidePort {
    override fun invoke(): List<Joueur> {
        return mariaDbJoueurRepository.findAll().map { it.toJoueur() }
    }
}