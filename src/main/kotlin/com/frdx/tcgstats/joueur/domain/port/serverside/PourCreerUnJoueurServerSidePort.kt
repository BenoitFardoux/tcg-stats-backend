package com.frdx.tcgstats.joueur.domain.port.serverside

import com.frdx.tcgstats.joueur.domain.model.Joueur

fun interface PourCreerUnJoueurServerSidePort {
    operator fun invoke(joueur : Joueur) : Joueur
}