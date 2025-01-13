package com.frdx.tcgstats.joueur.userside.mapper

import com.frdx.tcgstats.joueur.domain.model.Joueur
import com.frdx.tcgstats.joueur.userside.dto.CreerJoueurRestRessource
import com.frdx.tcgstats.joueur.userside.dto.JoueurRestRessource

object JoueurMapper {
    fun CreerJoueurRestRessource.toJoueur() : Joueur {
        return Joueur(
            verifie = false,
            motDePasse = motDePasse,
            courriel = courriel,
            pseudo = pseudonyme,
            photoProfil = "",
            jeux = emptyList()
        )
    }
    fun Joueur.toJoueurRestRessource() : JoueurRestRessource {
        return JoueurRestRessource(
            verifie = verifie,
            courriel = courriel,
            pseudo = pseudo,
            photoProfil = photoProfil,
            jeux = emptyList(),
            id = id
        )
    }
}