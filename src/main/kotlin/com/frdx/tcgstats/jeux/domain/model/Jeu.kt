package com.frdx.tcgstats.jeux.domain.model

import java.util.UUID

data class Jeu(
    val id : UUID? = null,
    val logo : String? = "",
    val nom : String,
)