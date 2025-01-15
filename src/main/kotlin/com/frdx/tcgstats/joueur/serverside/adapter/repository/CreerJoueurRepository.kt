package com.frdx.tcgstats.joueur.serverside.adapter.repository

import com.frdx.tcgstats.joueur.domain.model.Joueur
import com.frdx.tcgstats.joueur.domain.port.serverside.PourCreerUnJoueurServerSidePort
import com.frdx.tcgstats.joueur.serverside.adapter.mysql.repository.MariaDbJoueurRepository
import com.frdx.tcgstats.joueur.serverside.adapter.mysql.repository.MariaDbRoleRepository
import com.frdx.tcgstats.joueur.serverside.exception.JoueurDejaExistantException
import com.frdx.tcgstats.joueur.serverside.mapper.JoueurMapper.toJoueur
import com.frdx.tcgstats.joueur.serverside.mapper.JoueurMapper.toJoueurDocument
import org.springframework.stereotype.Repository


@Repository
class CreerJoueurRepository(
    val mariaDbJoueurRepository: MariaDbJoueurRepository,
    val mariaDbRoleRepository: MariaDbRoleRepository
) : PourCreerUnJoueurServerSidePort {
    override fun invoke(joueur: Joueur): Joueur {
        mariaDbJoueurRepository.getJoueurDocumentByCourriel(joueur.courriel)?.let {
            throw JoueurDejaExistantException("Le joueur existe déjà")
        }
        val joueurACreer = joueur.toJoueurDocument()
        val roleUSER = mariaDbRoleRepository.findByNom("USER")
        roleUSER.ifPresent{ role -> joueurACreer.roles.add(role)}
        return mariaDbJoueurRepository.saveAndFlush(joueurACreer).toJoueur()
    }

}