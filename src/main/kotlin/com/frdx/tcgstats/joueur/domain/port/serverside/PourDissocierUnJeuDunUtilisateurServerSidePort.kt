package com.frdx.tcgstats.joueur.domain.port.serverside

import com.frdx.tcgstats.joueur.domain.model.Jeu
import com.frdx.tcgstats.joueur.domain.model.Joueur


interface PourDissocierUnJeuDunUtilisateurServerSidePort {
    operator fun invoke(joueur : Joueur, jeu : Jeu) : Joueur
}