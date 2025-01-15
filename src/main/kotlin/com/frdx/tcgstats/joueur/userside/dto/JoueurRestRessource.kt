package com.frdx.tcgstats.joueur.userside.dto

import com.frdx.tcgstats.joueur.domain.model.Jeu
import java.util.*

data class JoueurRestRessource (
    val id : UUID?,
    val verifie : Boolean,
    val courriel : String,
    val pseudo : String,
    val photoProfil : String,
    val jeux : List<Jeu>,
    val roles : List<String>
)