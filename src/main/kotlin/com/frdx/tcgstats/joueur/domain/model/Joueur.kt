package com.frdx.tcgstats.joueur.domain.model

import java.util.UUID

data class Joueur (
    val id : UUID? = null,
    val verifie : Boolean,
    val motDePasse : String,
    val courriel : String,
    val pseudo : String,
    val photoProfil : String,
    val jeux : List<Jeu>,
    val roles : List<String>
)