package com.frdx.tcgstats.jeux.serverside.adapter.repository

import com.frdx.tcgstats.jeux.domain.model.Jeu
import com.frdx.tcgstats.jeux.domain.port.serverside.PourRecupererJeuServerSidePort
import com.frdx.tcgstats.jeux.serverside.adapter.mysql.repository.MariaDbJeuxRepository
import com.frdx.tcgstats.jeux.serverside.exception.JeuNonExistantException
import com.frdx.tcgstats.jeux.serverside.exception.UUIDJeuInvalideException
import com.frdx.tcgstats.jeux.serverside.mapper.JeuxMapper.toJeux
import com.frdx.tcgstats.utils.Utils
import org.springframework.stereotype.Repository
import java.util.UUID


@Repository
class RecupererJeuRepository(val mariaDbJeuxRepository: MariaDbJeuxRepository) : PourRecupererJeuServerSidePort {
    override fun invoke(id: String): Jeu {
        if (Utils.uuidEstValide(id).not())
            throw UUIDJeuInvalideException(id)
        val jeu = mariaDbJeuxRepository.findById(UUID.fromString(id))
        if (!jeu.isPresent){
            throw JeuNonExistantException(id)
        }
        return jeu.get().toJeux()
    }
}