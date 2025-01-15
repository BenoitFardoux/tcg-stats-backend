package com.frdx.tcgstats.joueur.userside.apater.controller.auth

import com.frdx.tcgstats.joueur.domain.usecase.joueur.ConnecterJoueur
import com.frdx.tcgstats.joueur.domain.usecase.joueur.CreerJoueur
import com.frdx.tcgstats.joueur.serverside.dto.JoueurDocument
import com.frdx.tcgstats.joueur.serverside.mapper.JoueurMapper.toJoueur
import com.frdx.tcgstats.joueur.serverside.mapper.JoueurMapper.toJoueurDocument
import com.frdx.tcgstats.joueur.userside.apater.controller.auth.documentation.AuthentificationControllerDocumentation
import com.frdx.tcgstats.joueur.userside.dto.ConnexionJoueurRestRessource
import com.frdx.tcgstats.joueur.userside.dto.CreerJoueurRestRessource
import com.frdx.tcgstats.joueur.userside.dto.JoueurRestRessource
import com.frdx.tcgstats.joueur.userside.dto.LoginResponse
import com.frdx.tcgstats.joueur.userside.exception.MotDePasseInvalideException
import com.frdx.tcgstats.joueur.userside.mapper.JoueurMapper.toJoueur
import com.frdx.tcgstats.joueur.userside.mapper.JoueurMapper.toJoueurRestRessource
import com.frdx.tcgstats.utils.JwtService
import com.frdx.tcgstats.utils.Utils.motDePasseEstValide
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/auth")
class AuthentificationController(
    val connecterJoueur: ConnecterJoueur,
    val creerJoueur: CreerJoueur,
    val passwordEncoder: BCryptPasswordEncoder,
    private val jwtService: JwtService,
) : AuthentificationControllerDocumentation {

    @PostMapping("/login")
    override fun connexion(@RequestBody connexionJoueurRestRessource: ConnexionJoueurRestRessource): ResponseEntity<LoginResponse> {
        val joueur = connecterJoueur(
            connexionJoueurRestRessource.email,
            connexionJoueurRestRessource.motDePasse
        ).toJoueurDocument()
        val jwt = jwtService.generateToken(joueur)
        val loginResponse = LoginResponse(token = jwt, expiresIn = jwtService.getExpirationTime())

        return ResponseEntity.ok(loginResponse)
    }

    @GetMapping("/me")
    @SecurityRequirement(name = "Bearer Authentication")
    override fun moi(): ResponseEntity<JoueurRestRessource> {
        val joueurActuel = SecurityContextHolder.getContext().authentication.principal as JoueurDocument
        return ResponseEntity.ok(joueurActuel.toJoueur().toJoueurRestRessource())

    }



    @PostMapping("/signup",produces = [MediaType.APPLICATION_JSON_VALUE])
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
}
