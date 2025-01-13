package com.frdx.tcgstats.joueur.domain.port.serverside

interface PourSupprimerUnJoueurServerSidePort {
    operator fun invoke(id :String)
}