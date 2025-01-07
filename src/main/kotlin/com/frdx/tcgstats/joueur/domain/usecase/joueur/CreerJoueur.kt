package com.frdx.tcgstats.joueur.domain.usecase.joueur

import com.frdx.tcgstats.joueur.domain.exception.CourrielInvalideException
import com.frdx.tcgstats.joueur.domain.model.Joueur
import com.frdx.tcgstats.joueur.domain.port.serverside.PourCreerUnJoueurServerSidePort
import com.frdx.tcgstats.utils.Utils.courrielEstValide
import jakarta.transaction.Transactional
import org.springframework.stereotype.Component


@Component
class CreerJoueur(val pourCreerJoueur: PourCreerUnJoueurServerSidePort) {

    @Transactional
    operator fun invoke(joueur : Joueur) : Joueur{
        if (!courrielEstValide(joueur.courriel)){
            throw CourrielInvalideException("Le courriel est invalide")
        }
       return pourCreerJoueur(joueur)
    }
}