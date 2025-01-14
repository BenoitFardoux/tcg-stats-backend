package com.frdx.tcgstats.jeux.serverside.exception

import com.frdx.tcgstats.jeux.domain.exception.JeuException

class JeuUtiliseException(nom : String) : JeuException("Le jeu $nom est encore utilisé")