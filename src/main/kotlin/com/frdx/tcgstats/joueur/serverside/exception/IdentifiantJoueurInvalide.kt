package com.frdx.tcgstats.joueur.serverside.exception

import com.frdx.tcgstats.joueur.domain.exception.JoueurException

class IdentifiantJoueurInvalide (   override val description: String) : JoueurException(description)