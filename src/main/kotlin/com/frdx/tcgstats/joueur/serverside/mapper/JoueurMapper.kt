package com.frdx.tcgstats.joueur.serverside.mapper

import com.frdx.tcgstats.joueur.domain.model.Joueur
import com.frdx.tcgstats.joueur.serverside.dto.JoueurDocument
import com.frdx.tcgstats.joueur.serverside.mapper.JeuxMapper.toJeux
import com.frdx.tcgstats.joueur.serverside.mapper.JeuxMapper.toJeuxDocument

object JoueurMapper {
    fun JoueurDocument.toJoueur() : Joueur = Joueur(
        id = this.id!!,
        pseudo = this.pseudo,
        courriel = this.courriel,
        verifie = this.verifie,
        motDePasse = this.motDePasse,
        photoProfil = this.photoProfil,
        jeux = this.jeux.map {
            it.toJeux()
        },
        roles = this.roles.map { it.nom }
    )

    fun Joueur.toJoueurDocument() : JoueurDocument {
        return JoueurDocument(
            id = this.id,
            verifie =this.verifie,
            motDePasse = this.motDePasse,
            courriel = this.courriel,
            pseudo = this.pseudo,
            photoProfil = this.photoProfil,
            jeux = this.jeux.map { it.toJeuxDocument() }.toMutableList(),
        )
    }
}