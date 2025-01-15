package com.frdx.tcgstats.joueur.userside.dto

data class LoginResponse(
    val token: String,

    val expiresIn: Long
)
