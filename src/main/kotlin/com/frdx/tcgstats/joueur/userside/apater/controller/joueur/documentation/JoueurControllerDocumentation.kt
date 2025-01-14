package com.frdx.tcgstats.joueur.userside.apater.controller.joueur.documentation

import com.frdx.tcgstats.joueur.userside.dto.CreerJoueurRestRessource
import com.frdx.tcgstats.joueur.userside.dto.JoueurRestRessource
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable

interface JoueurControllerDocumentation {
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
            ApiResponse(responseCode = "200", description = "La liste des joueurs existants")
        ]
    )
    @Operation(
        summary = "Recuperer les utilisateurs",
        security = [SecurityRequirement(name = "")]
    )
    fun recuperer(): ResponseEntity<List<JoueurRestRessource>>

    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "L'utilisateur demandé"
            ),
            ApiResponse(
                responseCode = "404",
                description = "L'utilisateur n'existe pas",
                content = [Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE)]
            )
        ]
    )
    fun recupererParId(@PathVariable id: String): ResponseEntity<JoueurRestRessource>
 @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "204",
                description = "L'utilisateur a été supprimé"
            ),
            ApiResponse(
                responseCode = "404",
                description = "L'utilisateur n'existe pas",
                content = [Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE)]
            )
        ]
    )
    fun supprimerUnJoueur(@PathVariable id: String)



}