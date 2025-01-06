package com.frdx.tcgstats.utils

object utils {
    val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$"
    fun valider_courriel(chaine : String) : Boolean{
        return chaine.matches(emailRegex.toRegex())
    }
}