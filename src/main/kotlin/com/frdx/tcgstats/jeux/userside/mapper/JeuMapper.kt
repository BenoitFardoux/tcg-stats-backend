package com.frdx.tcgstats.jeux.userside.mapper

import com.frdx.tcgstats.jeux.domain.model.Jeu
import com.frdx.tcgstats.jeux.userside.dto.CreerJeuDto

object JeuMapper {
    fun CreerJeuDto.toJeu() = Jeu(nom =nom)
}