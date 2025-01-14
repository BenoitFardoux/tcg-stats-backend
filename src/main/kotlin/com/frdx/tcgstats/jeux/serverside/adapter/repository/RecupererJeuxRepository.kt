package com.frdx.tcgstats.jeux.serverside.adapter.repository

import com.frdx.tcgstats.jeux.domain.model.Jeu
import com.frdx.tcgstats.jeux.domain.port.serverside.PourRecupererJeuxServerSidePort
import com.frdx.tcgstats.jeux.serverside.adapter.mysql.repository.MariaDbJeuxRepository
import com.frdx.tcgstats.jeux.serverside.mapper.JeuxMapper.toJeux
import org.springframework.stereotype.Repository


@Repository
class RecupererJeuxRepository(val mariaDbJeuxRepository: MariaDbJeuxRepository) : PourRecupererJeuxServerSidePort {
    override fun invoke(): List<Jeu> {
        val listeJeux = mariaDbJeuxRepository.findAll().map { it.toJeux() }
        return listeJeux
    }
}