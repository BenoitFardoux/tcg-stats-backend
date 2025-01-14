package com.frdx.tcgstats.joueur.serverside.mapper

import com.frdx.tcgstats.joueur.domain.model.Jeu
import com.frdx.tcgstats.jeux.serverside.dto.JeuxDocument

object JeuxMapper {
    fun JeuxDocument.toJeux() : Jeu= Jeu(
        id = this.id!!,
        nom = this.nom,
        logo = this.logo,
    )

    fun Jeu.toJeuxDocument() : JeuxDocument = JeuxDocument(id,nom)
}