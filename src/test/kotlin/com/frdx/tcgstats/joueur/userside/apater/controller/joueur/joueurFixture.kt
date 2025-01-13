package com.frdx.tcgstats.joueur.userside.apater.controller.joueur

import com.frdx.tcgstats.joueur.userside.dto.CreerJoueurRestRessource

object joueurFixture {
    const val courrielValdide = "addresseMail@mail.com"
    const val courrielInvalide = "amail.co"
    const val pseudoValide = "Joueur"
    const val motDePasseValide = "#Password12"
    const val motDePasseInvalide = "password"
    val creerJoueurDTO = CreerJoueurRestRessource(
        courrielValdide,
        motDePasseValide,
        pseudoValide
    )
}