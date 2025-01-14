package com.frdx.tcgstats.jeux.serverside.exception

import com.frdx.tcgstats.jeux.domain.exception.JeuException

class JeuDejaExistantException(nom : String) : JeuException("Le nom $nom existe déjà")