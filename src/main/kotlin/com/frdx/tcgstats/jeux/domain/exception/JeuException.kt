package com.frdx.tcgstats.jeux.domain.exception

abstract class JeuException (
    open val description: String
) : RuntimeException() {
    override val message: String
        get() = description
}