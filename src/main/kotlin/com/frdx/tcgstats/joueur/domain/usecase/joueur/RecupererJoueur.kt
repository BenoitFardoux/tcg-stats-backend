package com.frdx.tcgstats.joueur.domain.usecase.joueur

import com.frdx.tcgstats.joueur.domain.model.Joueur
import com.frdx.tcgstats.joueur.domain.port.serverside.PourRecupererUnJoueurServerSidePort
import jakarta.transaction.Transactional
import org.springframework.stereotype.Component

@Component
class RecupererJoueur(val pourRecupererUnJoueurServerSidePort: PourRecupererUnJoueurServerSidePort) {
    @Transactional
    operator fun invoke(id: String) : Joueur = pourRecupererUnJoueurServerSidePort(id)
}