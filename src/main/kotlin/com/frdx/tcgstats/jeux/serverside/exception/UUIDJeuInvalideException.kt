package com.frdx.tcgstats.jeux.serverside.exception

import com.frdx.tcgstats.jeux.domain.exception.JeuException

class UUIDJeuInvalideException(id : String) : JeuException("L'UUID $id est invalide")