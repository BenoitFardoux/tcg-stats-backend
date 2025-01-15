package com.frdx.tcgstats.joueur.domain.usecase.joueur

import com.frdx.tcgstats.joueur.domain.model.Joueur
import com.frdx.tcgstats.joueur.domain.port.serverside.PourConnecterJoueurServerSidePort
import org.springframework.stereotype.Component


@Component
class ConnecterJoueur(val pourConnecterJoueurServerSidePort: PourConnecterJoueurServerSidePort) {
    operator fun invoke(email : String, password : String) : Joueur = pourConnecterJoueurServerSidePort(email, password)
}