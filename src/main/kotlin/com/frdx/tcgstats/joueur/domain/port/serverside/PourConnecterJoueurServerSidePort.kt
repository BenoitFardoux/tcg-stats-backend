package com.frdx.tcgstats.joueur.domain.port.serverside

import com.frdx.tcgstats.joueur.domain.model.Joueur

fun interface PourConnecterJoueurServerSidePort {
    operator fun invoke(email: String, motDePasse : String): Joueur
}