package com.frdx.tcgstats.joueur.domain.usecase.joueur

import com.frdx.tcgstats.joueur.domain.exception.CourrielInvalideException
import com.frdx.tcgstats.joueur.domain.model.Joueur
import com.frdx.tcgstats.joueur.domain.port.serverside.PourCreerUnJoueurServerSidePort
import com.frdx.tcgstats.utils.utils.valider_courriel
import jakarta.transaction.Transactional
import org.springframework.stereotype.Component


@Component
class CreerJoueur(val pourCreerJoueur: PourCreerUnJoueurServerSidePort) {

    @Transactional
    operator fun invoke(joueur : Joueur) : Joueur{
        if (!valider_courriel(joueur.courriel)){
            throw CourrielInvalideException("Le courriel est invalide")
        }
       return pourCreerJoueur(joueur)
    }
}