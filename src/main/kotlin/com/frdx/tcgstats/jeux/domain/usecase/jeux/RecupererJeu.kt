package com.frdx.tcgstats.jeux.domain.usecase.jeux

import com.frdx.tcgstats.jeux.domain.port.serverside.PourRecupererJeuServerSidePort
import org.springframework.stereotype.Component


@Component
class RecupererJeu(val pourRecupererJeuServerSidePort: PourRecupererJeuServerSidePort) {
    operator fun invoke(id : String) = pourRecupererJeuServerSidePort(id)
}