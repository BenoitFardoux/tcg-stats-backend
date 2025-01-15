package com.frdx.tcgstats.joueur.serverside.adapter.repository

import com.frdx.tcgstats.joueur.domain.model.Joueur
import com.frdx.tcgstats.joueur.domain.port.serverside.PourConnecterJoueurServerSidePort
import com.frdx.tcgstats.joueur.serverside.adapter.mysql.repository.MariaDbJoueurRepository
import com.frdx.tcgstats.joueur.serverside.exception.JoueurInnexistantException
import com.frdx.tcgstats.joueur.serverside.mapper.JoueurMapper.toJoueur
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Repository


@Repository
class ConnecterJoueurRepository(
    private val mariaDbJoueurRepository: MariaDbJoueurRepository,
    private val authenticationManager: AuthenticationManager
) : PourConnecterJoueurServerSidePort {
    override fun invoke(email: String, motDePasse: String): Joueur {
        authenticationManager.authenticate(UsernamePasswordAuthenticationToken(email, motDePasse))
        val joueur = mariaDbJoueurRepository.getJoueurDocumentByCourriel(courriel = email)
        if (joueur != null) {

            return joueur.toJoueur()
        } else {
            throw JoueurInnexistantException(email)
        }
    }
}