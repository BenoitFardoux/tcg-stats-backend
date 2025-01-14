package com.frdx.tcgstats.jeux.domain.usecase.jeux

import com.frdx.tcgstats.jeux.domain.model.Jeu
import com.frdx.tcgstats.jeux.domain.port.serverside.PourRecupererJeuxServerSidePort
import org.springframework.stereotype.Component


@Component
class RecupererJeux(val pourRecupererJeuxServerSidePort: PourRecupererJeuxServerSidePort) {
    operator fun invoke(): List<Jeu> = pourRecupererJeuxServerSidePort()
}