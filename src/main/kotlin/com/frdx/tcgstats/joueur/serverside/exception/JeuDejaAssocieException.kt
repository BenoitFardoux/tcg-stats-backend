package com.frdx.tcgstats.joueur.serverside.exception

import com.frdx.tcgstats.joueur.domain.exception.JoueurException

class JeuDejaAssocieException(id : String) : JoueurException("le jeu $id est déjà associé") {
}