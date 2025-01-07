package com.frdx.tcgstats.utils

object Utils {
    const val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$"
    const val passwordRegex = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}\$"
    fun courrielEstValide(chaine : String) : Boolean{
        return chaine.matches(emailRegex.toRegex())
    }

    fun motDePasseEstValide(chaine : String) : Boolean =
        chaine.matches(passwordRegex.toRegex())

}