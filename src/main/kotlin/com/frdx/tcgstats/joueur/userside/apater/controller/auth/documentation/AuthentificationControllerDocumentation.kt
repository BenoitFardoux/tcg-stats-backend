package com.frdx.tcgstats.joueur.userside.apater.controller.auth.documentation

import com.frdx.tcgstats.joueur.userside.dto.ConnexionJoueurRestRessource
import com.frdx.tcgstats.joueur.userside.dto.CreerJoueurRestRessource
import com.frdx.tcgstats.joueur.userside.dto.JoueurRestRessource
import com.frdx.tcgstats.joueur.userside.dto.LoginResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity

interface AuthentificationControllerDocumentation{

    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "201",
                description = "Le joueur à bien été créé en base de données"
            ),
            ApiResponse(
                responseCode = "400",
                description = "Les données saisies sont invalides",
                content = [Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE)]
            ), ApiResponse(
                responseCode = "409",
                description = "L'utilisateur existe déjà.",
                content = [Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE)]
            )
        ]
    )
    @Operation(
        summary = "Créer un utilisateur",
        security = [SecurityRequirement(name = "")]
    )
    fun creer(joueur: CreerJoueurRestRessource): ResponseEntity<JoueurRestRessource>

    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "La clé api permettant de se connecter"
            )
        ]
    )
    @Operation(
        summary = "Se connecter à l'api",
        description = "renvoie une clé api",
        security = [SecurityRequirement(name = "")]  // Indique qu'il n'y a pas de sécurité requise

    )
    fun connexion(connexionJoueurRestRessource: ConnexionJoueurRestRessource) : ResponseEntity<LoginResponse>


    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Le joueur actuel"
            )
        ]
    )
    fun moi() : ResponseEntity<JoueurRestRessource>
}