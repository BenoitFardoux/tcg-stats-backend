package com.frdx.tcgstats.joueur.domain.exception

abstract class JoueurException (
    open val description: String
    ) : RuntimeException() {
        override val message: String
        get() = description
    }