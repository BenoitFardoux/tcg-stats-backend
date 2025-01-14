package com.frdx.tcgstats.jeux.domain.usecase.jeux

import com.frdx.tcgstats.jeux.domain.port.serverside.PourSupprimerJeuServerSidePort
import org.springframework.stereotype.Component

@Component
class SupprimerJeu( val pourSupprimerJeuServerSidePort: PourSupprimerJeuServerSidePort
) {
    operator fun invoke(id : String) {
        pourSupprimerJeuServerSidePort(id)
    }
}