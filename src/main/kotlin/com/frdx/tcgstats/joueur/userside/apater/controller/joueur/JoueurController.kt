package com.frdx.tcgstats.joueur.userside.apater.controller.joueur

import com.frdx.tcgstats.jeux.domain.usecase.jeux.RecupererJeu
import com.frdx.tcgstats.joueur.domain.model.Jeu
import com.frdx.tcgstats.joueur.domain.usecase.joueur.AssocierJeuAUnJoueur
import com.frdx.tcgstats.joueur.domain.usecase.joueur.CreerJoueur
import com.frdx.tcgstats.joueur.domain.usecase.joueur.DissocierJeuDUnJoueur
import com.frdx.tcgstats.joueur.domain.usecase.joueur.RecupererJoueur
import com.frdx.tcgstats.joueur.domain.usecase.joueur.RecupererJoueurs
import com.frdx.tcgstats.joueur.domain.usecase.joueur.SupprimerJoueur
import com.frdx.tcgstats.joueur.userside.apater.controller.joueur.documentation.JoueurControllerDocumentation
import com.frdx.tcgstats.joueur.userside.dto.AssocierJeuDto
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
import org.springframework.web.bind.annotation.PutMapping
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
    private val supprimerJoueur: SupprimerJoueur,
    private val associerJeuAUnJoueur: AssocierJeuAUnJoueur,
    private val recupererJeu: RecupererJeu,
     private val dissocierJeuDUnJoueur: DissocierJeuDUnJoueur
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

    @PutMapping("/{id}/jeux")
    override fun associerUnJeu(@PathVariable id:String,@RequestBody idJeu: AssocierJeuDto): ResponseEntity<JoueurRestRessource> {
        val joueur = recupererUnJoueur(id)
        val jeuRecupere  = recupererJeu(idJeu.idJeu)
        val jeu = Jeu(id = jeuRecupere.id!!, nom = jeuRecupere.nom, logo = jeuRecupere.logo)
        val resultat = associerJeuAUnJoueur(joueur,jeu)
        return ResponseEntity.ok(resultat.toJoueurRestRessource())
    }


    @DeleteMapping("/{id}/{idJeu}")
    override fun dissocierUnJeu(@PathVariable id: String,@PathVariable idJeu: String): ResponseEntity<JoueurRestRessource> {
        val joueur = recupererUnJoueur(id)
        val jeuRecupere = recupererJeu(idJeu)
        val jeu = Jeu(id = jeuRecupere.id!!, nom = jeuRecupere.nom, logo = jeuRecupere.logo)
        val resultat = dissocierJeuDUnJoueur(joueur,jeu)
        return ResponseEntity.ok(resultat.toJoueurRestRessource())
    }


}