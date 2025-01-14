package com.frdx.tcgstats.joueur.domain.usecase.joueur

import com.frdx.tcgstats.joueur.domain.exception.JoueurNeJouePasException
import com.frdx.tcgstats.joueur.domain.model.Jeu
import com.frdx.tcgstats.joueur.domain.model.Joueur
import com.frdx.tcgstats.joueur.domain.port.serverside.PourDissocierUnJeuDunUtilisateurServerSidePort
import org.springframework.stereotype.Component


@Component
class DissocierJeuDUnJoueur(val pourDissocierUnJeuDunUtilisateurServerSidePort: PourDissocierUnJeuDunUtilisateurServerSidePort) {
    operator fun invoke(joueur: Joueur, jeu : Jeu) : Joueur {
        if (joueur.jeux.contains(jeu).not())
            throw JoueurNeJouePasException(jeu.nom)
        return pourDissocierUnJeuDunUtilisateurServerSidePort(joueur, jeu)
    }
}