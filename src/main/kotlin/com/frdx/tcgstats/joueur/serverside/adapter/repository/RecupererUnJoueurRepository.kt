package com.frdx.tcgstats.joueur.serverside.adapter.repository

import com.frdx.tcgstats.joueur.domain.model.Joueur
import com.frdx.tcgstats.joueur.domain.port.serverside.PourRecupererUnJoueurServerSidePort
import com.frdx.tcgstats.joueur.serverside.adapter.mysql.repository.MariaDbJoueurRepository
import com.frdx.tcgstats.joueur.serverside.exception.IdentifiantJoueurInvalide
import com.frdx.tcgstats.joueur.serverside.exception.JoueurInnexistantException
import com.frdx.tcgstats.joueur.serverside.mapper.JoueurMapper.toJoueur
import org.springframework.stereotype.Repository
import java.util.*


@Repository
class RecupererUnJoueurRepository(val mariaDbJoueurRepository: MariaDbJoueurRepository) : PourRecupererUnJoueurServerSidePort {
    override operator fun invoke(id : String) : Joueur{
        try {

            val joueur = mariaDbJoueurRepository.findById(UUID.fromString(id))
            if (joueur.isPresent) {
                val joueurMappe = joueur.get().toJoueur()
                return joueurMappe
            }
            throw JoueurInnexistantException(id)
        } catch (illegalArgumentException: IllegalArgumentException) {
            throw IdentifiantJoueurInvalide("L'id ${id} est invalide")
        }

    }

}