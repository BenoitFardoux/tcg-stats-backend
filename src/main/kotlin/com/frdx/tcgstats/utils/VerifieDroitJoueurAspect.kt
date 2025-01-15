package com.frdx.tcgstats.utils

import com.frdx.tcgstats.joueur.serverside.dto.JoueurDocument
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import java.util.*

@Component
class VerifieDroitJoueurAspect {
    operator fun invoke(id : String) {
        println("je passe ici")
        val uuid = UUID.fromString(id)
        val idUtilisateurActuel = recupereIdJoueurAuthentifié()
        if (uuid != idUtilisateurActuel){
            throw IllegalArgumentException("id must be equal to idUtilisateurActuel")
        }
    }

    private fun recupereIdJoueurAuthentifié() : UUID? {
        val principal = SecurityContextHolder.getContext().authentication.principal as JoueurDocument
        return principal.id
    }
}