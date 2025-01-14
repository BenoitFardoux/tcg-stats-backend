package com.frdx.tcgstats.jeux.domain.usecase.jeux

import com.frdx.tcgstats.jeux.domain.model.Jeu
import com.frdx.tcgstats.jeux.domain.port.serverside.PourCreerJeuServerSidePort
import org.springframework.stereotype.Component


@Component
class CreerJeu(val pourCreerJeuServerSidePort: PourCreerJeuServerSidePort) {
    operator fun invoke(jeu: Jeu): Jeu = pourCreerJeuServerSidePort(jeu)
}