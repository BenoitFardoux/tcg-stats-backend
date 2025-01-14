package com.frdx.tcgstats.jeux.domain.port.serverside

import com.frdx.tcgstats.jeux.domain.model.Jeu

interface PourCreerJeuServerSidePort {
    operator fun invoke(jeu : Jeu): Jeu
}