package com.frdx.tcgstats.jeux.domain.model

import java.util.UUID

data class Jeu(
    val id : UUID? = null,
    val nom : String,
)