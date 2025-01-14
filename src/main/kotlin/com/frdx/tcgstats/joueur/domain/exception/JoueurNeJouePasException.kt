package com.frdx.tcgstats.joueur.domain.exception

class JoueurNeJouePasException(nom : String) : JoueurException("Le joueur ne joue pas au jeu $nom ") {
}