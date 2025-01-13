package com.frdx.tcgstats.joueur.domain.port.serverside

import com.frdx.tcgstats.joueur.domain.model.Joueur

interface PourRecupererUnJoueurServerSidePort {
    operator fun invoke(id: String) : Joueur
}