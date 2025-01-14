package com.frdx.tcgstats.jeux.serverside.exception

import com.frdx.tcgstats.jeux.domain.exception.JeuException

class JeuNonExistantException(id : String) : JeuException("Le jeu avec l'id $id n'existe pas.")