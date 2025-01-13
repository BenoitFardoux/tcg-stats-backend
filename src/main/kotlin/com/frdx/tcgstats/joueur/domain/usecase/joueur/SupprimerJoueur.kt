package com.frdx.tcgstats.joueur.domain.usecase.joueur

import com.frdx.tcgstats.joueur.domain.port.serverside.PourSupprimerUnJoueurServerSidePort
import jakarta.transaction.Transactional
import org.springframework.stereotype.Component

@Component
class SupprimerJoueur(val pourSupprimerUnJoueurServerSidePort: PourSupprimerUnJoueurServerSidePort) {
    @Transactional
    operator fun invoke(id : String) = pourSupprimerUnJoueurServerSidePort(id)
}