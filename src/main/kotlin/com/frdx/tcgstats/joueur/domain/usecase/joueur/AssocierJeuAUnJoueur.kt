package com.frdx.tcgstats.joueur.domain.usecase.joueur

import com.frdx.tcgstats.joueur.domain.model.Jeu
import com.frdx.tcgstats.joueur.domain.model.Joueur
import com.frdx.tcgstats.joueur.domain.port.serverside.PourAssocierUnJeuAUnJoueurServerSidePort
import com.frdx.tcgstats.joueur.serverside.exception.JeuDejaAssocieException
import org.springframework.stereotype.Component

@Component
class AssocierJeuAUnJoueur(val pourAssocierUnJeuAUnJoueurServerSidePort: PourAssocierUnJeuAUnJoueurServerSidePort) {
    operator fun invoke(joueur : Joueur, jeu : Jeu): Joueur{
            if (joueur.jeux.contains(jeu)){
                throw JeuDejaAssocieException(jeu.id.toString())
            }
            return pourAssocierUnJeuAUnJoueurServerSidePort(joueur, jeu)
        }
}