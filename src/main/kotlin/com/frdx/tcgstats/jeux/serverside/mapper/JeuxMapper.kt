package com.frdx.tcgstats.jeux.serverside.mapper

import com.frdx.tcgstats.jeux.domain.model.Jeu
import com.frdx.tcgstats.jeux.serverside.dto.JeuxDocument

object JeuxMapper {
    fun JeuxDocument.toJeux() : Jeu= Jeu(
        id = this.id!!,
        nom = this.nom,
    )

    fun Jeu.toJeuxDocument() : JeuxDocument = JeuxDocument(id,nom)
}