package com.frdx.tcgstats.joueur.domain.model

import java.util.UUID

data class Jeu(
    val nom : String,
    val id : UUID,
    val logo : String? = "",
)