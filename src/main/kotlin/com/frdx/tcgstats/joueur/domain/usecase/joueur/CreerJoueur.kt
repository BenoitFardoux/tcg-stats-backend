package com.frdx.tcgstats.joueur.domain.usecase.joueur

import com.frdx.tcgstats.joueur.domain.model.Joueur
import com.frdx.tcgstats.joueur.domain.port.serverside.PourCreerUnJoueurServerSidePort
import jakarta.transaction.Transactional
import org.springframework.stereotype.Component


@Component
class CreerJoueur(val pourCreerJoueur: PourCreerUnJoueurServerSidePort) {

    @Transactional
    operator fun invoke(joueur : Joueur) : Joueur = pourCreerJoueur(joueur)
}