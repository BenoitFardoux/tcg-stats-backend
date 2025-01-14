package com.frdx.tcgstats.jeux.domain.port.serverside

import com.frdx.tcgstats.jeux.domain.model.Jeu

interface PourRecupererJeuxServerSidePort {
    operator fun invoke(): List<Jeu>
}