package com.frdx.tcgstats.joueur.userside.apater.controller.joueur

import com.frdx.tcgstats.joueur.domain.usecase.joueur.CreerJoueur
import com.frdx.tcgstats.joueur.domain.usecase.joueur.RecupererJoueur
import com.frdx.tcgstats.joueur.domain.usecase.joueur.RecupererJoueurs
import com.frdx.tcgstats.joueur.domain.usecase.joueur.SupprimerJoueur
import com.frdx.tcgstats.joueur.userside.apater.controller.joueur.documentation.JoueurControllerDocumentation
import com.frdx.tcgstats.joueur.userside.dto.CreerJoueurRestRessource
import com.frdx.tcgstats.joueur.userside.dto.JoueurRestRessource
import com.frdx.tcgstats.joueur.userside.exception.MotDePasseInvalideException
import com.frdx.tcgstats.joueur.userside.mapper.JoueurMapper.toJoueur
import com.frdx.tcgstats.joueur.userside.mapper.JoueurMapper.toJoueurRestRessource
import com.frdx.tcgstats.utils.Utils.motDePasseEstValide
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("tcgstat/joueur")
 class JoueurController(
    private val creerJoueur: CreerJoueur,
    private val passwordEncoder: BCryptPasswordEncoder,
    private val recupererJoueurs: RecupererJoueurs,
    private val recupererUnJoueur: RecupererJoueur,
    private val supprimerJoueur: SupprimerJoueur
) : JoueurControllerDocumentation {

    @PostMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    override fun creer(@RequestBody joueur: CreerJoueurRestRessource): ResponseEntity<JoueurRestRessource> {
        motDePasseEstValide(joueur.motDePasse).let {
            if (!it) throw MotDePasseInvalideException("Mot de passe invalide")
        }
        joueur.motDePasse.ifBlank { throw MotDePasseInvalideException("Le mot de passe est vide") }
        val passwordEncoder = passwordEncoder.encode(joueur.motDePasse)

        val joueurRestRessource = joueur.toJoueur().copy(motDePasse = passwordEncoder)

        val joueurCree = creerJoueur(joueurRestRessource)

        return ResponseEntity.status(HttpStatus.CREATED).body(joueurCree.toJoueurRestRessource())
    }

    @GetMapping
    override fun recuperer(): ResponseEntity<List<JoueurRestRessource>> {
        val joueurs = recupererJoueurs().map { it.toJoueurRestRessource() }
        return ResponseEntity.ok().body(joueurs)
    }

    @GetMapping("/{id}")
    override fun recupererParId(@PathVariable id: String): ResponseEntity<JoueurRestRessource> {
        val joueur = recupererUnJoueur(id)
        return ResponseEntity.ok(joueur.toJoueurRestRessource())
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    override fun supprimerUnJoueur(@PathVariable id: String) {
        supprimerJoueur(id)
    }
}