package com.frdx.tcgstats.joueur.domain.port.serverside

import com.frdx.tcgstats.joueur.domain.model.Joueur

interface PourRecupererJoueursServersidePort {
    operator fun invoke(): List<Joueur>
}