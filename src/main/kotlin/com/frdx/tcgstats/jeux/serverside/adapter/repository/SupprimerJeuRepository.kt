package com.frdx.tcgstats.jeux.serverside.adapter.repository

import com.frdx.tcgstats.jeux.domain.port.serverside.PourSupprimerJeuServerSidePort
import com.frdx.tcgstats.jeux.serverside.adapter.mysql.repository.MariaDbJeuxRepository
import com.frdx.tcgstats.jeux.serverside.exception.JeuNonExistantException
import com.frdx.tcgstats.jeux.serverside.exception.JeuUtiliseException
import com.frdx.tcgstats.jeux.serverside.exception.UUIDJeuInvalideException
import com.frdx.tcgstats.utils.Utils
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class SupprimerJeuRepository(
    val mariaDbJeuxRepository: MariaDbJeuxRepository
) : PourSupprimerJeuServerSidePort {
    override fun invoke(id: String) {
        if (Utils.uuidEstValide(id).not())
            throw UUIDJeuInvalideException(id)
        if (mariaDbJeuxRepository.existsById(UUID.fromString(id)).not())
            throw JeuNonExistantException(id)
        val jeu = mariaDbJeuxRepository.findById(UUID.fromString(id)).get()
        if (jeu.joueurs.isEmpty()) {
            mariaDbJeuxRepository.deleteById(UUID.fromString(id))
        } else {
            throw JeuUtiliseException(jeu.nom)
        }
    }
}
