package com.frdx.tcgstats.joueur.userside.apater.controller.joueur

import com.frdx.tcgstats.joueur.domain.model.Joueur
import com.frdx.tcgstats.joueur.domain.usecase.joueur.CreerJoueur
import com.frdx.tcgstats.joueur.userside.apater.controller.joueur.documentation.JoueurControllerDocumentation
import com.frdx.tcgstats.joueur.userside.dto.CreerJoueurRestRessource
import com.frdx.tcgstats.joueur.userside.exception.MotDePasseInvalideException
import com.frdx.tcgstats.joueur.userside.mapper.JoueurMapper.toJoueur
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("tcgstat/joueur")
class JoueurController(private val creerJoueur: CreerJoueur, private val passwordEncoder: BCryptPasswordEncoder) : JoueurControllerDocumentation {

    @PostMapping("/creer")
    override fun creer(@RequestBody joueur : CreerJoueurRestRessource) : ResponseEntity<Joueur> {
        joueur.motDePasse.ifBlank { throw MotDePasseInvalideException("Le mot de passe est vide") }
        val passwordEncoder = passwordEncoder.encode(joueur.motDePasse)

        val joueurRestRessource = joueur.toJoueur().copy(motDePasse = passwordEncoder)

        val joueurCree = creerJoueur(joueurRestRessource)

        return ResponseEntity.ok(joueurCree)
    }
    @GetMapping
    fun get() : ResponseEntity<String?> {
        return ResponseEntity.ok().body("Hello Joueur")
    }
}