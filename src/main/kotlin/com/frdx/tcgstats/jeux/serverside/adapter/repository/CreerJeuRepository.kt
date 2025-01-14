package com.frdx.tcgstats.jeux.serverside.adapter.repository

import com.frdx.tcgstats.jeux.domain.port.serverside.PourCreerJeuServerSidePort
import com.frdx.tcgstats.jeux.serverside.adapter.mysql.repository.MariaDbJeuxRepository
import com.frdx.tcgstats.jeux.serverside.mapper.JeuxMapper.toJeuxDocument
import com.frdx.tcgstats.jeux.domain.model.Jeu
import com.frdx.tcgstats.jeux.serverside.exception.JeuDejaExistantException
import com.frdx.tcgstats.jeux.serverside.mapper.JeuxMapper.toJeux
import org.springframework.stereotype.Repository


@Repository
class CreerJeuRepository(val mariaDbJeuxRepository: MariaDbJeuxRepository) : PourCreerJeuServerSidePort {
    override fun invoke(jeu: Jeu): Jeu {
        if (mariaDbJeuxRepository.existsByNom(jeu.nom)){
            throw JeuDejaExistantException(jeu.nom)
        }
        val jeuACreer = jeu.toJeuxDocument()
        val joueurCree = mariaDbJeuxRepository.saveAndFlush(jeuACreer)
        return joueurCree.toJeux()
    }
}