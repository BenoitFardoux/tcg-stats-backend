package com.frdx.tcgstats.joueur.domain.usecase.joueur

import com.frdx.tcgstats.joueur.domain.model.Joueur
import com.frdx.tcgstats.joueur.domain.port.serverside.PourRecupererJoueursServersidePort
import jakarta.transaction.Transactional
import org.springframework.stereotype.Component


@Component
class RecupererJoueurs(val pourRecupererJoueursServersidePort: PourRecupererJoueursServersidePort) {

    @Transactional
    operator fun invoke() : List<Joueur> = pourRecupererJoueursServersidePort()
}