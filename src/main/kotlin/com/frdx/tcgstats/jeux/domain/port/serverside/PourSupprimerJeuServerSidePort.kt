package com.frdx.tcgstats.jeux.domain.port.serverside

interface PourSupprimerJeuServerSidePort {
    operator fun invoke(id : String)
}