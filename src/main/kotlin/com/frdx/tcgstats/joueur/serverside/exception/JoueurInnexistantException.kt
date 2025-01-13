package com.frdx.tcgstats.joueur.serverside.exception

import com.frdx.tcgstats.joueur.domain.exception.JoueurException

class JoueurInnexistantException(id : String) : JoueurException("Le joueur avec l'id $id n'existe pas.") {
}