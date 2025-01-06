package com.frdx.tcgstats.joueur.serverside.mapper

import com.frdx.tcgstats.joueur.domain.model.Jeux
import com.frdx.tcgstats.joueur.serverside.dto.JeuxDocument
import com.frdx.tcgstats.joueur.serverside.dto.JoueurDocument

object JeuxMapper {
    fun JeuxDocument.toJeux() : Jeux= Jeux(
        id = this.id!!,
        nom = this.nom,
    )

    fun Jeux.toJeuxDocument() : JeuxDocument = JeuxDocument(id,nom)
}