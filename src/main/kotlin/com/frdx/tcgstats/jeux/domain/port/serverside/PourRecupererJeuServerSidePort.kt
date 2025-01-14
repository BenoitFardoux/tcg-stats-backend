package com.frdx.tcgstats.jeux.domain.port.serverside

import com.frdx.tcgstats.jeux.domain.model.Jeu
import org.springframework.http.ResponseEntity

interface PourRecupererJeuServerSidePort {
    operator fun invoke(id : String) : Jeu
}