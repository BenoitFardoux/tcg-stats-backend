package com.frdx.tcgstats.joueur.serverside.adapter.repository

import com.frdx.tcgstats.joueur.domain.port.serverside.PourSupprimerUnJoueurServerSidePort
import com.frdx.tcgstats.joueur.serverside.adapter.mysql.repository.MariaDbJoueurRepository
import com.frdx.tcgstats.joueur.serverside.exception.IdentifiantJoueurInvalide
import com.frdx.tcgstats.joueur.serverside.exception.JoueurInnexistantException
import org.springframework.stereotype.Repository
import java.util.*


@Repository
class SupprimerJoueurRepository(val mariaDbJoueurRepository: MariaDbJoueurRepository) : PourSupprimerUnJoueurServerSidePort {
    override fun invoke(id: String) {
        try {

            val joueur = mariaDbJoueurRepository.findById(UUID.fromString(id))
            if (joueur.isPresent.not()) {
                throw JoueurInnexistantException(id)
            }
            mariaDbJoueurRepository.delete(joueur.get())
        } catch (illegalArgumentException: IllegalArgumentException) {
            throw IdentifiantJoueurInvalide("L'id ${id} est invalide")
        }
    }

}